package org.evoting.cia.domain.auth.model;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record RefreshRequest(String refreshToken) {
}
