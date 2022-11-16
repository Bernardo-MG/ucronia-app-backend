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

package com.bernardomg.security.login.service.springframework;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.login.model.ImmutableTokenLoginStatus;
import com.bernardomg.security.login.model.Login;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.service.LoginService;
import com.bernardomg.security.token.provider.TokenProvider;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Login service for token-based authentication which integrates with Spring Security. Will generate a token on a
 * successful login, and add it to the login status.
 * <h2>Composition</h2> Extends, through composition, {@link SpringSecurityLoginService}.
 * <h2>Tokens</h2>
 * <p>
 * The {@link TokenProvider} will generate tokens after a successful login attempt, and an instance of
 * {@link TokenLoginStatus} is returned. If the login failed, then the token is not generated, and a {@link LoginStatus}
 * is returned
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class SpringSecurityTokenLoginService implements LoginService {

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
     * @param tProvider
     *            token provider
     */
    public SpringSecurityTokenLoginService(@NonNull final UserDetailsService userDetService,
            @NonNull final PasswordEncoder passEncoder, @NonNull final TokenProvider tProvider) {
        super();

        wrapped = new SpringSecurityLoginService(userDetService, passEncoder);
        tokenProvider = tProvider;
    }

    @Override
    public final LoginStatus login(final Login login) {
        final String      token;
        final LoginStatus basicStatus;
        final LoginStatus status;

        log.debug("Log in attempt for {}", login.getUsername());

        // Attempts to login through the basic service
        basicStatus = wrapped.login(login);

        if (basicStatus.getSuccessful()) {
            // Valid user
            // Generate token
            log.debug("Successful login for {}", login.getUsername());
            token = tokenProvider.generateToken(login.getUsername());
            status = new ImmutableTokenLoginStatus(login.getUsername(), basicStatus.getSuccessful(), token);
        } else {
            // Invalid user
            // No token
            log.debug("Failed login for {}", login.getUsername());
            status = basicStatus;
        }

        return status;
    }

}
