package com.pipedream.api;

import com.pipedream.api.core.ClientOptions;

/**
 * Builder for creating AsyncPipedreamClient instances.
 */
public final class AsyncPipedreamClientBuilder extends AsyncBaseClientBuilder<AsyncPipedreamClientBuilder> {
    private String projectId;

    public AsyncPipedreamClient build() {
        return new AsyncPipedreamClient(buildClientOptions());
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

    @Override
    public void setVariables(ClientOptions.Builder builder) {
        builder.projectId(this.projectId);
    }
}
