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
@JsonDeserialize(builder = InvokeWorkflowOpts.Builder.class)
public final class InvokeWorkflowOpts {
    private final String urlOrEndpoint;

    private final Optional<Object> body;

    private final Optional<Map<String, String>> headers;

    private final Optional<String> method;

    private final Optional<HTTPAuthType> authType;

    private InvokeWorkflowOpts(
            String urlOrEndpoint,
            Optional<Object> body,
            Optional<Map<String, String>> headers,
            Optional<String> method,
            Optional<HTTPAuthType> authType) {
        this.urlOrEndpoint = urlOrEndpoint;
        this.body = body;
        this.headers = headers;
        this.method = method;
        this.authType = authType;
    }

    @JsonProperty("urlOrEndpoint")
    public String getUrlOrEndpoint() {
        return urlOrEndpoint;
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
        return other instanceof InvokeWorkflowOpts && equalTo((InvokeWorkflowOpts) other);
    }

    private boolean equalTo(InvokeWorkflowOpts other) {
        return urlOrEndpoint.equals(other.urlOrEndpoint)
                && body.equals(other.body)
                && headers.equals(other.headers)
                && method.equals(other.method)
                && authType.equals(other.authType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.urlOrEndpoint, this.body, this.headers, this.method, this.authType);
    }

    @Override
    public String toString() {
        return "InvokeWorkflowOpts{urlOrEndpoint: " + urlOrEndpoint + ", body: " + body + ", headers: " + headers
                + ", method: " + method + ", authType: " + authType + "}";
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private String urlOrEndpoint;

        private Optional<Object> body = Optional.empty();

        private Optional<Map<String, String>> headers = Optional.empty();

        private Optional<String> method = Optional.empty();

        private Optional<HTTPAuthType> authType = Optional.empty();

        private Builder() {}

        @JsonSetter("urlOrEndpoint")
        public Builder urlOrEndpoint(String urlOrEndpoint) {
            this.urlOrEndpoint = urlOrEndpoint;
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

        public InvokeWorkflowOpts build() {
            return new InvokeWorkflowOpts(urlOrEndpoint, body, headers, method, authType);
        }
    }
}
