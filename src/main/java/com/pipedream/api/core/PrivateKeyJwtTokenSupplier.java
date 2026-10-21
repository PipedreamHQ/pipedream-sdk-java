package com.pipedream.api.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pipedream.api.errors.TooManyRequestsError;
import com.pipedream.api.types.CreateOAuthTokenResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Supplies an {@code Authorization} header value ({@code Bearer <token>}) for a client that
 * authenticates with a private key. Access tokens are cached and refreshed like {@link
 * OAuthTokenSupplier}, but each token request carries a newly signed client assertion.
 *
 * <p>Token requests bypass the HTTP client's retry interceptor, which would resend the same
 * assertion: the API rejects a reused one. Transient failures (408, 429, 5xx, network errors) are
 * retried here instead, each attempt with a fresh assertion. Thread-safe.
 */
public final class PrivateKeyJwtTokenSupplier implements Supplier<String> {
    private static final long BUFFER_IN_MINUTES = 2;
    private static final int MAX_RETRIES = 2;

    private final String clientId;
    private final String scope;
    private final ClientAssertionSigner signer;
    private final ClientOptions clientOptions;
    private final OkHttpClient httpClient;

    private String accessToken;
    private Instant expiresAt = Instant.now();

    /**
     * @param clientOptions options for the token request (base URL, headers, HTTP client), without
     *     an {@code Authorization} header
     */
    public PrivateKeyJwtTokenSupplier(
            String clientId, String scope, ClientAssertionSigner signer, ClientOptions clientOptions) {
        this.clientId = clientId;
        this.scope = scope;
        this.signer = signer;
        this.clientOptions = clientOptions;
        OkHttpClient.Builder builder = clientOptions.httpClient().newBuilder();
        builder.interceptors().removeIf(interceptor -> interceptor instanceof RetryInterceptor);
        this.httpClient = builder.build();
    }

    /**
     * The API's issuer identifier, sent as the assertion's {@code aud}: the origin of the base URL,
     * without a trailing slash.
     */
    public static String issuer(String baseUrl) {
        HttpUrl url = HttpUrl.parse(baseUrl);
        if (url == null) {
            throw new IllegalArgumentException("Invalid base URL: " + baseUrl);
        }
        boolean defaultPort = url.port() == HttpUrl.defaultPort(url.scheme());
        return url.scheme() + "://" + url.host() + (defaultPort ? "" : ":" + url.port());
    }

    @Override
    public synchronized String get() {
        if (accessToken == null || expiresAt.isBefore(Instant.now())) {
            CreateOAuthTokenResponse response = fetchToken();
            this.accessToken = response.getAccessToken();
            this.expiresAt = Instant.now()
                    .plus(response.getExpiresIn(), ChronoUnit.SECONDS)
                    .minus(BUFFER_IN_MINUTES, ChronoUnit.MINUTES);
        }
        return "Bearer " + accessToken;
    }

    private CreateOAuthTokenResponse fetchToken() {
        for (int attempt = 0; ; attempt++) {
            try {
                return requestToken();
            } catch (RuntimeException e) {
                if (attempt >= MAX_RETRIES || !isRetryable(e)) {
                    throw e;
                }
                try {
                    Thread.sleep(500L << attempt);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
            }
        }
    }

    private CreateOAuthTokenResponse requestToken() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("client_id", clientId);
        body.put("client_assertion_type", ClientAssertionSigner.CLIENT_ASSERTION_TYPE);
        body.put("client_assertion", signer.sign());
        if (scope != null) {
            body.put("scope", scope);
        }
        Request request;
        try {
            request = new Request.Builder()
                    .url(HttpUrl.parse(clientOptions.environment().getUrl())
                            .newBuilder()
                            .addPathSegments("v1/oauth/token")
                            .build())
                    .post(RequestBody.create(
                            ObjectMappers.JSON_MAPPER.writeValueAsBytes(body), MediaTypes.APPLICATION_JSON))
                    .headers(Headers.of(clientOptions.headers(null)))
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
        } catch (JsonProcessingException e) {
            throw new BaseClientException("Failed to serialize request", e);
        }
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            String responseBodyString = responseBody != null ? responseBody.string() : "{}";
            if (response.isSuccessful()) {
                return ObjectMappers.JSON_MAPPER.readValue(responseBodyString, CreateOAuthTokenResponse.class);
            }
            Object errorBody = ObjectMappers.parseErrorBody(responseBodyString);
            if (response.code() == 429) {
                throw new TooManyRequestsError(errorBody, response);
            }
            throw new BaseClientApiException(
                    "Error with status code " + response.code(), response.code(), errorBody, response);
        } catch (IOException e) {
            throw new BaseClientException("Network error executing HTTP request", e);
        }
    }

    private static boolean isRetryable(RuntimeException e) {
        if (e instanceof BaseClientApiException) {
            int status = ((BaseClientApiException) e).statusCode();
            return status == 408 || status == 429 || status >= 500;
        }
        // Network errors; serialization errors don't recover on retry.
        return e instanceof BaseClientException && e.getCause() instanceof IOException;
    }
}
