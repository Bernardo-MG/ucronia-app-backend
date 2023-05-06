
package com.bernardomg.security.login.service;

import com.bernardomg.security.login.model.ImmutableLoginStatus;
import com.bernardomg.security.login.model.ImmutableTokenLoginStatus;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.token.provider.TokenProvider;

public final class TokenLoginStatusProvider implements LoginStatusProvider {

    /**
     * Token provider, creates authentication tokens.
     */
    private final TokenProvider tokenProvider;

    public TokenLoginStatusProvider(final TokenProvider tokenProv) {
        super();

        tokenProvider = tokenProv;
    }

    @Override
    public final LoginStatus getStatus(final String username, final Boolean logged) {
        final LoginStatus status;
        final String      token;

        if (logged) {
            token = tokenProvider.generateToken(username);
            status = new ImmutableTokenLoginStatus(username, logged, token);
        } else {
            status = new ImmutableLoginStatus(username, logged);
        }

        return status;
    }

}
