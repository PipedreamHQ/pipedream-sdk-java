/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.pipedream.api.resources.actions.requests;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pipedream.api.core.ObjectMappers;
import com.pipedream.api.types.ConfigurePropOpts;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(builder = ActionsConfigurePropRequest.Builder.class)
public final class ActionsConfigurePropRequest {
    private final Optional<String> asyncHandle;

    private final ConfigurePropOpts body;

    private final Map<String, Object> additionalProperties;

    private ActionsConfigurePropRequest(
            Optional<String> asyncHandle, ConfigurePropOpts body, Map<String, Object> additionalProperties) {
        this.asyncHandle = asyncHandle;
        this.body = body;
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("x-async-handle")
    public Optional<String> getAsyncHandle() {
        return asyncHandle;
    }

    @JsonProperty("body")
    public ConfigurePropOpts getBody() {
        return body;
    }

    @java.lang.Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof ActionsConfigurePropRequest && equalTo((ActionsConfigurePropRequest) other);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    private boolean equalTo(ActionsConfigurePropRequest other) {
        return asyncHandle.equals(other.asyncHandle) && body.equals(other.body);
    }

    @java.lang.Override
    public int hashCode() {
        return Objects.hash(this.asyncHandle, this.body);
    }

    @java.lang.Override
    public String toString() {
        return ObjectMappers.stringify(this);
    }

    public static BodyStage builder() {
        return new Builder();
    }

    public interface BodyStage {
        _FinalStage body(@NotNull ConfigurePropOpts body);

        Builder from(ActionsConfigurePropRequest other);
    }

    public interface _FinalStage {
        ActionsConfigurePropRequest build();

        _FinalStage asyncHandle(Optional<String> asyncHandle);

        _FinalStage asyncHandle(String asyncHandle);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder implements BodyStage, _FinalStage {
        private ConfigurePropOpts body;

        private Optional<String> asyncHandle = Optional.empty();

        @JsonAnySetter
        private Map<String, Object> additionalProperties = new HashMap<>();

        private Builder() {}

        @java.lang.Override
        public Builder from(ActionsConfigurePropRequest other) {
            asyncHandle(other.getAsyncHandle());
            body(other.getBody());
            return this;
        }

        @java.lang.Override
        @JsonSetter("body")
        public _FinalStage body(@NotNull ConfigurePropOpts body) {
            this.body = Objects.requireNonNull(body, "body must not be null");
            return this;
        }

        @java.lang.Override
        public _FinalStage asyncHandle(String asyncHandle) {
            this.asyncHandle = Optional.ofNullable(asyncHandle);
            return this;
        }

        @java.lang.Override
        @JsonSetter(value = "x-async-handle", nulls = Nulls.SKIP)
        public _FinalStage asyncHandle(Optional<String> asyncHandle) {
            this.asyncHandle = asyncHandle;
            return this;
        }

        @java.lang.Override
        public ActionsConfigurePropRequest build() {
            return new ActionsConfigurePropRequest(asyncHandle, body, additionalProperties);
        }
    }
}
