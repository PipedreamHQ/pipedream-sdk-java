package com.pipedream.api;

import com.pipedream.api.core.ClientOptions;

/**
 * Builder for creating PipedreamClient instances.
 */
public final class PipedreamClientBuilder extends BaseClientBuilder<PipedreamClientBuilder> {
    private String projectId;

    public PipedreamClient build() {
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

    @Override
    public void setVariables(ClientOptions.Builder builder) {
        builder.projectId(this.projectId);
    }
}
