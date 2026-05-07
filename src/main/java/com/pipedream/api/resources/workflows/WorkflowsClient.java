/**
 * This file was manually created to add workflow invocation support.
 */
package com.pipedream.api.resources.workflows;

import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.RequestOptions;
import com.pipedream.api.resources.workflows.requests.InvokeWorkflowForExternalUserOpts;
import com.pipedream.api.resources.workflows.requests.InvokeWorkflowOpts;

public class WorkflowsClient {
    protected final ClientOptions clientOptions;

    private final RawWorkflowsClient rawClient;

    public WorkflowsClient(ClientOptions clientOptions) {
        this.clientOptions = clientOptions;
        this.rawClient = new RawWorkflowsClient(clientOptions);
    }

    /**
     * Get responses with HTTP metadata like headers
     */
    public RawWorkflowsClient withRawResponse() {
        return this.rawClient;
    }

    /**
     * Invokes a workflow using the URL of its HTTP interface(s).
     *
     * @param urlOrEndpoint The URL of the workflow's HTTP interface, or the ID of the endpoint
     * @return The response from the workflow
     */
    public Object invoke(String urlOrEndpoint) {
        InvokeWorkflowOpts request =
                InvokeWorkflowOpts.builder().urlOrEndpoint(urlOrEndpoint).build();
        return this.rawClient.invoke(request).body();
    }

    /**
     * Invokes a workflow using the URL of its HTTP interface(s).
     *
     * @param urlOrEndpoint The URL of the workflow's HTTP interface, or the ID of the endpoint
     * @param requestOptions Additional request options
     * @return The response from the workflow
     */
    public Object invoke(String urlOrEndpoint, RequestOptions requestOptions) {
        InvokeWorkflowOpts request =
                InvokeWorkflowOpts.builder().urlOrEndpoint(urlOrEndpoint).build();
        return this.rawClient.invoke(request, requestOptions).body();
    }

    /**
     * Invokes a workflow using the InvokeWorkflowOpts request object.
     *
     * @param request The request containing workflow invocation parameters
     * @return The response from the workflow
     */
    public Object invoke(InvokeWorkflowOpts request) {
        return this.rawClient.invoke(request).body();
    }

    /**
     * Invokes a workflow using the InvokeWorkflowOpts request object.
     *
     * @param request The request containing workflow invocation parameters
     * @param requestOptions Additional request options
     * @return The response from the workflow
     */
    public Object invoke(InvokeWorkflowOpts request, RequestOptions requestOptions) {
        return this.rawClient.invoke(request, requestOptions).body();
    }

    /**
     * Invokes a workflow for a Pipedream Connect user in a project.
     *
     * @param urlOrEndpoint The URL of the workflow's HTTP interface, or the ID of the endpoint
     * @param externalUserId Your end user ID, for whom you're invoking the workflow
     * @return The response from the workflow
     */
    public Object invokeForExternalUser(String urlOrEndpoint, String externalUserId) {
        InvokeWorkflowForExternalUserOpts request = InvokeWorkflowForExternalUserOpts.builder()
                .url(urlOrEndpoint)
                .externalUserId(externalUserId)
                .build();
        return this.rawClient.invokeForExternalUser(request).body();
    }

    /**
     * Invokes a workflow for a Pipedream Connect user in a project.
     *
     * @param urlOrEndpoint The URL of the workflow's HTTP interface, or the ID of the endpoint
     * @param externalUserId Your end user ID, for whom you're invoking the workflow
     * @param requestOptions Additional request options
     * @return The response from the workflow
     */
    public Object invokeForExternalUser(String urlOrEndpoint, String externalUserId, RequestOptions requestOptions) {
        InvokeWorkflowForExternalUserOpts request = InvokeWorkflowForExternalUserOpts.builder()
                .url(urlOrEndpoint)
                .externalUserId(externalUserId)
                .build();
        return this.rawClient.invokeForExternalUser(request, requestOptions).body();
    }

    /**
     * Invokes a workflow for a Pipedream Connect user using the InvokeWorkflowForExternalUserOpts request object.
     *
     * @param request The request containing workflow invocation parameters
     * @return The response from the workflow
     */
    public Object invokeForExternalUser(InvokeWorkflowForExternalUserOpts request) {
        return this.rawClient.invokeForExternalUser(request).body();
    }

    /**
     * Invokes a workflow for a Pipedream Connect user using the InvokeWorkflowForExternalUserOpts request object.
     *
     * @param request The request containing workflow invocation parameters
     * @param requestOptions Additional request options
     * @return The response from the workflow
     */
    public Object invokeForExternalUser(InvokeWorkflowForExternalUserOpts request, RequestOptions requestOptions) {
        return this.rawClient.invokeForExternalUser(request, requestOptions).body();
    }
}
