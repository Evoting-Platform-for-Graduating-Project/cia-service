package org.evoting.cia.domain.auth.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TokenPair(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn
) {
}
