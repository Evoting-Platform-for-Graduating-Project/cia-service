package org.evoting.cia.web;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;
import org.evoting.cia.domain.role.model.SecurityTier;
import org.evoting.cia.security.password.Argon2PasswordEncoder;

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final Argon2PasswordEncoder passwordEncoder;

    @Get("/")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public String index() {
        return "ok";
    }

    @Get("/secure")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public String secure() {
        return "secured";
    }

    @Post("/encode")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public String encode(@Body EncodePasswordRequest request) {
        return passwordEncoder.encode(request.getPassword(), SecurityTier.fromLevel(request.getLevel()));
    }
}
