package com.pipedream.api.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Signs client assertions for private_key_jwt client authentication (RFC 7523): an OAuth client
 * that registered a public key authenticates at the token endpoint with a short-lived JWT signed by
 * the matching private key, instead of a client secret.
 *
 * <p>Each call to {@link #sign()} returns a new JWT with {@code iss} and {@code sub} set to the
 * client ID, the given {@code aud}, a 60-second lifetime, and a random single-use {@code jti}. The
 * private key may be an unencrypted PKCS#8 PEM ({@code -----BEGIN PRIVATE KEY-----}) or a JWK as a
 * JSON string; EC P-256 keys sign with ES256 and RSA keys with RS256. The key ID is sent as the
 * {@code kid} header and defaults to the JWK's {@code kid}, if any. Uses only the JDK.
 */
public final class ClientAssertionSigner {
    public static final String CLIENT_ASSERTION_TYPE = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";

    // Assertions are single-use and only need to outlive one token request.
    private static final long ASSERTION_LIFETIME_SECONDS = 60;

    private static final int P256_COORDINATE_BYTES = 32;

    private final String clientId;
    private final String audience;
    private final PrivateKey key;
    private final String alg;
    private final String keyId;

    /**
     * @throws IllegalArgumentException if the key can't be read or isn't an EC P-256 or RSA private
     *     key
     */
    public ClientAssertionSigner(String clientId, String privateKey, String keyId, String audience) {
        this.clientId = clientId;
        this.audience = audience;
        String text = privateKey.trim();
        String jwkKid = null;
        try {
            if (text.startsWith("{")) {
                JsonNode jwk = ObjectMappers.JSON_MAPPER.readTree(text);
                this.key = keyFromJwk(jwk);
                jwkKid = jwk.hasNonNull("kid") ? jwk.get("kid").asText() : null;
            } else {
                this.key = keyFromPem(text);
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "privateKey must be an unencrypted PKCS#8 PEM private key or a JWK (JSON). If you passed a"
                            + " public key, pass the private key instead.",
                    e);
        }
        if (key instanceof ECPrivateKey && isP256(((ECPrivateKey) key).getParams())) {
            this.alg = "ES256";
        } else if (key instanceof RSAPrivateKey) {
            this.alg = "RS256";
        } else {
            throw new IllegalArgumentException("privateKey must be an EC P-256 key (ES256) or an RSA key (RS256)");
        }
        this.keyId = keyId != null ? keyId : jwkKid;
    }

    /** Signs a new client assertion. */
    public String sign() {
        long now = System.currentTimeMillis() / 1000;
        Map<String, Object> header = new LinkedHashMap<>();
        header.put("alg", alg);
        header.put("typ", "client-authentication+jwt");
        if (keyId != null) {
            header.put("kid", keyId);
        }
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("iss", clientId);
        claims.put("sub", clientId);
        claims.put("aud", audience);
        claims.put("iat", now);
        claims.put("exp", now + ASSERTION_LIFETIME_SECONDS);
        claims.put("jti", UUID.randomUUID().toString());
        try {
            String signingInput = base64Url(ObjectMappers.JSON_MAPPER.writeValueAsBytes(header)) + "."
                    + base64Url(ObjectMappers.JSON_MAPPER.writeValueAsBytes(claims));
            Signature signature = Signature.getInstance("ES256".equals(alg) ? "SHA256withECDSA" : "SHA256withRSA");
            signature.initSign(key);
            signature.update(signingInput.getBytes(StandardCharsets.US_ASCII));
            byte[] sig = signature.sign();
            // JWS ES256 signatures are the raw r||s pair, not the DER encoding the JDK produces.
            return signingInput + "." + base64Url("ES256".equals(alg) ? derToRaw(sig) : sig);
        } catch (GeneralSecurityException | JsonProcessingException e) {
            throw new BaseClientException("Failed to sign client assertion", e);
        }
    }

    private static PrivateKey keyFromPem(String pem) throws GeneralSecurityException {
        if (!pem.contains("-----BEGIN PRIVATE KEY-----")) {
            throw new IllegalArgumentException(
                    "privateKey must be an unencrypted PKCS#8 PEM private key (-----BEGIN PRIVATE KEY-----) or a"
                            + " JWK. If you passed a public key, pass the private key instead. Convert other PEM"
                            + " formats with: openssl pkcs8 -topk8 -nocrypt -in key.pem");
        }
        String body = pem.replaceAll("-----(BEGIN|END) PRIVATE KEY-----", "").replaceAll("\\s", "");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(body));
        try {
            return KeyFactory.getInstance("EC").generatePrivate(spec);
        } catch (GeneralSecurityException notEc) {
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        }
    }

    private static PrivateKey keyFromJwk(JsonNode jwk) throws GeneralSecurityException {
        String kty = jwk.path("kty").asText();
        if ("EC".equals(kty)) {
            if (!"P-256".equals(jwk.path("crv").asText())) {
                throw new IllegalArgumentException("privateKey must be an EC P-256 key (ES256) or an RSA key (RS256)");
            }
            return KeyFactory.getInstance("EC").generatePrivate(new ECPrivateKeySpec(member(jwk, "d"), p256()));
        }
        if ("RSA".equals(kty)) {
            BigInteger n = member(jwk, "n");
            BigInteger d = member(jwk, "d");
            KeyFactory rsa = KeyFactory.getInstance("RSA");
            if (jwk.hasNonNull("p")) {
                return rsa.generatePrivate(new RSAPrivateCrtKeySpec(
                        n,
                        member(jwk, "e"),
                        d,
                        member(jwk, "p"),
                        member(jwk, "q"),
                        member(jwk, "dp"),
                        member(jwk, "dq"),
                        member(jwk, "qi")));
            }
            return rsa.generatePrivate(new RSAPrivateKeySpec(n, d));
        }
        throw new IllegalArgumentException("privateKey must be an EC P-256 key (ES256) or an RSA key (RS256)");
    }

    private static BigInteger member(JsonNode jwk, String name) {
        if (!jwk.hasNonNull(name)) {
            throw new IllegalArgumentException("privateKey JWK is missing \"" + name
                    + "\". If you passed a public key, pass the private key instead.");
        }
        return new BigInteger(1, Base64.getUrlDecoder().decode(jwk.get(name).asText()));
    }

    private static ECParameterSpec p256() throws GeneralSecurityException {
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("EC");
        parameters.init(new ECGenParameterSpec("secp256r1"));
        return parameters.getParameterSpec(ECParameterSpec.class);
    }

    private static boolean isP256(ECParameterSpec params) {
        try {
            ECParameterSpec p256 = p256();
            return params.getOrder().equals(p256.getOrder())
                    && params.getGenerator().equals(p256.getGenerator());
        } catch (GeneralSecurityException e) {
            return false;
        }
    }

    // Converts a DER-encoded ECDSA signature (SEQUENCE { INTEGER r, INTEGER s }) to the fixed-length
    // r||s form JWS uses.
    static byte[] derToRaw(byte[] der) {
        int offset = 2;
        if ((der[1] & 0xff) == 0x81) {
            offset = 3; // long-form sequence length
        }
        byte[] raw = new byte[2 * P256_COORDINATE_BYTES];
        for (int part = 0; part < 2; part++) {
            int length = der[offset + 1];
            int start = offset + 2;
            int copy = Math.min(length, P256_COORDINATE_BYTES);
            // INTEGERs are big-endian and may carry a leading 0x00 sign byte, or be shorter than 32 bytes.
            System.arraycopy(der, start + length - copy, raw, (part + 1) * P256_COORDINATE_BYTES - copy, copy);
            offset = start + length;
        }
        return raw;
    }

    private static String base64Url(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
