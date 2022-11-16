
package com.bernardomg.security.password.change.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.change.model.ImmutablePasswordChangeStatus;
import com.bernardomg.security.password.change.model.PasswordChangeStatus;
import com.bernardomg.validation.exception.ValidationException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultPasswordChangeService implements PasswordChangeService {

    /**
     * Password encoder, for validating passwords.
     */
    private final PasswordEncoder    passwordEncoder;

    /**
     * User repository.
     */
    private final UserRepository     repository;

    /**
     * User details service, to find and validate users.
     */
    private final UserDetailsService userDetailsService;

    public DefaultPasswordChangeService(@NonNull final UserRepository userRepo,
            @NonNull final UserDetailsService userDetsService, @NonNull final PasswordEncoder passEncoder) {
        super();

        repository = userRepo;
        userDetailsService = userDetsService;
        passwordEncoder = passEncoder;
    }

    @Override
    public final PasswordChangeStatus changePassword(final String username, final String currentPassword,
            final String password) {
        final Boolean                  successful;
        final UserDetails              user;
        final Optional<PersistentUser> read;
        final PersistentUser           entity;
        final String                   encodedPassword;

        verifyPrivileges(username);

        read = repository.findOneByUsername(username);

        if (read.isPresent()) {
            // TODO: Avoid this second query
            user = userDetailsService.loadUserByUsername(username);

            successful = validatePasswordChange(user, username, currentPassword);

            if (successful) {
                entity = read.get();
                encodedPassword = passwordEncoder.encode(password);
                entity.setPassword(encodedPassword);

                repository.save(entity);
            }
        } else {
            successful = false;
        }

        return new ImmutablePasswordChangeStatus(successful);
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

    /**
     * Validates the password change.
     *
     * @param user
     *            user for which the password is changed, or empty if no user was found
     * @param username
     *            user's username
     * @param currentPassword
     *            current user's password
     * @return {@code true} if the password can be changed, {@code false} otherwise
     */
    private final Boolean validatePasswordChange(final UserDetails user, final String username,
            final String currentPassword) {
        Boolean valid;

        valid = true;

        // Verify the password matches is not changed
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            log.warn("Received password doesn't match the one stored for username {}", user.getUsername());
            valid = false;
        }

        if (!isValid(user)) {
            log.warn("User {} is not enabled", user.getUsername());
            valid = false;
        }

        return valid;
    }

    private final void verifyPrivileges(final String username) {
        final Failure             failure;
        final Collection<Failure> failures;
        final Authentication      auth;
        final String              sessionUser;

        failures = new ArrayList<>();

        auth = SecurityContextHolder.getContext()
            .getAuthentication();
        if (auth != null) {
            sessionUser = auth.getName();
        } else {
            sessionUser = "";
        }

        // TODO: This isn't a validation, it is a security error
        if (!username.equals(sessionUser)) {
            log.error("The user {} tried to change the password for {}", sessionUser, username);
            failure = FieldFailure.of("error.user.unauthorized", "roleForm", "memberId", username);
            failures.add(failure);
        }

        if (!failures.isEmpty()) {
            throw new ValidationException(failures);
        }
    }

}
