/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.pipedream.api.resources.accounts.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pipedream.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = AccountsRetrieveRequest.Builder.class)
public final class AccountsRetrieveRequest {
    private final Optional<Boolean> includeCredentials;

    private final Map<String, Object> additionalProperties;

    private AccountsRetrieveRequest(Optional<Boolean> includeCredentials, Map<String, Object> additionalProperties) {
        this.includeCredentials = includeCredentials;
        this.additionalProperties = additionalProperties;
    }

    /**
     * @return Whether to retrieve the account's credentials or not
     */
    @JsonProperty("include_credentials")
    public Optional<Boolean> getIncludeCredentials() {
        return includeCredentials;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof AccountsRetrieveRequest && equalTo((AccountsRetrieveRequest) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(AccountsRetrieveRequest other) {
        return includeCredentials.equals(other.includeCredentials);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.includeCredentials);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private Optional<Boolean> includeCredentials = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        public Builder from(AccountsRetrieveRequest other) {
            includeCredentials(other.getIncludeCredentials());
            return this;
        }

        /**
         * <p>Whether to retrieve the account's credentials or not</p>
         */
        @JsonSetter(value = "include_credentials", nulls = Nulls.SKIP)
        public Builder includeCredentials(Optional<Boolean> includeCredentials) {
            this.includeCredentials = includeCredentials;
            return this;
        }

        public Builder includeCredentials(Boolean includeCredentials) {
            this.includeCredentials = Optional.ofNullable(includeCredentials);
            return this;
        }

        public AccountsRetrieveRequest build() {
            return new AccountsRetrieveRequest(includeCredentials, additionalProperties);
        }
    }
}
