package org.evoting.cia.domain.auth.model;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Introspected
@RequiredArgsConstructor
@Builder
@Value
public class FullLoginRequest {

    String username;
    String password;
    String realm;
    String host;

    public static FullLoginRequest of(
        LoginRequest lr,
        String host,
        String realm
    ) {
        if (lr == null) {
            return null;
        }
        return FullLoginRequest.builder()
            .username(lr.username())
            .password(lr.password())
            .host(host)
            .realm(realm)
            .build();
    }
}
