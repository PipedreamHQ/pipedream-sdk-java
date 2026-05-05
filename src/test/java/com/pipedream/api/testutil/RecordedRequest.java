package com.pipedream.api.testutil;

import java.nio.charset.StandardCharsets;

/**
 * Drop-in replacement for {@code okhttp3.mockwebserver.RecordedRequest}. Exposes only
 * the {@code getMethod()}, {@code getPath()}, and {@code getBody().readUtf8()} surface
 * used by the wire tests in this repo.
 */
public final class RecordedRequest {
    private final String method;
    private final String path;
    private final byte[] body;

    public RecordedRequest(String method, String path, byte[] body) {
        this.method = method;
        this.path = path;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Body getBody() {
        return new Body(body);
    }

    /** Minimal stand-in for {@code okio.Buffer} as exposed by {@code RecordedRequest.getBody()}. */
    public static final class Body {
        private final byte[] bytes;

        Body(byte[] bytes) {
            this.bytes = bytes;
        }

        public String readUtf8() {
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }
}
