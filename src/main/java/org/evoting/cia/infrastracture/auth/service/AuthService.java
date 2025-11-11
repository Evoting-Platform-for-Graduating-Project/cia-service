package org.evoting.cia.infrastracture.auth.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.context.annotation.Value;
import io.micronaut.security.authentication.AuthenticationException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.evoting.api.cia.model.LoginRequestDto;
import org.evoting.cia.domain.auth.model.FullLoginRequest;
import org.evoting.cia.domain.auth.model.TokenPair;
import org.evoting.cia.domain.auth.service.IAuthService;
import org.evoting.cia.infrastracture.data.db.entity.Client;
import org.evoting.cia.infrastracture.data.db.entity.User;
import org.evoting.cia.infrastracture.data.db.repository.ClientRepository;
import org.evoting.cia.infrastracture.data.db.repository.UserRepository;
import org.evoting.cia.security.password.Argon2PasswordEncoder;
import org.jspecify.annotations.NonNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class AuthService implements IAuthService {

    public static final String FAKE_PASSWORD =
        "$argon2id$v=19$m=262144,t=3,p=1$om1zrNX3WbQzD2hJXvTJpg$xdAXo1/YyieZEBdLYnZ2ohLKS1B4JpbIPsmUXYM93uc";

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;

    private final Argon2PasswordEncoder passwordEncoder;

    @Value("${micronaut.security.token.jwt.signatures.secret.generator.secret}")
    private String secret;

    @Value("${security.jwt.access-token.expiration-seconds}")
    private long accessTokenTtlSeconds;

    @Value("${security.jwt.refresh-token.expiration-seconds}")
    private long refreshTokenTtlSeconds;

    @Override
    public User authenticate(FullLoginRequest request) {
        validateRequestContainsData(request);
        LoginRequestDto requestData = request.getRequest();

        Client client = verifyClient(request, requestData);

        User user = verifyUser(requestData, client);

        if (!passwordEncoder.matches(requestData.getPassword(), user.getPassword())) {
            throw new AuthenticationException();
        }

        return user;
    }

    @Override
    public TokenPair issueTokens(FullLoginRequest request) throws JOSEException {
        return issueTokensForUser(authenticate(request));
    }

    @Override
    public TokenPair issueTokensForUser(User user) throws JOSEException {
        // Set<String> roles = user.get.stream()
        //        .map(Role::getName)
        //        .collect(java.util.stream.Collectors.toSet());
        Set<String> roles = Set.of("admin");
        String access = createAccessToken(user.getUsername(), roles);
        String refresh = createRefreshToken(user.getUsername(), roles);
        return new TokenPair(access, refresh, "Bearer", accessTokenTtlSeconds);
    }

    @Override
    public TokenPair refreshing(String refreshToken) throws JOSEException, ParseException {
        SignedJWT jwt = SignedJWT.parse(refreshToken);
        if (!jwt.verify(new MACVerifier(secret.getBytes()))) {
            throw new AuthenticationException();
        }
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        String tokenType = claims.getStringClaim("tokenType");
        Date exp = claims.getExpirationTime();
        if (!"refresh".equals(tokenType) || exp == null || exp.before(new Date())) {
            throw new AuthenticationException();
        }
        String subject = claims.getSubject();
        if (subject == null || subject.isBlank()) {
            throw new AuthenticationException();
        }
        Set<String> roles = new HashSet<>(claims.getStringListClaim("roles"));
        String access = createAccessToken(subject, roles);
        String refresh = createRefreshToken(subject, roles);

        return new TokenPair(
            access,
            refresh,
            "Bearer",
            accessTokenTtlSeconds
        );
    }

    private @NonNull User verifyUser(LoginRequestDto requestData, Client client) {
        Optional<User> userOpt = userRepository.findByUsernameAndRealm(
            requestData.getUsername(),
            client.getRealm()
        );

        return userOpt.orElseThrow(() -> {
            maskAuthenticationException(requestData.getPassword());
            return new AuthenticationException();
        });
    }

    private @NonNull Client verifyClient(
        FullLoginRequest request,
        LoginRequestDto requestData
    ) {
        Optional<Client> clientOpt = clientRepository.findByHostsContainsIgnoreCaseAndRealm_Name(
            request.getHost(),
            request.getRealm()
        );

        return clientOpt.orElseThrow(() -> {
            maskAuthenticationException(requestData.getPassword());
            return new AuthenticationException();
        });
    }

    private void validateRequestContainsData(FullLoginRequest request) {
        if (request == null) {
            throw new AuthenticationException();
        }

        LoginRequestDto requestData =  request.getRequest();

        if (requestData == null) {
            throw new AuthenticationException();
        }

        if (requestData.getPassword() == null || requestData.getPassword().isBlank()) {
            throw new AuthenticationException();
        }

        if (requestData.getUsername() == null || requestData.getUsername().isBlank()) {
            throw new AuthenticationException();
        }

        if (request.getRealm() == null || request.getRealm().isBlank()) {
            throw new AuthenticationException();
        }

        if (request.getHost() == null || request.getHost().isBlank()) {
            throw new AuthenticationException();
        }
    }

    private void maskAuthenticationException(String password) {
        passwordEncoder.matches(password, FAKE_PASSWORD);
    }

    private String createAccessToken(
        String subject,
        Set<String> roles
    ) throws JOSEException {
        return createJwt(subject, accessTokenTtlSeconds, "access", roles);
    }

    private String createRefreshToken(
        String username,
        Set<String> roles
    ) throws JOSEException {
        return createJwt(username, refreshTokenTtlSeconds, "refresh", roles);
    }

    private String createJwt(
        String subject,
        long ttlSeconds,
        String tokenType,
        Set<String> roles
    ) throws JOSEException {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ttlSeconds);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(subject)
                .issueTime(Date.from(now))
                .expirationTime(Date.from(exp))
                .claim("roles", roles)
                .claim("tokenType", tokenType)
                .build();
        SignedJWT signedjwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        MACSigner signer = new MACSigner(secret.getBytes());
        signedjwt.sign(signer);
        return signedjwt.serialize();
    }

}
