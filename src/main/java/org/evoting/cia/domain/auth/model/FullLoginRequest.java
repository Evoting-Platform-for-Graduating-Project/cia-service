package org.evoting.cia.domain.auth.model;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.evoting.api.cia.model.LoginRequestDto;

@Introspected
@RequiredArgsConstructor
@Builder
@Value
public class FullLoginRequest {

    LoginRequestDto request;
    String host;
    String realm;
}
