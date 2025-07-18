/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.pipedream.api.resources.users;

import com.pipedream.api.core.ClientOptions;
import com.pipedream.api.core.RequestOptions;

public class UsersClient {
    protected final ClientOptions clientOptions;

    private final RawUsersClient rawClient;

    public UsersClient(ClientOptions clientOptions) {
        this.clientOptions = clientOptions;
        this.rawClient = new RawUsersClient(clientOptions);
    }

    /**
     * Get responses with HTTP metadata like headers
     */
    public RawUsersClient withRawResponse() {
        return this.rawClient;
    }

    public void deleteExternalUser(String externalUserId) {
        this.rawClient.deleteExternalUser(externalUserId).body();
    }

    public void deleteExternalUser(String externalUserId, RequestOptions requestOptions) {
        this.rawClient.deleteExternalUser(externalUserId, requestOptions).body();
    }
}
