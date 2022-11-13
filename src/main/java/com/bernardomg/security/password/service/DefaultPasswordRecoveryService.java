
package com.bernardomg.security.password.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.token.provider.TokenProvider;
import com.bernardomg.security.token.provider.TokenValidator;
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

    private final TokenProvider       tokenProvider;

    private final TokenValidator      tokenValidator;

    /**
     * User details service, to find and validate users.
     */
    private final UserDetailsService  userDetailsService;

    public DefaultPasswordRecoveryService(@NonNull final UserRepository repo,
            @NonNull final UserDetailsService userDetsService, @NonNull final SecurityEmailSender mSender,
            @NonNull final TokenProvider tProvider, @NonNull final TokenValidator tValidator,
            @NonNull final PasswordEncoder passEncoder) {
        super();

        repository = repo;
        userDetailsService = userDetsService;
        mailSender = mSender;
        tokenProvider = tProvider;
        tokenValidator = tValidator;
        passwordEncoder = passEncoder;
    }

    @Override
    public final Boolean changePassword(final String token, final String currentPassword, final String newPassword) {
        final Boolean             succesful;
        final Collection<Failure> failures;
        final String              user;

        user = tokenValidator.getSubject(token);

        failures = validateChange(user, currentPassword);

        if (!failures.isEmpty()) {
            log.debug("Got errors: {}", failures);
            throw new ValidationException(failures);
        }

        if (tokenValidator.hasExpired(token)) {
            succesful = false;
        } else {
            succesful = false;
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
            token = tokenProvider.generateToken(email);

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

    private final Collection<Failure> validateChange(final String username, final String currentPassword) {
        final Optional<PersistentUser> user;
        final Collection<Failure>      failures;
        final Boolean                  exists;
        Failure                        failure;

        user = repository.findOneByUsername(username);

        failures = new ArrayList<>();

        // Verify the user exists
        if (!user.isPresent()) {
            log.error("No user exists for username {}", username);
            failure = FieldFailure.of("error.user.notExisting", "roleForm", "memberId", username);
            failures.add(failure);
            exists = false;
        } else {
            exists = true;
        }

        // Verify the password matches is not changed
        if (exists && !passwordEncoder.matches(user.get()
            .getPassword(), currentPassword)) {
            log.debug("Received password doesn't match the one stored for username {}", username);
            failure = FieldFailure.of("error.password.invalid", "roleForm", "id", username);
            failures.add(failure);
        }

        return failures;
    }

}
