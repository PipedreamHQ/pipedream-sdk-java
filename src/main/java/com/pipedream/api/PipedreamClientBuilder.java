package com.pipedream.api;

import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.Environment;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookupFactory;

/**
 * Builder for creating PipedreamClient instances.
 */
public final class PipedreamClientBuilder extends BaseClientBuilder<PipedreamClientBuilder> {
    private String projectId;

    public PipedreamClient build() {
        return new PipedreamClient(buildClientOptions());
    }

    public PipedreamClientBuilder environment(final Environment environment) {
        final String patchedUrl = patchUrl(environment.getUrl());
        final Environment withPatchedUrl = Environment.custom(patchedUrl);
        super.environment(withPatchedUrl);
        return this;
    }

    public PipedreamClientBuilder projectId(final String projectId) {
        this.projectId = projectId;
        return this;
    }

    @Override
    public void setVariables(ClientOptions.Builder builder) {
        builder.projectId(this.projectId);
    }

    private static String patchUrl(final String templateUrl) {
        StringSubstitutor sub = new StringSubstitutor(StringLookupFactory.INSTANCE.environmentVariableStringLookup());

        return sub.replace(templateUrl);
    }
}
