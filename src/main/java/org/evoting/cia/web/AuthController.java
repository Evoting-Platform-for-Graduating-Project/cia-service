package org.evoting.cia.web;

import com.nimbusds.jose.JOSEException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import org.evoting.cia.domain.auth.model.FullLoginRequest;
import org.evoting.cia.domain.auth.model.LoginRequest;
import org.evoting.cia.domain.auth.model.RefreshRequest;
import org.evoting.cia.domain.auth.model.TokenPair;
import org.evoting.cia.domain.auth.model.TokenResponse;
import org.evoting.cia.infrastracture.auth.service.AuthService;
import org.evoting.cia.infrastracture.data.db.entity.User;

@Controller("/auth")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthController {

    private final AuthService authService;

    @Inject
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Post(value = "/login/{realm}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<TokenResponse> login(
        HttpRequest<?> httpRequest,
        @PathVariable String realm,
        @Body LoginRequest request
    ) throws JOSEException {
        // TODO: Replace with real credential verification
        FullLoginRequest fullLoginRequest = FullLoginRequest.of(
            request,
            httpRequest.getRemoteAddress().getHostName(),
            realm);
        User user = authService.authenticate(fullLoginRequest);
        TokenPair pair = authService.issueTokensForUser(user);
        return HttpResponse.ok(
            new TokenResponse(pair.accessToken(), pair.refreshToken(), pair.tokenType(), pair.expiresIn()));
    }

    @Post(value = "/refresh", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<TokenResponse> refresh(@Body RefreshRequest request) {
        if (request == null || request.refreshToken() == null || request.refreshToken().isBlank()) {
            return HttpResponse.unauthorized();
        }
        return authService.refreshUsing(request.refreshToken())
                .<HttpResponse<TokenResponse>>map(p -> HttpResponse.ok(
                    new TokenResponse(p.accessToken(), p.refreshToken(), p.tokenType(), p.expiresIn())))
                .orElse(HttpResponse.unauthorized());
    }

}
