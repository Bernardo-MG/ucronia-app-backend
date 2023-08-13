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

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.token.exception.InvalidTokenException;
import com.bernardomg.security.token.exception.MissingTokenException;
import com.bernardomg.security.token.store.TokenStore;
import com.bernardomg.security.user.exception.UserDisabledException;
import com.bernardomg.security.user.exception.UserExpiredException;
import com.bernardomg.security.user.exception.UserLockedException;
import com.bernardomg.security.user.exception.UserNotFoundException;
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
     * Token scope for reseting passwords.
     */
    private final String                tokenScope;

    /**
     * Token processor.
     */
    private final TokenStore            tokenStore;

    /**
     * User details service, to find and validate users.
     */
    private final UserDetailsService    userDetailsService;

    /**
     * User repository.
     */
    private final UserRepository        userRepository;

    public SpringSecurityPasswordResetService(@NonNull final UserRepository repo,
            @NonNull final UserDetailsService userDetsService, @NonNull final SecurityMessageSender mSender,
            @NonNull final TokenStore tStore, @NonNull final PasswordEncoder passEncoder, final String scope) {
        super();

        userRepository = repo;
        userDetailsService = userDetsService;
        messageSender = mSender;
        tokenStore = tStore;
        passwordEncoder = passEncoder;
        tokenScope = scope;
    }

    @Override
    @Transactional
    public final void changePassword(final String token, final String password) {
        final String         username;
        final PersistentUser user;
        final String         encodedPassword;

        // TODO: Use a token validator which takes care of the exceptions
        if (!tokenStore.exists(token, tokenScope)) {
            log.error("Token missing: {}", token);
            throw new MissingTokenException(token);
        }

        if (!tokenStore.isValid(token, tokenScope)) {
            // TODO: Throw an exception for each possible case
            log.error("Token expired: {}", token);
            throw new InvalidTokenException(token);
        }

        username = tokenStore.getUsername(token);

        log.debug("Applying requested password change for {}", username);

        user = getUserByUsername(username);

        authorizePasswordChange(user.getUsername());

        encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.save(user);
        tokenStore.consumeToken(token);

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

        // Revoke previous tokens
        tokenStore.revokeExistingTokens(user.getId(), tokenScope);

        // Register new token
        token = tokenStore.createToken(user.getId(), user.getUsername(), tokenScope);

        // TODO: Handle through events
        messageSender.sendPasswordRecoveryMessage(user.getEmail(), token);

        log.debug("Finished password recovery request for {}", email);
    }

    @Override
    public final boolean validateToken(final String token) {
        return tokenStore.isValid(token, tokenScope);
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

        // TODO: This should be contained in a common class
        if (!userDetails.isAccountNonExpired()) {
            log.error("Can't reset password. User {} is expired", userDetails.getUsername());
            throw new UserExpiredException(userDetails.getUsername());
        }
        if (!userDetails.isAccountNonLocked()) {
            log.error("Can't reset password. User {} is locked", userDetails.getUsername());
            throw new UserLockedException(userDetails.getUsername());
        }
        if (!userDetails.isCredentialsNonExpired()) {
            log.error("Can't reset password. User {} credentials are expired", userDetails.getUsername());
            throw new UserExpiredException(userDetails.getUsername());
        }
        if (!userDetails.isEnabled()) {
            log.error("Can't reset password. User {} is disabled", userDetails.getUsername());
            throw new UserDisabledException(userDetails.getUsername());
        }
    }

    private final PersistentUser getUserByEmail(final String email) {
        final Optional<PersistentUser> user;

        user = userRepository.findOneByEmail(email);

        // Validate the user exists
        if (!user.isPresent()) {
            log.error("Couldn't change password for email {}, as no user exists for it", email);
            throw new UserNotFoundException(email);
        }

        return user.get();
    }

    private final PersistentUser getUserByUsername(final String username) {
        final Optional<PersistentUser> user;

        user = userRepository.findOneByUsername(username);

        // Validate the user exists
        if (!user.isPresent()) {
            log.error("Couldn't change password for user {}, as it doesn't exist", username);
            throw new UserNotFoundException(username);
        }

        return user.get();
    }

}
