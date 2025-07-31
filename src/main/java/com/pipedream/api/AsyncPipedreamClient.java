package com.pipedream.api;

import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.Environment;
import com.pipedream.api.core.Suppliers;
import com.pipedream.api.resources.workflows.WorkflowsClient;
import org.immutables.value.Value;

import java.util.Optional;
import java.util.function.Supplier;

@Value
public class AsyncPipedreamClient extends AsyncBaseClient {
    private final Supplier<WorkflowsClient> workflowsClient;

    public AsyncPipedreamClient(final ClientOptions clientOptions) {
        super(clientOptions);
        this.workflowsClient = Suppliers.memoize(() -> new WorkflowsClient(clientOptions));
    }

    public static AsyncPipedreamClientBuilder builder() {
        return new AsyncPipedreamClientBuilder()
                .clientId(System.getenv("PIPEDREAM_CLIENT_ID"))
                .clientSecret(System.getenv("PIPEDREAM_CLIENT_SECRET"))
                .environment(Environment.PROD)
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
