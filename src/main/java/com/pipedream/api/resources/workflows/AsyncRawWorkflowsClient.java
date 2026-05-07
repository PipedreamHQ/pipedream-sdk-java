/**
 * This file was manually created to add workflow invocation support.
 */
package com.pipedream.api.resources.workflows;

import com.pipedream.api.core.BaseClientApiException;
import com.pipedream.api.core.BaseClientException;
import com.pipedream.api.core.BaseClientHttpResponse;
import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.MediaTypes;
import com.pipedream.api.core.ObjectMappers;
import com.pipedream.api.core.RequestOptions;
import com.pipedream.api.resources.workflows.requests.InvokeWorkflowForExternalUserOpts;
import com.pipedream.api.resources.workflows.requests.InvokeWorkflowOpts;
import com.pipedream.api.types.HTTPAuthType;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AsyncRawWorkflowsClient {
    protected final ClientOptions clientOptions;
    private final String workflowDomain;
    private final String urlProtocol;

    public AsyncRawWorkflowsClient(ClientOptions clientOptions) {
        this.clientOptions = clientOptions;
        this.workflowDomain = getDefaultWorkflowDomain();
        this.urlProtocol = getUrlProtocol();
    }

    public CompletableFuture<BaseClientHttpResponse<Object>> invoke(InvokeWorkflowOpts request) {
        return invoke(request, null);
    }

    public CompletableFuture<BaseClientHttpResponse<Object>> invoke(
            InvokeWorkflowOpts request, RequestOptions requestOptions) {

        // Build the workflow URL
        String urlString = buildWorkflowUrl(request.getUrlOrEndpoint());

        HttpUrl httpUrl;
        try {
            httpUrl = HttpUrl.parse(urlString);
            if (httpUrl == null) {
                throw new IllegalArgumentException("Invalid URL: " + urlString);
            }
        } catch (Exception e) {
            CompletableFuture<BaseClientHttpResponse<Object>> future = new CompletableFuture<>();
            future.completeExceptionally(new IllegalArgumentException("Invalid URL: " + urlString, e));
            return future;
        }

        // Determine auth type - default to OAuth if not specified
        HTTPAuthType authType = request.getAuthType().orElse(HTTPAuthType.OAUTH);

        // Prepare headers - start with client options headers (includes OAuth auth if configured)
        Map<String, String> allHeaders = new HashMap<>(clientOptions.headers(requestOptions));

        // Handle authentication based on type
        if (authType == HTTPAuthType.OAUTH) {
            // For OAuth, the Authorization header should already be in clientOptions.headers()
            // No additional action needed
        } else if (authType == HTTPAuthType.STATIC_BEARER) {
            // For static_bearer, users must provide the Authorization header in request.getHeaders()
            // Their header will override any existing OAuth header when we merge request headers
        } else if (authType == HTTPAuthType.NONE) {
            // For NONE auth type, set Authorization header to empty string (matches Python SDK)
            allHeaders.put("Authorization", "");
        }

        // Add request-specific headers (can override auth headers for STATIC_BEARER)
        if (request.getHeaders().isPresent()) {
            allHeaders.putAll(request.getHeaders().get());
        }

        // Determine HTTP method
        String method = request.getMethod().orElse("POST").toUpperCase();

        // Prepare request body if needed
        RequestBody body = null;
        if (request.getBody().isPresent()) {
            try {
                body = RequestBody.create(
                        ObjectMappers.JSON_MAPPER.writeValueAsBytes(
                                request.getBody().get()),
                        MediaTypes.APPLICATION_JSON);
                allHeaders.put("Content-Type", "application/json");
            } catch (Exception e) {
                CompletableFuture<BaseClientHttpResponse<Object>> future = new CompletableFuture<>();
                future.completeExceptionally(new RuntimeException("Failed to serialize request body", e));
                return future;
            }
        } else if (("POST".equals(method) || "PUT".equals(method) || "PATCH".equals(method))) {
            // For methods that typically require a body, send an empty body
            // to avoid OkHttp's "method POST must have a request body" error
            body = RequestBody.create(new byte[0], null);
        }

        // Build the request
        Request.Builder requestBuilder =
                new Request.Builder().url(httpUrl).method(method, body).headers(Headers.of(allHeaders));

        if (!allHeaders.containsKey("Accept")) {
            requestBuilder.addHeader("Accept", "application/json");
        }

        Request okhttpRequest = requestBuilder.build();

        // Execute the request asynchronously
        OkHttpClient client = clientOptions.httpClient();
        if (requestOptions != null && requestOptions.getTimeout().isPresent()) {
            client = clientOptions.httpClientWithTimeout(requestOptions);
        }

        CompletableFuture<BaseClientHttpResponse<Object>> future = new CompletableFuture<>();

        client.newCall(okhttpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(new BaseClientException("Network error executing HTTP request", e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (response.isSuccessful()) {
                        String responseBodyString = responseBody != null ? responseBody.string() : "{}";
                        Object parsedResponse;
                        try {
                            parsedResponse = ObjectMappers.JSON_MAPPER.readValue(responseBodyString, Object.class);
                        } catch (Exception e) {
                            // If JSON parsing fails, return the raw string
                            parsedResponse = responseBodyString;
                        }
                        future.complete(new BaseClientHttpResponse<>(parsedResponse, response));
                    } else {
                        String responseBodyString = responseBody != null ? responseBody.string() : "{}";
                        future.completeExceptionally(new BaseClientApiException(
                                "Error with status code " + response.code(),
                                response.code(),
                                ObjectMappers.JSON_MAPPER.readValue(responseBodyString, Object.class),
                                response));
                    }
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }

    public CompletableFuture<BaseClientHttpResponse<Object>> invokeForExternalUser(
            InvokeWorkflowForExternalUserOpts request) {
        return invokeForExternalUser(request, null);
    }

    public CompletableFuture<BaseClientHttpResponse<Object>> invokeForExternalUser(
            InvokeWorkflowForExternalUserOpts request, RequestOptions requestOptions) {

        // Validate inputs
        if (request.getExternalUserId() == null
                || request.getExternalUserId().trim().isEmpty()) {
            CompletableFuture<BaseClientHttpResponse<Object>> future = new CompletableFuture<>();
            future.completeExceptionally(new IllegalArgumentException("External user ID is required"));
            return future;
        }

        if (request.getUrl() == null || request.getUrl().trim().isEmpty()) {
            CompletableFuture<BaseClientHttpResponse<Object>> future = new CompletableFuture<>();
            future.completeExceptionally(new IllegalArgumentException("Workflow URL is required"));
            return future;
        }

        // Prepare headers with external user ID
        Map<String, String> headers = new HashMap<>();
        if (request.getHeaders().isPresent()) {
            headers.putAll(request.getHeaders().get());
        }
        headers.put("X-PD-External-User-ID", request.getExternalUserId());

        // Create a new request with the authentication from the original request and the external user header
        InvokeWorkflowOpts invokeRequest = InvokeWorkflowOpts.builder()
                .urlOrEndpoint(request.getUrl())
                .body(request.getBody())
                .headers(headers)
                .method(request.getMethod())
                .authType(request.getAuthType().orElse(HTTPAuthType.OAUTH))
                .build();

        return invoke(invokeRequest, requestOptions);
    }

    /**
     * Builds a full workflow URL based on the input.
     *
     * @param input Either a full URL (with or without protocol) or just an endpoint ID.
     * @return The fully constructed URL.
     */
    private String buildWorkflowUrl(String input) {
        String sanitizedInput = input.trim().toLowerCase();
        if (sanitizedInput.isEmpty()) {
            throw new IllegalArgumentException("URL or endpoint ID is required");
        }

        // Check if it's already a full URL
        if (sanitizedInput.startsWith("http://") || sanitizedInput.startsWith("https://")) {
            try {
                URL url = new URL(input);
                // Validate the hostname
                String workflowDomain = this.workflowDomain;
                if (!url.getHost().endsWith(this.workflowDomain)) {
                    throw new IllegalArgumentException(
                            "Invalid workflow domain. URL must end with " + this.workflowDomain);
                }
                return input;
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("The provided URL is malformed: " + input, e);
            }
        }

        // Check if it's a URL without protocol
        if (sanitizedInput.contains(".")) {
            return buildWorkflowUrl("https://" + input);
        }

        // It's an endpoint ID
        if (!sanitizedInput.matches("^e[no][a-z0-9-]+$")) {
            throw new IllegalArgumentException(
                    "Invalid endpoint ID format. Must contain only letters, numbers, and hyphens, "
                            + "and start with either 'en' or 'eo'.");
        }

        return urlProtocol + "://" + sanitizedInput + "." + workflowDomain;
    }

    private String getDefaultWorkflowDomain() {
        String envUrl = clientOptions.environment().getUrl();
        // For non-prod environments (dev, staging), use dev domain
        if (!envUrl.equals("https://api.pipedream.com") && !envUrl.equals("https://api2.pipedream.com")) {
            return "m.d.pipedream.net";
        }
        // For prod and canary, use standard domain
        return "m.pipedream.net";
    }

    private String getUrlProtocol() {
        String envUrl = clientOptions.environment().getUrl();
        // For non-prod environments (dev, staging), use http
        if (!envUrl.equals("https://api.pipedream.com") && !envUrl.equals("https://api2.pipedream.com")) {
            return "http";
        }
        // For prod and canary, use https
        return "https";
    }
}
