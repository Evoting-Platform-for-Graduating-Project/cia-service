package org.evoting.cia.domain.auth.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder
public record TokenPair(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn
) {
}
