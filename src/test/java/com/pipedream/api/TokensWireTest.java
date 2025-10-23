package com.pipedream.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pipedream.api.core.ObjectMappers;
import com.pipedream.api.resources.tokens.requests.TokensValidateRequest;
import com.pipedream.api.types.ValidateTokenResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TokensWireTest {
    private MockWebServer server;
    private BaseClient client;
    private ObjectMapper objectMapper = ObjectMappers.JSON_MAPPER;

    @BeforeEach
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
        client = BaseClient.builder()
                .url(server.url("/").toString())
                .token("oauth-test-token")
                .build();
    }

    @AfterEach
    public void teardown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testValidate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"app\":{\"id\":\"id\",\"name_slug\":\"name_slug\",\"name\":\"name\",\"auth_type\":\"keys\",\"description\":\"description\",\"img_src\":\"img_src\",\"custom_fields_json\":\"custom_fields_json\",\"categories\":[\"categories\"],\"featured_weight\":1.1},\"error\":\"error\",\"error_redirect_uri\":\"error_redirect_uri\",\"oauth_app_id\":\"oauth_app_id\",\"project_app_name\":\"project_app_name\",\"project_environment\":\"project_environment\",\"project_id\":\"project_id\",\"project_support_email\":\"project_support_email\",\"success\":true,\"success_redirect_uri\":\"success_redirect_uri\"}"));
        ValidateTokenResponse response = client.tokens()
                .validate(
                        "ctok",
                        TokensValidateRequest.builder()
                                .appId("app_id")
                                .oauthAppId("oauth_app_id")
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"app\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"name_slug\": \"name_slug\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"auth_type\": \"keys\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"img_src\": \"img_src\",\n"
                + "    \"custom_fields_json\": \"custom_fields_json\",\n"
                + "    \"categories\": [\n"
                + "      \"categories\"\n"
                + "    ],\n"
                + "    \"featured_weight\": 1.1\n"
                + "  },\n"
                + "  \"error\": \"error\",\n"
                + "  \"error_redirect_uri\": \"error_redirect_uri\",\n"
                + "  \"oauth_app_id\": \"oauth_app_id\",\n"
                + "  \"project_app_name\": \"project_app_name\",\n"
                + "  \"project_environment\": \"project_environment\",\n"
                + "  \"project_id\": \"project_id\",\n"
                + "  \"project_support_email\": \"project_support_email\",\n"
                + "  \"success\": true,\n"
                + "  \"success_redirect_uri\": \"success_redirect_uri\"\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertEquals(
                expectedResponseNode, actualResponseNode, "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }
}
