/**
 * This file was manually created to add workflow invocation support.
 */
package com.pipedream.api.resources.workflows;

import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.RequestOptions;
import com.pipedream.api.resources.workflows.requests.InvokeWorkflowForExternalUserOpts;
import com.pipedream.api.resources.workflows.requests.InvokeWorkflowOpts;
import java.util.concurrent.CompletableFuture;

public class AsyncWorkflowsClient {
    protected final ClientOptions clientOptions;

    private final AsyncRawWorkflowsClient rawClient;

    public AsyncWorkflowsClient(ClientOptions clientOptions) {
        this.clientOptions = clientOptions;
        this.rawClient = new AsyncRawWorkflowsClient(clientOptions);
    }

    /**
     * Get responses with HTTP metadata like headers
     */
    public AsyncRawWorkflowsClient withRawResponse() {
        return this.rawClient;
    }

    /**
     * Invokes a workflow using the URL of its HTTP interface(s).
     *
     * @param urlOrEndpoint The URL of the workflow's HTTP interface, or the ID of the endpoint
     * @return A future containing the response from the workflow
     */
    public CompletableFuture<Object> invoke(String urlOrEndpoint) {
        InvokeWorkflowOpts request =
                InvokeWorkflowOpts.builder().urlOrEndpoint(urlOrEndpoint).build();
        return this.rawClient.invoke(request).thenApply(response -> response.body());
    }

    /**
     * Invokes a workflow using the URL of its HTTP interface(s).
     *
     * @param urlOrEndpoint The URL of the workflow's HTTP interface, or the ID of the endpoint
     * @param requestOptions Additional request options
     * @return A future containing the response from the workflow
     */
    public CompletableFuture<Object> invoke(String urlOrEndpoint, RequestOptions requestOptions) {
        InvokeWorkflowOpts request =
                InvokeWorkflowOpts.builder().urlOrEndpoint(urlOrEndpoint).build();
        return this.rawClient.invoke(request, requestOptions).thenApply(response -> response.body());
    }

    /**
     * Invokes a workflow using the InvokeWorkflowOpts request object.
     *
     * @param request The request containing workflow invocation parameters
     * @return A future containing the response from the workflow
     */
    public CompletableFuture<Object> invoke(InvokeWorkflowOpts request) {
        return this.rawClient.invoke(request).thenApply(response -> response.body());
    }

    /**
     * Invokes a workflow using the InvokeWorkflowOpts request object.
     *
     * @param request The request containing workflow invocation parameters
     * @param requestOptions Additional request options
     * @return A future containing the response from the workflow
     */
    public CompletableFuture<Object> invoke(InvokeWorkflowOpts request, RequestOptions requestOptions) {
        return this.rawClient.invoke(request, requestOptions).thenApply(response -> response.body());
    }

    /**
     * Invokes a workflow for a Pipedream Connect user in a project.
     *
     * @param urlOrEndpoint The URL of the workflow's HTTP interface, or the ID of the endpoint
     * @param externalUserId Your end user ID, for whom you're invoking the workflow
     * @return A future containing the response from the workflow
     */
    public CompletableFuture<Object> invokeForExternalUser(String urlOrEndpoint, String externalUserId) {
        InvokeWorkflowForExternalUserOpts request = InvokeWorkflowForExternalUserOpts.builder()
                .url(urlOrEndpoint)
                .externalUserId(externalUserId)
                .build();
        return this.rawClient.invokeForExternalUser(request).thenApply(response -> response.body());
    }

    /**
     * Invokes a workflow for a Pipedream Connect user in a project.
     *
     * @param urlOrEndpoint The URL of the workflow's HTTP interface, or the ID of the endpoint
     * @param externalUserId Your end user ID, for whom you're invoking the workflow
     * @param requestOptions Additional request options
     * @return A future containing the response from the workflow
     */
    public CompletableFuture<Object> invokeForExternalUser(
            String urlOrEndpoint, String externalUserId, RequestOptions requestOptions) {
        InvokeWorkflowForExternalUserOpts request = InvokeWorkflowForExternalUserOpts.builder()
                .url(urlOrEndpoint)
                .externalUserId(externalUserId)
                .build();
        return this.rawClient.invokeForExternalUser(request, requestOptions).thenApply(response -> response.body());
    }

    /**
     * Invokes a workflow for a Pipedream Connect user using the InvokeWorkflowForExternalUserOpts request object.
     *
     * @param request The request containing workflow invocation parameters
     * @return A future containing the response from the workflow
     */
    public CompletableFuture<Object> invokeForExternalUser(InvokeWorkflowForExternalUserOpts request) {
        return this.rawClient.invokeForExternalUser(request).thenApply(response -> response.body());
    }

    /**
     * Invokes a workflow for a Pipedream Connect user using the InvokeWorkflowForExternalUserOpts request object.
     *
     * @param request The request containing workflow invocation parameters
     * @param requestOptions Additional request options
     * @return A future containing the response from the workflow
     */
    public CompletableFuture<Object> invokeForExternalUser(
            InvokeWorkflowForExternalUserOpts request, RequestOptions requestOptions) {
        return this.rawClient.invokeForExternalUser(request, requestOptions).thenApply(response -> response.body());
    }
}
