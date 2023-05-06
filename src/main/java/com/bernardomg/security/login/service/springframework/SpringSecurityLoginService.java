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

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.login.model.Login;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.service.LoginService;
import com.bernardomg.security.login.service.LoginStatusProvider;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Login service which integrates with Spring Security. It makes use of {@link UserDetailsService} to find the user
 * which tries to log in.
 * <h2>Validations</h2>
 * <p>
 * If any of these fails, then the log in fails.
 * <ul>
 * <li>Received username exists as a user</li>
 * <li>Received password matchs the one encrypted for the user</li>
 * <li>User should be enabled, and valid</li>
 * </ul>
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class SpringSecurityLoginService implements LoginService {

    private final LoginStatusProvider loginStatusProvider;

    /**
     * Password encoder, for validating passwords.
     */
    private final PasswordEncoder     passwordEncoder;

    /**
     * User details service, to find and validate users.
     */
    private final UserDetailsService  userDetailsService;

    /**
     * Builds a service with the specified arguments.
     *
     * @param userDetService
     *            user details service to acquire users
     * @param passEncoder
     *            password encoder to validate passwords
     * @param loginStatusProv
     *            login status provider
     */
    public SpringSecurityLoginService(@NonNull final UserDetailsService userDetService,
            @NonNull final PasswordEncoder passEncoder, final LoginStatusProvider loginStatusProv) {
        super();

        userDetailsService = userDetService;
        passwordEncoder = passEncoder;
        loginStatusProvider = loginStatusProv;
    }

    @Override
    public final LoginStatus login(final Login login) {
        final Boolean valid;
        final String  username;

        username = login.getUsername()
            .toLowerCase();

        log.debug("Log in attempt for {}", username);

        valid = isValid(login);

        return loginStatusProvider.getStatus(username, valid);
    }

    private final Boolean isValid(final Login login) {
        final Boolean         valid;
        Optional<UserDetails> details;

        // Find the user
        try {
            details = Optional.ofNullable(userDetailsService.loadUserByUsername(login.getUsername()
                .toLowerCase()));
        } catch (final UsernameNotFoundException e) {
            details = Optional.empty();
        }

        if (details.isEmpty()) {
            // No user found for username
            log.debug("No user for username {}. Failed login", login.getUsername());
            valid = false;
        } else if (isValid(details.get())) {
            // User exists
            // Validate password
            valid = passwordEncoder.matches(login.getPassword(), details.get()
                .getPassword());
            if (!valid) {
                log.debug("Received password doesn't match the one stored for username {}. Failed login",
                    login.getUsername());
            }
        } else {
            // Invalid user
            log.debug("User {} is in an invalid state. Failed login", login.getUsername());
            if (!details.get()
                .isAccountNonExpired()) {
                log.debug("User {} account expired", login.getUsername());
            }
            if (!details.get()
                .isAccountNonLocked()) {
                log.debug("User {} account is locked", login.getUsername());
            }
            if (!details.get()
                .isCredentialsNonExpired()) {
                log.debug("User {} credentials expired", login.getUsername());
            }
            if (!details.get()
                .isEnabled()) {
                log.debug("User {} is disabled", login.getUsername());
            }
            valid = false;
        }

        return valid;
    }

    /**
     * Checks if the user is valid. This means it has no flag marking it as not usable.
     *
     * @param userDetails
     *            user the check
     * @return {@code true} if the user is valid, {@code false} otherwise
     */
    private final Boolean isValid(final UserDetails userDetails) {
        return userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked()
                && userDetails.isCredentialsNonExpired() && userDetails.isEnabled();
    }

}
