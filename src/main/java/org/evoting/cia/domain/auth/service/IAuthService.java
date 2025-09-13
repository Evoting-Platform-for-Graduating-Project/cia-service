package org.evoting.cia.domain.auth.service;

import com.nimbusds.jose.JOSEException;
import java.util.Optional;
import org.evoting.cia.domain.auth.model.FullLoginRequest;
import org.evoting.cia.domain.auth.model.TokenPair;
import org.evoting.cia.infrastracture.data.db.entity.User;

public interface IAuthService {
    User authenticate(FullLoginRequest request);

    TokenPair issueTokensForUser(User user) throws JOSEException;

    Optional<TokenPair> refreshUsing(String refreshToken);
}
