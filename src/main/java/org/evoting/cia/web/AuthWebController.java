package org.evoting.cia.web;

import com.nimbusds.jose.JOSEException;
import io.micronaut.context.annotation.Context;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.evoting.api.cia.AuthApi;
import org.evoting.api.cia.model.LoginRequestDto;
import org.evoting.api.cia.model.RefreshRequestDto;
import org.evoting.api.cia.model.TokenResponseDto;
import org.evoting.cia.domain.auth.model.FullLoginRequest;
import org.evoting.cia.domain.auth.model.TokenPair;
import org.evoting.cia.infrastracture.auth.service.AuthService;

@Controller("/auth")
@Secured(SecurityRule.IS_ANONYMOUS)
@RequiredArgsConstructor
public class AuthWebController implements AuthApi {

    private final AuthService authService;
    @Context
    private final HttpRequest<?> httpRequest;

    @Override
    public TokenResponseDto authLogin(
        String realm,
        LoginRequestDto request
    ) {
        FullLoginRequest fullLoginRequest = FullLoginRequest.builder()
            .request(request)
            .realm(realm)
            .host(httpRequest.getRemoteAddress().getHostName())
            .build();
        try {
            TokenPair pair = authService.issueTokens(fullLoginRequest);
            return new TokenResponseDto()
                .accessToken(pair.accessToken())
                .refreshToken(pair.refreshToken())
                .tokenType(pair.tokenType())
                .expiresIn(pair.expiresIn());
        } catch (JOSEException e) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
    }

    @Override
    public TokenResponseDto authRefresh(
        RefreshRequestDto refreshRequestDto
    ) {
        try {
            TokenPair pair = authService.refreshing(refreshRequestDto.getRefreshToken());
            return new TokenResponseDto()
                .accessToken(pair.accessToken())
                .refreshToken(pair.refreshToken())
                .tokenType(pair.tokenType())
                .expiresIn(pair.expiresIn());
        } catch (JOSEException | ParseException e) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }
    }
}
