package org.evoting.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.evoting.cia.domain.role.model.SecurityTier;
import org.evoting.cia.security.password.Argon2PasswordEncoder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled("Heavy Argon2 parameters; skip in unit test environment")
class Argon2PasswordEncoderTest {

    private final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Test
    @DisplayName("Encodes and verifies with LOW tier (64 MiB, t=2, p=1)")
    void lowTierEncodeAndVerify() {
        String raw = "S3cr3t!";
        String hash = encoder.encode(raw, SecurityTier.LOW);
        assertNotNull(hash);
        assertTrue(hash.startsWith("$argon2id$"));
        assertTrue(hash.contains("m=65536"), "Expected memory m=65536 in hash");
        assertTrue(hash.contains("t=2"));
        assertTrue(hash.contains("p=1"));
        assertTrue(encoder.matches(raw.toCharArray(), hash));
        assertFalse(encoder.matches("wrong".toCharArray(), hash));
    }

    @Test
    @DisplayName("Encodes and verifies with MEDIUM tier (128 MiB, t=2, p=1)")
    void mediumTierEncodeAndVerify() {
        String raw = "P@ssw0rd";
        String hash = encoder.encode(raw, SecurityTier.MEDIUM);
        assertNotNull(hash);
        assertTrue(hash.startsWith("$argon2id$"));
        assertTrue(hash.contains("m=131072"), "Expected memory m=131072 in hash");
        assertTrue(hash.contains("t=2"));
        assertTrue(hash.contains("p=1"));
        assertTrue(encoder.matches(raw, hash));
    }

    @Test
    @DisplayName("Encodes and verifies with HIGH tier (256 MiB, t=3, p=1)")
    void highTierEncodeAndVerify() {
        String raw = "Admin!234";
        String hash = encoder.encode(raw, SecurityTier.HIGH);
        assertNotNull(hash);
        assertTrue(hash.startsWith("$argon2id$"));
        assertTrue(hash.contains("m=262144"), "Expected memory m=262144 in hash");
        assertTrue(hash.contains("t=3"));
        assertTrue(hash.contains("p=1"));
        assertTrue(encoder.matches(raw, hash));
    }
}
