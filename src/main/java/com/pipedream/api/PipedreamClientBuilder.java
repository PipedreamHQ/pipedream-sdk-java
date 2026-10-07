package com.pipedream.api;

import com.pipedream.api.core.ClientAssertionSigner;
import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.ConnectPathNormalizationInterceptor;
import com.pipedream.api.core.OAuthTokenSupplier;
import com.pipedream.api.core.PrivateKeyJwtTokenSupplier;
import com.pipedream.api.resources.oauthtokens.OauthTokensClient;
import java.util.function.Supplier;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

/**
 * Builder for creating PipedreamClient instances.
 */
public final class PipedreamClientBuilder extends BaseClientBuilder<PipedreamClientBuilder> {
    private String projectId;
    private String clientId;
    private String clientSecret;
    private String privateKey;
    private String keyId;
    private String token;
    private String scope;
    // Credentials read from the environment by {@link PipedreamClientbuilder()}. Explicitly set
    // credentials take precedence over these.
    private String envClientSecret;
    private String envPrivateKey;
    private String envKeyId;

    public PipedreamClient build() {
        validateConfiguration();

        final ClientOptions baseOptions = buildClientOptions();
        final ClientOptions.Builder optionsBuilder = ClientOptions.Builder.from(baseOptions);
        final ClientOptions finalOptions = optionsBuilder
                .addHeader("Authorization", getAuthHeaderSupplier(baseOptions))
                .httpClient(withConnectPathNormalization(baseOptions.httpClient()))
                .build();
        return new PipedreamClient(finalOptions);
    }

    @NotNull
    private Supplier<String> getAuthHeaderSupplier(final ClientOptions baseOptions) {
        if (this.token != null) {
            return () -> "Bearer " + this.token;
        }

        if (this.clientSecret != null && this.privateKey != null) {
            throw new IllegalStateException("Pass either clientSecret or privateKey, not both");
        }
        String secret = this.clientSecret;
        String key = this.privateKey;
        String kid = this.keyId;
        if (secret == null && key == null) {
            if (this.envClientSecret != null && this.envPrivateKey != null) {
                throw new IllegalStateException(
                        "Both PIPEDREAM_CLIENT_SECRET and PIPEDREAM_PRIVATE_KEY are set; set only the one your client uses");
            }
            secret = this.envClientSecret;
            key = this.envPrivateKey;
        }
        if (kid == null) {
            kid = this.envKeyId;
        }

        if (this.clientId != null && key != null) {
            // The assertion audience is the API's issuer identifier: the base URL's origin
            // (https://api.pipedream.com by default).
            final ClientAssertionSigner signer = new ClientAssertionSigner(
                    this.clientId,
                    key,
                    kid,
                    PrivateKeyJwtTokenSupplier.issuer(baseOptions.environment().getUrl()));
            return new PrivateKeyJwtTokenSupplier(this.clientId, this.scope, signer, baseOptions);
        }

        if (this.clientId != null && secret != null) {
            final OauthTokensClient authClient = new OauthTokensClient(baseOptions);
            return new OAuthTokenSupplier(this.clientId, secret, this.scope, authClient);
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
     * For clients that authenticate with a public key instead of a client secret: the matching
     * private key, as an unencrypted PKCS#8 PEM ({@code -----BEGIN PRIVATE KEY-----}) or a JWK
     * (JSON). The SDK signs a short-lived client assertion with it for each token request: ES256 for
     * EC P-256 keys, RS256 for RSA keys. Can't be combined with {@link #clientSecret(String)}.
     * Defaults to {@code PIPEDREAM_PRIVATE_KEY} when the builder is created via {@link
     * PipedreamClientbuilder()}.
     */
    public PipedreamClientBuilder privateKey(final String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    /**
     * Optional. The key ID shown in the Pipedream UI, sent as the client assertion's {@code kid}
     * header. Defaults to {@code PIPEDREAM_KEY_ID} when the builder is created via {@link
     * PipedreamClientbuilder()}.
     */
    public PipedreamClientBuilder keyId(final String keyId) {
        this.keyId = keyId;
        return this;
    }

    /** Credentials from the environment, used only when none are set explicitly. */
    PipedreamClientBuilder environmentCredentials(
            final String clientSecret, final String privateKey, final String keyId) {
        this.envClientSecret = clientSecret;
        this.envPrivateKey = privateKey;
        this.envKeyId = keyId;
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
        // Coerce project IDs to strings, so that an NPE doesn't blow things up
        // at runtime.
        builder.projectId(this.projectId != null ? this.projectId : "");
    }
}
