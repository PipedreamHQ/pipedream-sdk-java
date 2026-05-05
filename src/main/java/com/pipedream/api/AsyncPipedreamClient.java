package com.pipedream.api;

import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.Environment;
import com.pipedream.api.core.Suppliers;
import com.pipedream.api.resources.workflows.WorkflowsClient;
import java.util.Optional;
import java.util.function.Supplier;

public class AsyncPipedreamClient extends AsyncBaseClient {
    private final Supplier<WorkflowsClient> workflowsClient;

    public AsyncPipedreamClient(final ClientOptions clientOptions) {
        super(clientOptions);
        this.workflowsClient = Suppliers.memoize(() -> new WorkflowsClient(clientOptions));
    }

    /**
     * Creates a builder pre-populated from the standard Pipedream environment variables
     * (PIPEDREAM_BASE_URL, PIPEDREAM_CLIENT_ID, PIPEDREAM_CLIENT_SECRET,
     * PIPEDREAM_PROJECT_ENVIRONMENT, PIPEDREAM_PROJECT_ID).
     *
     * <p>For OAuth client credentials, the env-var defaults can be overridden via
     * {@code .clientId(...)} / {@code .clientSecret(...)} and progressive scopes set with
     * {@code .scope(...)}. For pre-generated access tokens, use {@code .token(...)} to bypass
     * the OAuth flow entirely.
     */
    public static AsyncPipedreamClientBuilder builder() {
        String baseUrl = System.getenv("PIPEDREAM_BASE_URL") != null
                ? System.getenv("PIPEDREAM_BASE_URL")
                : Environment.PROD.getUrl();
        return new AsyncPipedreamClientBuilder()
                .clientId(System.getenv("PIPEDREAM_CLIENT_ID"))
                .clientSecret(System.getenv("PIPEDREAM_CLIENT_SECRET"))
                .url(baseUrl)
                .projectEnvironment(System.getenv("PIPEDREAM_PROJECT_ENVIRONMENT"))
                .projectId(System.getenv("PIPEDREAM_PROJECT_ID"));
    }

    /**
     * Returns an access token that can be used to authenticate API requests
     *
     * @return the access token string (if available)
     */
    public Optional<String> rawAccessToken() {
        final String authorizationHeader = this.clientOptions.headers(null).get("Authorization");

        // The header might not be defined, so we wrap it as an Optional to
        // further process it. The processing consists of removing the `Bearer`
        // or `Basic` prefix from the header value.
        return Optional.ofNullable(authorizationHeader).map(h -> h.replaceFirst("^.*?\\s+", ""));
    }

    public WorkflowsClient workflows() {
        return this.workflowsClient.get();
    }
}
