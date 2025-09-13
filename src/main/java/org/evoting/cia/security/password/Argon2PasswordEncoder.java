package org.evoting.cia.security.password;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.inject.Singleton;
import org.evoting.cia.domain.role.model.SecurityTier;

/**
 * Argon2id encoder that allows selecting security parameters via SecurityTier.
 * Memory parameter (memory) is in KiB as per Argon2 specification.
 */
@Singleton
public class Argon2PasswordEncoder {

    private final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    /**
     * Encode the provided raw password using Argon2id with parameters based on SecurityTier.
     *
     * @param raw  raw password (not null or blank)
     * @param tier security tier determining Argon2 parameters (not null)
     * @return encoded hash in the standard $argon2id$ format
     */
    public String encode(
        String raw,
        SecurityTier tier
    ) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw password must not be null or blank");
        }
        if (tier == null) {
            throw new IllegalArgumentException("SecurityTier must not be null");
        }
        Params p = map(tier);
        char[] pwd = raw.toCharArray();
        try {
            return argon2.hash(p.iterations(), p.memory(), p.parallelism(), pwd);
        } finally {
            argon2.wipeArray(pwd);
        }
    }

    /**
     * Convenience method that uses MEDIUM tier.
     */
    public String encode(String raw) {
        return encode(raw, SecurityTier.MEDIUM);
    }

    /**
     * Verifies if raw password matches stored Argon2 hash.
     * The parameters are encoded in the hash; tier is not required for verification.
     *
     * @param raw  raw password
     * @param hash stored hash in $argon2id$ format
     * @return true if matches
     */
    public boolean matches(String raw, String hash) {
        if (raw == null) {
            return false;
        }
        return matches(raw.toCharArray(), hash);
    }

    /**
     * Verifies if raw password chars match stored Argon2 hash.
     * Prefer using the String overload for convenience.
     */
    public boolean matches(char[] raw, String hash) {
        if (raw == null || hash == null || hash.isBlank()) {
            return false;
        }
        try {
            return argon2.verify(hash, raw);
        } finally {
            argon2.wipeArray(raw);
        }
    }

    private Params map(SecurityTier tier) {
        return switch (tier) {
            case LOW -> new Params(65_536, 2, 1);      // 64 MiB
            case MEDIUM -> new Params(131_072, 2, 1);  // 128 MiB
            case HIGH -> new Params(262_144, 3, 1);    // 256 MiB  // 256 MiB
            case ULTRA  -> new Params(524_288, 4, 2);
            case FINAL -> new Params(1_048_576, 5, 4);
        };
    }
}
