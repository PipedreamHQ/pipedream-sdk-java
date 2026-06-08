package com.pipedream.api;

import com.pipedream.api.testutil.MockResponse;
import com.pipedream.api.testutil.MockWebServer;
import com.pipedream.api.testutil.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * When authenticating with a Connect token, callers may omit the project ID (the backend derives
 * it from the token). The client keeps it as "" and the ConnectPathNormalizationInterceptor strips
 * the resulting empty path segment, so requests are prefixed by `/v1/connect/` of `/v1/connect//`.
 */
public class ConnectTokenProjectIdWireTest {
    private MockWebServer server;

    @BeforeEach
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    public void teardown() throws Exception {
        server.shutdown();
    }

    @Test
    public void tokenAuthWithoutProjectIdCollapsesConnectPath() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"data\":[],\"page_info\":{}}"));

        // new PipedreamClientBuilder() (vs PipedreamClient.builder()) avoids pulling
        // PIPEDREAM_PROJECT_ID from the environment, keeping the test deterministic.
        PipedreamClient client = new PipedreamClientBuilder()
                .url(server.url("/").toString())
                .token("ctok")
                .build();

        client.accounts().list();

        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());
        Assertions.assertTrue(
                request.getPath().contains("/v1/connect/accounts"),
                "expected /v1/connect/accounts, got: " + request.getPath());
        Assertions.assertFalse(
                request.getPath().contains("/v1/connect//accounts"),
                "empty project segment should be stripped, got: " + request.getPath());
    }
}
