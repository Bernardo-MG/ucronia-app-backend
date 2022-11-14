
package com.bernardomg.security.password.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.token.provider.TokenProcessor;
import com.bernardomg.validation.exception.ValidationException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultPasswordRecoveryService implements PasswordRecoveryService {

    private final SecurityEmailSender mailSender;

    /**
     * Password encoder, for validating passwords.
     */
    private final PasswordEncoder     passwordEncoder;

    private final UserRepository      repository;

    private final TokenProcessor      tokenProcessor;

    /**
     * User details service, to find and validate users.
     */
    private final UserDetailsService  userDetailsService;

    public DefaultPasswordRecoveryService(@NonNull final UserRepository repo,
            @NonNull final UserDetailsService userDetsService, @NonNull final SecurityEmailSender mSender,
            @NonNull final TokenProcessor tProcessor, @NonNull final PasswordEncoder passEncoder) {
        super();

        repository = repo;
        userDetailsService = userDetsService;
        mailSender = mSender;
        tokenProcessor = tProcessor;
        passwordEncoder = passEncoder;
    }

    @Override
    public final Boolean changePassword(final String token, final String currentPassword, final String newPassword) {
        final Boolean succesful;
        final String  user;

        if (tokenProcessor.hasExpired(token)) {
            log.warn("Token {} has expired", token);
            succesful = false;
        } else {
            user = tokenProcessor.getSubject(token);

            succesful = validateChange(user, currentPassword);
        }

        return succesful;
    }

    @Override
    public final Boolean startPasswordRecovery(final String email) {
        final Optional<PersistentUser> user;
        final Failure                  error;
        final UserDetails              details;
        final Boolean                  valid;
        final String                   token;

        user = repository.findOneByEmail(email);
        if (!user.isPresent()) {
            error = FieldFailure.of("error.email.notExisting", "roleForm", "memberId", email);
            throw new ValidationException(Arrays.asList(error));
        }

        details = userDetailsService.loadUserByUsername(user.get()
            .getUsername());

        valid = isValid(details);
        if (valid) {
            token = tokenProcessor.generateToken(user.get()
                .getUsername());

            mailSender.sendPasswordRecoveryEmail(user.get()
                .getEmail(), token);
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

    private final Boolean validateChange(final String username, final String currentPassword) {
        final Optional<PersistentUser> user;
        Boolean                        valid;

        user = repository.findOneByUsername(username);

        // Verify the user exists
        if (!user.isPresent()) {
            log.error("No user exists for username {}", username);
            valid = false;
        } else {
            // User exists
            valid = true;

            // Verify the password matches is not changed
            if (!passwordEncoder.matches(currentPassword, user.get()
                .getPassword())) {
                log.debug("Received password doesn't match the one stored for username {}", username);
                valid = false;
            }
        }

        return valid;
    }

}
