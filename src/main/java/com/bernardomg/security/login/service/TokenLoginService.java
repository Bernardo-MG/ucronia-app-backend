/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.security.login.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.login.model.ImmutableTokenLoginStatus;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.token.TokenProvider;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Login service for token-based authentication. Will generate a token on a successful login, and add it to the login
 * status.
 * <h2>Tokens</h2>
 * <p>
 * The {@link TokenProvider} will generate tokens after a succesful login attempt.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class TokenLoginService implements LoginService {

    /**
     * Token provider, creates authentication tokens.
     */
    private final TokenProvider tokenProvider;

    /**
     * Wrapped login service.
     */
    private final LoginService  wrapped;

    /**
     * Builds a service with the specified arguments.
     *
     * @param userDetService
     *            user details service to acquire users
     * @param passEncoder
     *            password encoder to validate passwords
     * @param validator
     *            login validator
     */
    public TokenLoginService(@NonNull final UserDetailsService userDetService,
            @NonNull final PasswordEncoder passEncoder, @NonNull final TokenProvider tProvider) {
        super();

        wrapped = new BasicLoginService(userDetService, passEncoder);
        tokenProvider = tProvider;
    }

    @Override
    public final TokenLoginStatus login(final String username, final String password) {
        final String      token;
        final LoginStatus status;

        log.debug("Log in attempt for {}", username);

        status = wrapped.login(username, password);

        if (status.getLogged()) {
            // Valid user
            // Generate token
            token = tokenProvider.generateToken(username);
            log.debug("Successful login for {}", username);
        } else {
            // Invalid user
            // No token
            token = "";
            log.debug("Failed login for {}", username);
        }

        return new ImmutableTokenLoginStatus(username, status.getLogged(), token);
    }

}
