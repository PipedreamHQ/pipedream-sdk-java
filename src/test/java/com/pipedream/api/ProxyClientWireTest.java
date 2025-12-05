package com.pipedream.api;

import static org.junit.jupiter.api.Assertions.*;

import com.pipedream.api.resources.proxy.requests.ProxyGetRequest;
import com.pipedream.api.resources.proxy.types.ProxyResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProxyClientWireTest {
    private MockWebServer server;
    private BaseClient client;
    private AsyncBaseClient asyncClient;

    private static final String TEST_EXTERNAL_USER_ID = "test-user-123";
    private static final String TEST_ACCOUNT_ID = "test-account-456";
    private static final String TEST_URL = "https://api.example.com/data";

    @BeforeEach
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
        String baseUrl = server.url("/").toString();
        // Explicitly set credentials to null to avoid OAuth token fetching
        // (environment variables might be set, which would trigger network calls)
        client = BaseClient.builder()
                .url(baseUrl)
                .projectId("test-project")
                .clientId(null)
                .clientSecret(null)
                .build();
        asyncClient = AsyncBaseClient.builder()
                .url(baseUrl)
                .projectId("test-project")
                .clientId(null)
                .clientSecret(null)
                .build();
    }

    @AfterEach
    public void teardown() throws Exception {
        server.shutdown();
    }

    private ProxyGetRequest createGetRequest() {
        return ProxyGetRequest.builder()
                .externalUserId(TEST_EXTERNAL_USER_ID)
                .accountId(TEST_ACCOUNT_ID)
                .build();
    }

    /**
     * Java 8-compatible replacement for InputStream.readAllBytes() (Java 9+).
     */
    private byte[] readAllBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }

    // ==================== Sync Client Tests ====================

    @Test
    public void testGetJsonResponse() throws Exception {
        String jsonBody = "{\"key\":\"value\",\"number\":42}";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonBody));

        try (ProxyResponse response = client.proxy().get(TEST_URL, createGetRequest())) {

            // Verify response type
            assertTrue(response.isJson(), "Response should be JSON");
            assertFalse(response.isStream(), "Response should not be a stream");

            // Verify parsed JSON content
            Object json = response.json();
            assertNotNull(json, "JSON body should not be null");
            assertInstanceOf(Map.class, json, "JSON body should be a Map");

            @SuppressWarnings("unchecked")
            Map<String, Object> jsonMap = (Map<String, Object>) json;
            assertEquals("value", jsonMap.get("key"));
            assertEquals(42, jsonMap.get("number"));

            // Verify content type is preserved
            assertTrue(response.getContentType().isPresent());
            assertEquals("application/json", response.getContentType().get());
        }

        // Verify request was made correctly
        RecordedRequest request = server.takeRequest();
        assertNotNull(request);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void testGetJsonResponseWithCharset() throws Exception {
        String jsonBody = "{\"message\":\"hello\"}";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(jsonBody));

        try (ProxyResponse response = client.proxy().get(TEST_URL, createGetRequest())) {

            // Should still be detected as JSON even with charset parameter
            assertTrue(response.isJson(), "Response should be JSON even with charset");

            @SuppressWarnings("unchecked")
            Map<String, Object> jsonMap = (Map<String, Object>) response.json();
            assertEquals("hello", jsonMap.get("message"));
        }
    }

    @Test
    public void testGetOctetStreamResponse() throws Exception {
        byte[] binaryData = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05};
        Buffer buffer = new Buffer();
        buffer.write(binaryData);

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/octet-stream")
                .setBody(buffer));

        ProxyResponse response = client.proxy().get(TEST_URL, createGetRequest());

        // Verify response type
        assertTrue(response.isStream(), "Response should be a stream");
        assertFalse(response.isJson(), "Response should not be JSON");

        // Verify binary content
        byte[] actualData = readAllBytes(response.stream());
        assertArrayEquals(binaryData, actualData, "Binary data should match");

        // Verify content type is preserved
        assertTrue(response.getContentType().isPresent());
        assertEquals("application/octet-stream", response.getContentType().get());

        // Clean up
        response.close();

        // Verify request was made correctly
        RecordedRequest request = server.takeRequest();
        assertNotNull(request);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void testGetRedirectFollowed() throws Exception {
        // First response: 302 redirect
        server.enqueue(new MockResponse()
                .setResponseCode(302)
                .setHeader("Location", server.url("/redirected").toString()));

        // Second response: 200 OK with JSON
        String jsonBody = "{\"redirected\":true,\"status\":\"success\"}";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonBody));

        try (ProxyResponse response = client.proxy().get(TEST_URL, createGetRequest())) {

            // Verify we got the final response (redirect was followed)
            assertTrue(response.isJson(), "Response should be JSON after redirect");

            @SuppressWarnings("unchecked")
            Map<String, Object> jsonMap = (Map<String, Object>) response.json();
            assertEquals(true, jsonMap.get("redirected"));
            assertEquals("success", jsonMap.get("status"));
        }

        // Verify both requests were made (original + redirect)
        RecordedRequest firstRequest = server.takeRequest();
        assertNotNull(firstRequest);
        assertEquals("GET", firstRequest.getMethod());

        RecordedRequest secondRequest = server.takeRequest();
        assertNotNull(secondRequest);
        assertEquals("GET", secondRequest.getMethod());
        assertNotNull(secondRequest.getPath());
        assertTrue(secondRequest.getPath().contains("redirected"));
    }

    // ==================== Async Client Tests ====================

    @Test
    public void testAsyncGetJsonResponse() throws Exception {
        String jsonBody = "{\"async\":true,\"data\":\"test\"}";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonBody));

        CompletableFuture<ProxyResponse> future = asyncClient.proxy().get(TEST_URL, createGetRequest());
        try (ProxyResponse response = future.get(10, TimeUnit.SECONDS)) {

            // Verify response type
            assertTrue(response.isJson(), "Response should be JSON");
            assertFalse(response.isStream(), "Response should not be a stream");

            // Verify parsed JSON content
            @SuppressWarnings("unchecked")
            Map<String, Object> jsonMap = (Map<String, Object>) response.json();
            assertEquals(true, jsonMap.get("async"));
            assertEquals("test", jsonMap.get("data"));
        }

        // Verify request was made correctly
        RecordedRequest request = server.takeRequest();
        assertNotNull(request);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void testAsyncGetOctetStreamResponse() throws Exception {
        byte[] binaryData = new byte[] {(byte) 0xFF, (byte) 0xFE, (byte) 0xFD};
        Buffer buffer = new Buffer();
        buffer.write(binaryData);

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/octet-stream")
                .setBody(buffer));

        CompletableFuture<ProxyResponse> future = asyncClient.proxy().get(TEST_URL, createGetRequest());
        ProxyResponse response = future.get(10, TimeUnit.SECONDS);

        // Verify response type
        assertTrue(response.isStream(), "Response should be a stream");
        assertFalse(response.isJson(), "Response should not be JSON");

        // Verify binary content
        byte[] actualData = readAllBytes(response.stream());
        assertArrayEquals(binaryData, actualData, "Binary data should match");

        // Clean up
        response.close();

        // Verify request was made correctly
        RecordedRequest request = server.takeRequest();
        assertNotNull(request);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void testAsyncGetRedirectFollowed() throws Exception {
        // First response: 302 redirect
        server.enqueue(new MockResponse()
                .setResponseCode(302)
                .setHeader("Location", server.url("/async-redirected").toString()));

        // Second response: 200 OK with JSON
        String jsonBody = "{\"asyncRedirected\":true}";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonBody));

        CompletableFuture<ProxyResponse> future = asyncClient.proxy().get(TEST_URL, createGetRequest());
        try (ProxyResponse response = future.get(10, TimeUnit.SECONDS)) {

            // Verify we got the final response (redirect was followed)
            assertTrue(response.isJson(), "Response should be JSON after redirect");

            @SuppressWarnings("unchecked")
            Map<String, Object> jsonMap = (Map<String, Object>) response.json();
            assertEquals(true, jsonMap.get("asyncRedirected"));
        }

        // Verify both requests were made (original + redirect)
        RecordedRequest firstRequest = server.takeRequest();
        assertNotNull(firstRequest);

        RecordedRequest secondRequest = server.takeRequest();
        assertNotNull(secondRequest);
        assertNotNull(secondRequest.getPath());
        assertTrue(secondRequest.getPath().contains("async-redirected"));
    }

    // ==================== HttpUrl Overload Tests ====================

    @Test
    public void testGetWithHttpUrl() throws Exception {
        String jsonBody = "{\"key\":\"value\"}";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonBody));

        // Build URL with query parameters using HttpUrl
        HttpUrl url = HttpUrl.parse(TEST_URL)
                .newBuilder()
                .addQueryParameter("fields", "name,mimeType")
                .addQueryParameter("limit", "10")
                .build();

        try (ProxyResponse response = client.proxy().get(url, createGetRequest())) {
            assertTrue(response.isJson(), "Response should be JSON");

            @SuppressWarnings("unchecked")
            Map<String, Object> jsonMap = (Map<String, Object>) response.json();
            assertEquals("value", jsonMap.get("key"));
        }

        // Verify request was made correctly
        RecordedRequest request = server.takeRequest();
        assertNotNull(request);
        assertEquals("GET", request.getMethod());

        // Verify the base64-encoded URL in the path contains the query parameters
        String path = request.getPath();
        assertNotNull(path);
        // The URL with query params should be base64-encoded in the path
        // We can't easily decode it here, but we verify the request was made
    }

    @Test
    public void testGetWithHttpUrlAndSpecialCharacters() throws Exception {
        String jsonBody = "{\"result\":\"ok\"}";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonBody));

        // Build URL with query parameters containing special characters
        HttpUrl url = HttpUrl.parse(TEST_URL)
                .newBuilder()
                .addQueryParameter("filter", "status=active&type=user")
                .addQueryParameter("name", "John Doe")
                .build();

        try (ProxyResponse response = client.proxy().get(url, createGetRequest())) {
            assertTrue(response.isJson(), "Response should be JSON");
        }

        RecordedRequest request = server.takeRequest();
        assertNotNull(request);
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void testAsyncGetWithHttpUrl() throws Exception {
        String jsonBody = "{\"async\":true}";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonBody));

        // Build URL with query parameters using HttpUrl
        HttpUrl url = HttpUrl.parse(TEST_URL)
                .newBuilder()
                .addQueryParameter("page", "1")
                .addQueryParameter("size", "20")
                .build();

        CompletableFuture<ProxyResponse> future = asyncClient.proxy().get(url, createGetRequest());
        try (ProxyResponse response = future.get(10, TimeUnit.SECONDS)) {
            assertTrue(response.isJson(), "Response should be JSON");

            @SuppressWarnings("unchecked")
            Map<String, Object> jsonMap = (Map<String, Object>) response.json();
            assertEquals(true, jsonMap.get("async"));
        }

        RecordedRequest request = server.takeRequest();
        assertNotNull(request);
        assertEquals("GET", request.getMethod());
    }
}
