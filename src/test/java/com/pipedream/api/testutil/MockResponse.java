package com.pipedream.api.testutil;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import okio.Buffer;

/**
 * Drop-in replacement for {@code okhttp3.mockwebserver.MockResponse} backed by JDK primitives.
 * Exposes only the surface used by the wire tests in this repo.
 */
public final class MockResponse {
    int code = 200;
    final Map<String, String> headers = new LinkedHashMap<>();
    byte[] body = new byte[0];

    public MockResponse setResponseCode(int code) {
        this.code = code;
        return this;
    }

    public MockResponse setHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public MockResponse setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public MockResponse setBody(Buffer body) {
        this.body = body.readByteArray();
        return this;
    }
}
