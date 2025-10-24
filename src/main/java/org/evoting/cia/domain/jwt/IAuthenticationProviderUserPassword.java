package org.evoting.cia.domain.jwt;

import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider;

public interface IAuthenticationProviderUserPassword<B> extends HttpRequestAuthenticationProvider<B> {
}
