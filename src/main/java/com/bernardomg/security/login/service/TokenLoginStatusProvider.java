
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
    public final LoginStatus getStatus(final String username, final boolean logged) {
        final LoginStatus status;
        final String      token;

        if (logged) {
            token = tokenEncoder.encode(username);
            status = ImmutableTokenLoginStatus.builder()
                .username(username)
                .logged(logged)
                .token(token)
                .build();
        } else {
            status = ImmutableLoginStatus.builder()
                .username(username)
                .logged(logged)
                .build();
        }

        return status;
    }

}
