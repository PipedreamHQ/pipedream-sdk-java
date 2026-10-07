package com.pipedream.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pipedream.api.core.BaseClientApiException;
import com.pipedream.api.core.ClientAssertionSigner;
import com.pipedream.api.testutil.MockResponse;
import com.pipedream.api.testutil.MockWebServer;
import com.pipedream.api.testutil.RecordedRequest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Private key (private_key_jwt) client authentication. */
public class PrivateKeyJwtTest {
    private static final String ASSERTION_TYPE = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";
    private static final ObjectMapper MAPPER = new ObjectMapper();

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

    // ── Signer ────────────────────────────────────────────────────────────────

    @Test
    public void signsEs256WithTheClientsClaimsAndAFreshJti() throws Exception {
        // Arrange
        KeyPair pair = ecKeyPair("secp256r1");
        ClientAssertionSigner signer =
                new ClientAssertionSigner("client_123", pem(pair.getPrivate()), null, "https://api.pipedream.com");

        // Act
        String first = signer.sign();
        String second = signer.sign();

        // Assert
        Assertions.assertEquals("{\"alg\":\"ES256\",\"typ\":\"client-authentication+jwt\"}", decodePart(first, 0));
        JsonNode claims = MAPPER.readTree(decodePart(first, 1));
        Assertions.assertEquals("client_123", claims.get("iss").asText());
        Assertions.assertEquals("client_123", claims.get("sub").asText());
        Assertions.assertEquals("https://api.pipedream.com", claims.get("aud").asText());
        Assertions.assertEquals(
                60, claims.get("exp").asLong() - claims.get("iat").asLong());
        Assertions.assertNotEquals(
                claims.get("jti").asText(),
                MAPPER.readTree(decodePart(second, 1)).get("jti").asText());
        Assertions.assertTrue(verifies(first, pair.getPublic()));
    }

    @Test
    public void es256SignaturesAlwaysVerify() throws Exception {
        // Many signatures, so r and s values with leading zero bytes (shorter DER INTEGERs) and with
        // a high bit (a leading 0x00 sign byte) are both exercised by the DER-to-raw conversion.
        KeyPair pair = ecKeyPair("secp256r1");
        ClientAssertionSigner signer = new ClientAssertionSigner("c", pem(pair.getPrivate()), null, "aud");
        for (int i = 0; i < 300; i++) {
            Assertions.assertTrue(verifies(signer.sign(), pair.getPublic()));
        }
    }

    @Test
    public void signsRs256WithAnRsaKeyAndSendsTheKeyId() throws Exception {
        // Arrange
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        ClientAssertionSigner signer =
                new ClientAssertionSigner("c", pem(pair.getPrivate()), "key-2026", "https://api.pipedream.com");

        // Act
        String assertion = signer.sign();

        // Assert
        JsonNode header = MAPPER.readTree(decodePart(assertion, 0));
        Assertions.assertEquals("RS256", header.get("alg").asText());
        Assertions.assertEquals("key-2026", header.get("kid").asText());
        Assertions.assertTrue(verifies(assertion, pair.getPublic()));
    }

    @Test
    public void acceptsAJwkPrivateKeyAndUsesItsKid() throws Exception {
        // Arrange
        KeyPair pair = ecKeyPair("secp256r1");
        String d = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(unsigned32(((ECPrivateKey) pair.getPrivate()).getS()));
        String jwk = "{\"kty\":\"EC\",\"crv\":\"P-256\",\"d\":\"" + d + "\",\"kid\":\"jwk-kid\"}";
        ClientAssertionSigner signer = new ClientAssertionSigner("c", jwk, null, "https://api.pipedream.com");

        // Act
        String assertion = signer.sign();

        // Assert
        Assertions.assertEquals(
                "jwk-kid", MAPPER.readTree(decodePart(assertion, 0)).get("kid").asText());
        Assertions.assertTrue(verifies(assertion, pair.getPublic()));
    }

    @Test
    public void rejectsKeysItCannotUse() throws Exception {
        String publicKey = "-----BEGIN PUBLIC KEY-----\n"
                + Base64.getMimeEncoder()
                        .encodeToString(ecKeyPair("secp256r1").getPublic().getEncoded())
                + "\n-----END PUBLIC KEY-----";
        String[] keys = {
            publicKey,
            pem(ecKeyPair("secp384r1").getPrivate()),
            "-----BEGIN EC PRIVATE KEY-----\nMHcCAQEE\n-----END EC PRIVATE KEY-----",
            "not a key",
        };
        for (String key : keys) {
            IllegalArgumentException e = Assertions.assertThrows(
                    IllegalArgumentException.class, () -> new ClientAssertionSigner("c", key, null, "aud"));
            Assertions.assertTrue(e.getMessage().startsWith("privateKey must be"), e.getMessage());
        }
    }

    // ── Client ────────────────────────────────────────────────────────────────

    @Test
    public void authenticatesWithAClientAssertionAndCachesTheToken() throws Exception {
        // Arrange
        KeyPair pair = ecKeyPair("secp256r1");
        enqueueToken("token-1");
        enqueueAccounts();
        enqueueAccounts();
        PipedreamClient client = keyClient(pem(pair.getPrivate()));

        // Act
        client.accounts().list();
        client.accounts().list();

        // Assert: one token request, then two API calls.
        RecordedRequest tokenRequest = server.takeRequest();
        Assertions.assertEquals("/v1/oauth/token", tokenRequest.getPath());
        JsonNode body = MAPPER.readTree(tokenRequest.getBody().readUtf8());
        Assertions.assertEquals("client_credentials", body.get("grant_type").asText());
        Assertions.assertEquals("client_123", body.get("client_id").asText());
        Assertions.assertEquals(
                ASSERTION_TYPE, body.get("client_assertion_type").asText());
        Assertions.assertFalse(body.has("client_secret"));
        String assertion = body.get("client_assertion").asText();
        Assertions.assertTrue(verifies(assertion, pair.getPublic()));
        Assertions.assertEquals(
                "kid-1", MAPPER.readTree(decodePart(assertion, 0)).get("kid").asText());
        // The audience is the API's issuer: the base URL's origin.
        Assertions.assertEquals(
                issuerOf(server.url("/").toString()),
                MAPPER.readTree(decodePart(assertion, 1)).get("aud").asText());
        Assertions.assertTrue(server.takeRequest().getPath().contains("/accounts"));
        Assertions.assertTrue(server.takeRequest().getPath().contains("/accounts"));
    }

    @Test
    public void retriesATransientFailureWithANewlySignedAssertion() throws Exception {
        // Arrange
        server.enqueue(new MockResponse().setResponseCode(503).setBody("{\"error\":\"temporarily_unavailable\"}"));
        enqueueToken("token-2");
        enqueueAccounts();
        PipedreamClient client = keyClient(pem(ecKeyPair("secp256r1").getPrivate()));

        // Act
        client.accounts().list();

        // Assert
        String firstJti = jtiOf(server.takeRequest());
        String secondJti = jtiOf(server.takeRequest());
        Assertions.assertNotEquals(firstJti, secondJti);
    }

    @Test
    public void doesNotRetryARejectedAssertion() throws Exception {
        // Arrange
        server.enqueue(new MockResponse().setResponseCode(401).setBody("{\"error\":\"invalid_client\"}"));
        // A retry would get this token and move on, so the 401 surfacing proves there was none.
        enqueueToken("unused");
        PipedreamClient client = keyClient(pem(ecKeyPair("secp256r1").getPrivate()));

        // Act
        BaseClientApiException e = Assertions.assertThrows(
                BaseClientApiException.class, () -> client.accounts().list());

        // Assert
        Assertions.assertEquals(401, e.statusCode());
        Assertions.assertEquals("/v1/oauth/token", server.takeRequest().getPath());
    }

    @Test
    public void refusesAClientSecretAndAPrivateKeyTogether() throws Exception {
        String key = pem(ecKeyPair("secp256r1").getPrivate());
        IllegalStateException e =
                Assertions.assertThrows(IllegalStateException.class, () -> new PipedreamClientBuilder()
                        .url(server.url("/").toString())
                        .clientId("c")
                        .clientSecret("s")
                        .privateKey(key)
                        .build());
        Assertions.assertTrue(e.getMessage().contains("not both"));
    }

    @Test
    public void anExplicitPrivateKeyOverridesASecretFromTheEnvironment() throws Exception {
        // Arrange
        enqueueToken("token-1");
        enqueueAccounts();
        PipedreamClient client = new PipedreamClientBuilder()
                .url(server.url("/").toString())
                .clientId("client_123")
                .environmentCredentials("env-secret", null, null)
                .privateKey(pem(ecKeyPair("secp256r1").getPrivate()))
                .projectId("proj_123")
                .build();

        // Act
        client.accounts().list();

        // Assert
        JsonNode body = MAPPER.readTree(server.takeRequest().getBody().readUtf8());
        Assertions.assertTrue(body.has("client_assertion"));
        Assertions.assertFalse(body.has("client_secret"));
    }

    @Test
    public void refusesBothCredentialsFromTheEnvironment() throws Exception {
        String key = pem(ecKeyPair("secp256r1").getPrivate());
        IllegalStateException e =
                Assertions.assertThrows(IllegalStateException.class, () -> new PipedreamClientBuilder()
                        .url(server.url("/").toString())
                        .clientId("c")
                        .environmentCredentials("env-secret", key, null)
                        .build());
        Assertions.assertTrue(e.getMessage().contains("set only the one your client uses"));
    }

    @Test
    public void asyncClientAuthenticatesWithAClientAssertion() throws Exception {
        // Arrange
        enqueueToken("token-1");
        enqueueAccounts();
        AsyncPipedreamClient client = new AsyncPipedreamClientBuilder()
                .url(server.url("/").toString())
                .clientId("client_123")
                .privateKey(pem(ecKeyPair("secp256r1").getPrivate()))
                .projectId("proj_123")
                .build();

        // Act
        client.accounts().list().get();

        // Assert
        JsonNode body = MAPPER.readTree(server.takeRequest().getBody().readUtf8());
        Assertions.assertEquals(
                ASSERTION_TYPE, body.get("client_assertion_type").asText());
        Assertions.assertFalse(body.has("client_secret"));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private PipedreamClient keyClient(String privateKey) {
        // new PipedreamClientBuilder() (vs PipedreamClient.builder()) keeps PIPEDREAM_* environment
        // variables out of the test.
        return new PipedreamClientBuilder()
                .url(server.url("/").toString())
                .clientId("client_123")
                .privateKey(privateKey)
                .keyId("kid-1")
                .projectId("proj_123")
                .maxRetries(0)
                .build();
    }

    private void enqueueToken(String accessToken) {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"access_token\":\"" + accessToken + "\",\"token_type\":\"bearer\",\"expires_in\":3600}"));
    }

    private void enqueueAccounts() {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"data\":[],\"page_info\":{}}"));
    }

    private static String jtiOf(RecordedRequest request) throws Exception {
        String assertion = MAPPER.readTree(request.getBody().readUtf8())
                .get("client_assertion")
                .asText();
        return MAPPER.readTree(decodePart(assertion, 1)).get("jti").asText();
    }

    private static String issuerOf(String url) {
        okhttp3.HttpUrl parsed = okhttp3.HttpUrl.parse(url);
        return parsed.scheme() + "://" + parsed.host() + ":" + parsed.port();
    }

    private static KeyPair ecKeyPair(String curve) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
        generator.initialize(new ECGenParameterSpec(curve));
        return generator.generateKeyPair();
    }

    private static String pem(PrivateKey key) {
        return "-----BEGIN PRIVATE KEY-----\n" + Base64.getMimeEncoder().encodeToString(key.getEncoded())
                + "\n-----END PRIVATE KEY-----\n";
    }

    private static byte[] unsigned32(BigInteger value) {
        byte[] bytes = value.toByteArray();
        if (bytes.length == 32) {
            return bytes;
        }
        byte[] out = new byte[32];
        int copy = Math.min(bytes.length, 32);
        System.arraycopy(bytes, bytes.length - copy, out, 32 - copy, copy);
        return out;
    }

    private static String decodePart(String jwt, int index) {
        return new String(Base64.getUrlDecoder().decode(jwt.split("\\.")[index]), StandardCharsets.UTF_8);
    }

    private static boolean verifies(String jwt, PublicKey publicKey) throws Exception {
        String[] parts = jwt.split("\\.");
        String alg = MAPPER.readTree(decodePart(jwt, 0)).get("alg").asText();
        // Tests run on JDK 11+, which verifies the raw r||s (IEEE P1363) form JWS uses directly.
        Signature signature =
                Signature.getInstance("ES256".equals(alg) ? "SHA256withECDSAinP1363Format" : "SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update((parts[0] + "." + parts[1]).getBytes(StandardCharsets.US_ASCII));
        return signature.verify(Base64.getUrlDecoder().decode(parts[2]));
    }
}
