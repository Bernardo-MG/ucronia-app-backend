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

package com.bernardomg.security.password.reset.service;

import java.util.Optional;

import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.exception.UserDisabledException;
import com.bernardomg.security.exception.UserNotFoundException;
import com.bernardomg.security.token.exception.ExpiredTokenException;
import com.bernardomg.security.token.exception.MissingTokenException;
import com.bernardomg.security.token.provider.TokenProcessor;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Password recovery service which integrates with Spring Security.
 * <h2>Validations</h2>
 * <p>
 * If any of these fails, then the password change can't start.
 * <ul>
 * <li>Received email exists as a user</li>
 * <li>User should be enabled, and valid</li>
 * </ul>
 * If any of these fails, then the password change can't finalize.
 * <ul>
 * <li>The token is valid</li>
 * <li>Password received as the current password matches the actual current password</li>
 * </ul>
 * <h2></h2>
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class SpringSecurityPasswordResetService implements PasswordResetService {

    /**
     * Message sender. Recovery steps may require emails, or other kind of messaging.
     */
    private final SecurityMessageSender messageSender;

    /**
     * Password encoder, for validating passwords.
     */
    private final PasswordEncoder       passwordEncoder;

    /**
     * User repository.
     */
    private final UserRepository        repository;

    /**
     * Token processor.
     */
    private final TokenProcessor        tokenProcessor;

    /**
     * User details service, to find and validate users.
     */
    private final UserDetailsService    userDetailsService;

    public SpringSecurityPasswordResetService(@NonNull final UserRepository repo,
            @NonNull final UserDetailsService userDetsService, @NonNull final SecurityMessageSender mSender,
            @NonNull final TokenProcessor tProcessor, @NonNull final PasswordEncoder passEncoder) {
        super();

        repository = repo;
        userDetailsService = userDetsService;
        messageSender = mSender;
        tokenProcessor = tProcessor;
        passwordEncoder = passEncoder;
    }

    @Override
    public final void changePassword(final String token, final String password) {
        final String         username;
        final PersistentUser user;
        final String         encodedPassword;
        final Token          issuedToken;

        if (!tokenProcessor.exists(token)) {
            log.error("Token missing: {}", token);
            throw new MissingTokenException(token);
        }

        if (tokenProcessor.hasExpired(token)) {
            log.error("Token expired: {}", token);
            throw new ExpiredTokenException(token);
        }

        issuedToken = tokenProcessor.decode(token);
        username = issuedToken.getExtendedInformation();

        log.debug("Applying requested password change for {}", username);

        user = getUser(username);

        authorizePasswordChange(user.getUsername());

        encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        repository.save(user);
        tokenProcessor.closeToken(token);

        log.debug("Finished password change for {}", username);
    }

    @Override
    public final void startPasswordRecovery(final String email) {
        final PersistentUser user;
        final String         token;

        log.debug("Requested password recovery for {}", email);

        user = getUserByEmail(email);

        // TODO: Reject authenticated users? Allow only password recovery for the anonymous user

        // Make sure the user can change the password
        authorizePasswordChange(user.getUsername());

        token = tokenProcessor.generateToken(user.getUsername());

        // TODO: Handle through events
        messageSender.sendPasswordRecoveryMessage(user.getEmail(), token);

        log.debug("Finished password recovery request for {}", email);
    }

    @Override
    public final boolean validateToken(final String token) {
        return !tokenProcessor.hasExpired(token);
    }

    /**
     * Authenticates the password change attempt. If the user is not authenticated, then an exception is thrown.
     *
     * @param username
     *            username for which the password is changed
     */
    private final void authorizePasswordChange(final String username) {
        final UserDetails userDetails;

        // TODO: Avoid this second query
        userDetails = userDetailsService.loadUserByUsername(username);

        // Verify the user is enabled
        if (!isValid(userDetails)) {
            log.warn("User {} is not enabled", userDetails.getUsername());
            // TODO: Use more concrete exception for the exact status
            throw new UserDisabledException(String.format("User %s is not enabled", userDetails.getUsername()));
        }
    }

    private final PersistentUser getUser(final String username) {
        final Optional<PersistentUser> user;

        user = repository.findOneByUsername(username);

        // Validate the user exists
        if (!user.isPresent()) {
            log.error("Couldn't change password for user {}, as it doesn't exist", username);
            // TODO: Use more concrete exception for the exact status
            throw new UserDisabledException(
                String.format("Couldn't change password for user %s, as it doesn't exist", username));
        }

        return user.get();
    }

    private final PersistentUser getUserByEmail(final String email) {
        final Optional<PersistentUser> user;

        user = repository.findOneByEmail(email);

        // Validate the user exists
        if (!user.isPresent()) {
            log.error("Couldn't change password for email {}, as no user exists for it", email);
            throw new UserNotFoundException(
                String.format("Couldn't change password for email %s, as no user exists for it", email));
        }

        return user.get();
    }

    /**
     * Checks if the user is valid. This means it has no flag marking it as not usable.
     *
     * @param userDetails
     *            user the check
     * @return {@code true} if the user is valid, {@code false} otherwise
     */
    private final boolean isValid(final UserDetails userDetails) {
        // TODO: This should be contained in a common class
        return userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked()
                && userDetails.isCredentialsNonExpired() && userDetails.isEnabled();
    }

}
