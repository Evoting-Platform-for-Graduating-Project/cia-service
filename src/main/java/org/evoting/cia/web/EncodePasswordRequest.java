package org.evoting.cia.web;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Value;

@Value
@Serdeable
public class EncodePasswordRequest {
    String password;
    int level;
}
