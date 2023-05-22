
package com.bernardomg.security.login.service;

import java.util.Objects;

import com.bernardomg.security.login.model.ImmutableLoginStatus;
import com.bernardomg.security.login.model.ImmutableTokenLoginStatus;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.token.TokenEncoder;

public final class TokenLoginStatusProvider implements LoginStatusProvider {

    /**
     * Token encoder for creating authentication tokens.
     */
    private final TokenEncoder<String> tokenEncoder;

    public TokenLoginStatusProvider(final TokenEncoder<String> encoder) {
        super();

        tokenEncoder = Objects.requireNonNull(encoder);
    }

    @Override
    public final LoginStatus getStatus(final String username, final Boolean logged) {
        final LoginStatus status;
        final String      token;

        if (logged) {
            token = tokenEncoder.encode(username);
            status = new ImmutableTokenLoginStatus(username, logged, token);
        } else {
            status = new ImmutableLoginStatus(username, logged);
        }

        return status;
    }

}
