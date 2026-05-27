/**
 * This file was manually created to add workflow invocation support.
 */
package com.pipedream.api.resources.workflows.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pipedream.api.types.HTTPAuthType;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = InvokeWorkflowForExternalUserOpts.Builder.class)
public final class InvokeWorkflowForExternalUserOpts {
    private final String url;

    private final String externalUserId;

    private final Optional<Object> body;

    private final Optional<Map<String, String>> headers;

    private final Optional<String> method;

    private final Optional<HTTPAuthType> authType;

    private InvokeWorkflowForExternalUserOpts(
            String url,
            String externalUserId,
            Optional<Object> body,
            Optional<Map<String, String>> headers,
            Optional<String> method,
            Optional<HTTPAuthType> authType) {
        this.url = url;
        this.externalUserId = externalUserId;
        this.body = body;
        this.headers = headers;
        this.method = method;
        this.authType = authType;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("externalUserId")
    public String getExternalUserId() {
        return externalUserId;
    }

    @JsonProperty("body")
    public Optional<Object> getBody() {
        return body;
    }

    @JsonProperty("headers")
    public Optional<Map<String, String>> getHeaders() {
        return headers;
    }

    @JsonProperty("method")
    public Optional<String> getMethod() {
        return method;
    }

    @JsonProperty("authType")
    public Optional<HTTPAuthType> getAuthType() {
        return authType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof InvokeWorkflowForExternalUserOpts && equalTo((InvokeWorkflowForExternalUserOpts) other);
    }

    private boolean equalTo(InvokeWorkflowForExternalUserOpts other) {
        return url.equals(other.url)
                && externalUserId.equals(other.externalUserId)
                && body.equals(other.body)
                && headers.equals(other.headers)
                && method.equals(other.method)
                && authType.equals(other.authType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.url, this.externalUserId, this.body, this.headers, this.method, this.authType);
    }

    @Override
    public String toString() {
        return "InvokeWorkflowForExternalUserOpts{url: " + url + ", externalUserId: " + externalUserId + ", body: "
                + body + ", headers: " + headers + ", method: " + method + ", authType: " + authType + "}";
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private String url;

        private String externalUserId;

        private Optional<Object> body = Optional.empty();

        private Optional<Map<String, String>> headers = Optional.empty();

        private Optional<String> method = Optional.empty();

        private Optional<HTTPAuthType> authType = Optional.empty();

        private Builder() {}

        @JsonSetter("url")
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        @JsonSetter("externalUserId")
        public Builder externalUserId(String externalUserId) {
            this.externalUserId = externalUserId;
            return this;
        }

        @JsonSetter(value = "body", nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
        public Builder body(Optional<Object> body) {
            this.body = body;
            return this;
        }

        public Builder body(Object body) {
            this.body = Optional.ofNullable(body);
            return this;
        }

        @JsonSetter(value = "headers", nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
        public Builder headers(Optional<Map<String, String>> headers) {
            this.headers = headers;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = Optional.ofNullable(headers);
            return this;
        }

        @JsonSetter(value = "method", nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
        public Builder method(Optional<String> method) {
            this.method = method;
            return this;
        }

        public Builder method(String method) {
            this.method = Optional.ofNullable(method);
            return this;
        }

        @JsonSetter(value = "authType", nulls = com.fasterxml.jackson.annotation.Nulls.SKIP)
        public Builder authType(Optional<HTTPAuthType> authType) {
            this.authType = authType;
            return this;
        }

        public Builder authType(HTTPAuthType authType) {
            this.authType = Optional.ofNullable(authType);
            return this;
        }

        public InvokeWorkflowForExternalUserOpts build() {
            return new InvokeWorkflowForExternalUserOpts(url, externalUserId, body, headers, method, authType);
        }
    }
}
