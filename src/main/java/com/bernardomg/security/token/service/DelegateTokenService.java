
package com.bernardomg.security.token.service;

import com.bernardomg.security.token.provider.TokenValidator;

import lombok.NonNull;

public class DelegateTokenService implements TokenService {

    private final TokenValidator tokenValidator;

    public DelegateTokenService(@NonNull final TokenValidator tValidator) {
        super();

        // TODO: Can be merged with Spring's token service?

        tokenValidator = tValidator;
    }

    @Override
    public final Boolean verifyToken(final String token) {
        return !tokenValidator.hasExpired(token);
    }

}
