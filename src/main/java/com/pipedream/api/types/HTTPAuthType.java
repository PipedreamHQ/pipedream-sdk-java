/**
 * This file was manually created to add workflow invocation support.
 */
package com.pipedream.api.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Authentication types for workflow invocation
 */
public enum HTTPAuthType {
    NONE("none"),
    STATIC_BEARER("static_bearer_token"),
    OAUTH("oauth");

    private final String value;

    HTTPAuthType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static HTTPAuthType fromValue(String value) {
        for (HTTPAuthType type : HTTPAuthType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown HTTPAuthType: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
