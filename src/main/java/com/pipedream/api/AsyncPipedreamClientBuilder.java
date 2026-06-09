package com.pipedream.api;

import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.ConnectPathNormalizationInterceptor;
import com.pipedream.api.core.OAuthTokenSupplier;
import com.pipedream.api.resources.oauthtokens.OauthTokensClient;
import java.util.function.Supplier;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

/**
 * Builder for creating AsyncPipedreamClient instances.
 */
public final class AsyncPipedreamClientBuilder extends AsyncBaseClientBuilder<AsyncPipedreamClientBuilder> {
    private String projectId;
    private String clientId;
    private String clientSecret;
    private String token;
    private String scope;

    public AsyncPipedreamClient build() {
        validateConfiguration();

        final ClientOptions baseOptions = buildClientOptions();
        final ClientOptions.Builder optionsBuilder = ClientOptions.Builder.from(baseOptions);
        final ClientOptions finalOptions = optionsBuilder
                .addHeader("Authorization", getAuthHeaderSupplier(baseOptions))
                .httpClient(withConnectPathNormalization(baseOptions.httpClient()))
                .build();
        return new AsyncPipedreamClient(finalOptions);
    }

    @NotNull
    private Supplier<String> getAuthHeaderSupplier(final ClientOptions baseOptions) {
        if (this.token != null) {
            return () -> "Bearer " + this.token;
        }

        if (this.clientId != null && this.clientSecret != null) {
            final OauthTokensClient authClient = new OauthTokensClient(baseOptions);
            return new OAuthTokenSupplier(this.clientId, this.clientSecret, this.scope, authClient);
        }

        return () -> "";
    }

    private static OkHttpClient withConnectPathNormalization(OkHttpClient httpClient) {
        return httpClient
                .newBuilder()
                .addInterceptor(new ConnectPathNormalizationInterceptor())
                .build();
    }

    /**
     * Overrides the default API base URL (https://api.pipedream.com).
     * If not set, the production URL is used.
     */
    public AsyncPipedreamClientBuilder baseUrl(String url) {
        return this.url(url);
    }

    public AsyncPipedreamClientBuilder projectId(final String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * Sets the OAuth client ID. Defaults to {@code PIPEDREAM_CLIENT_ID} when the builder is
     * created via {@link AsyncPipedreamClient#builder()}.
     */
    public AsyncPipedreamClientBuilder clientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Sets the OAuth client secret. Defaults to {@code PIPEDREAM_CLIENT_SECRET} when the builder
     * is created via {@link AsyncPipedreamClient#builder()}.
     */
    public AsyncPipedreamClientBuilder clientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /**
     * Sets a pre-generated access token. When set, OAuth client-credentials are bypassed
     * and the token is sent directly in the Authorization header.
     */
    public AsyncPipedreamClientBuilder token(final String token) {
        this.token = token;
        return this;
    }

    /**
     * Sets the OAuth scope used when exchanging client credentials for an access token.
     * Supports progressive scopes.
     */
    public AsyncPipedreamClientBuilder scope(final String scope) {
        this.scope = scope;
        return this;
    }

    @Override
    public void setVariables(ClientOptions.Builder builder) {
        // Coerce project IDs to strings, so that an NPE doesn't blow things up
        // at runtime.
        builder.projectId(this.projectId != null ? this.projectId : "");
    }
}
