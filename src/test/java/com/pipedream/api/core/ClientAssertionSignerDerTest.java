package com.pipedream.api.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** The DER-to-raw ECDSA signature conversion used for ES256. */
public class ClientAssertionSignerDerTest {
    @Test
    public void padsShortIntegersAndDropsSignBytes() {
        // Arrange: r is 33 bytes (a 0x00 sign byte, then 0xff...), s is 31 bytes (one leading zero byte omitted).
        byte[] r = new byte[33];
        r[0] = 0x00;
        for (int i = 1; i < 33; i++) r[i] = (byte) 0xff;
        byte[] s = new byte[31];
        for (int i = 0; i < 31; i++) s[i] = 0x11;
        byte[] der = new byte[2 + 2 + r.length + 2 + s.length];
        der[0] = 0x30;
        der[1] = (byte) (der.length - 2);
        der[2] = 0x02;
        der[3] = (byte) r.length;
        System.arraycopy(r, 0, der, 4, r.length);
        der[4 + r.length] = 0x02;
        der[5 + r.length] = (byte) s.length;
        System.arraycopy(s, 0, der, 6 + r.length, s.length);

        // Act
        byte[] raw = ClientAssertionSigner.derToRaw(der);

        // Assert
        Assertions.assertEquals(64, raw.length);
        for (int i = 0; i < 32; i++) Assertions.assertEquals((byte) 0xff, raw[i]);
        Assertions.assertEquals(0x00, raw[32]);
        for (int i = 33; i < 64; i++) Assertions.assertEquals(0x11, raw[i]);
    }
}
