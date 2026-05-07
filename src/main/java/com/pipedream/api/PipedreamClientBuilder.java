package com.pipedream.api;

import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.OAuthTokenSupplier;
import com.pipedream.api.resources.oauthtokens.OauthTokensClient;

/**
 * Builder for creating PipedreamClient instances.
 */
public final class PipedreamClientBuilder extends BaseClientBuilder<PipedreamClientBuilder> {
    private String projectId;
    private String clientId;
    private String clientSecret;
    private String token;
    private String scope;

    public PipedreamClient build() {
        validateConfiguration();
        if (this.token != null) {
            ClientOptions baseOptions = buildClientOptions();
            ClientOptions finalOptions = ClientOptions.Builder.from(baseOptions)
                    .addHeader("Authorization", "Bearer " + this.token)
                    .build();
            return new PipedreamClient(finalOptions);
        }
        if (this.clientId != null && this.clientSecret != null) {
            ClientOptions baseOptions = buildClientOptions();
            OauthTokensClient authClient = new OauthTokensClient(baseOptions);
            OAuthTokenSupplier oAuthTokenSupplier =
                    new OAuthTokenSupplier(this.clientId, this.clientSecret, this.scope, authClient);
            ClientOptions finalOptions = ClientOptions.Builder.from(baseOptions)
                    .addHeader("Authorization", oAuthTokenSupplier)
                    .build();
            return new PipedreamClient(finalOptions);
        }
        return new PipedreamClient(buildClientOptions());
    }

    /**
     * Overrides the default API base URL (https://api.pipedream.com).
     * If not set, the production URL is used.
     */
    public PipedreamClientBuilder baseUrl(String url) {
        return this.url(url);
    }

    public PipedreamClientBuilder projectId(final String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * Sets the OAuth client ID. Defaults to {@code PIPEDREAM_CLIENT_ID} when the builder is
     * created via {@link PipedreamClient#builder()}.
     */
    public PipedreamClientBuilder clientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Sets the OAuth client secret. Defaults to {@code PIPEDREAM_CLIENT_SECRET} when the builder
     * is created via {@link PipedreamClient#builder()}.
     */
    public PipedreamClientBuilder clientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /**
     * Sets a pre-generated access token. When set, OAuth client-credentials are bypassed
     * and the token is sent directly in the Authorization header.
     */
    public PipedreamClientBuilder token(final String token) {
        this.token = token;
        return this;
    }

    /**
     * Sets the OAuth scope used when exchanging client credentials for an access token.
     * Supports progressive scopes.
     */
    public PipedreamClientBuilder scope(final String scope) {
        this.scope = scope;
        return this;
    }

    @Override
    public void setVariables(ClientOptions.Builder builder) {
        builder.projectId(this.projectId);
    }
}
