package org.evoting.cia.domain.auth.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record LoginRequest(String username, String password) {
}
