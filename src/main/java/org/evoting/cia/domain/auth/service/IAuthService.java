package org.evoting.cia.domain.auth.service;

import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import org.evoting.cia.domain.auth.model.FullLoginRequest;
import org.evoting.cia.domain.auth.model.TokenPair;
import org.evoting.cia.infrastracture.data.db.entity.User;

public interface IAuthService {
    User authenticate(FullLoginRequest request);

    TokenPair issueTokens(FullLoginRequest request) throws JOSEException;

    TokenPair issueTokensForUser(User user) throws JOSEException;

    TokenPair refreshing(String refreshToken) throws JOSEException, ParseException;
}
