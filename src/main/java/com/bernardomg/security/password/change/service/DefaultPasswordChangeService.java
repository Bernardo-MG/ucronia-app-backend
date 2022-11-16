
package com.bernardomg.security.password.change.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.change.model.ImmutablePasswordChangeStatus;
import com.bernardomg.security.password.change.model.PasswordChangeStatus;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultPasswordChangeService implements PasswordChangeService {

    /**
     * Password encoder, for validating passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * User repository.
     */
    private final UserRepository  repository;

    public DefaultPasswordChangeService(@NonNull final UserRepository userRepo,
            @NonNull final PasswordEncoder passEncoder) {
        super();

        repository = userRepo;
        passwordEncoder = passEncoder;
    }

    @Override
    public final PasswordChangeStatus changePassword(final String username, final String currentPassword,
            final String password) {
        final Boolean                  successful;
        final Optional<PersistentUser> user;
        final PersistentUser           entity;
        final String                   encodedPassword;

        user = repository.findOneByUsername(username);

        successful = validatePasswordChange(user, username, currentPassword);

        if (successful) {
            entity = user.get();

            encodedPassword = passwordEncoder.encode(password);
            entity.setPassword(encodedPassword);

            repository.save(entity);
        }

        return new ImmutablePasswordChangeStatus(successful);
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
    private final Boolean validatePasswordChange(final Optional<PersistentUser> user, final String username,
            final String currentPassword) {
        Boolean valid;

        // Verify the user exists
        if (!user.isPresent()) {
            log.warn("No user exists for username {}", username);
            valid = false;
        } else {
            // User exists
            valid = true;

            // Verify the password matches is not changed
            if (!passwordEncoder.matches(currentPassword, user.get()
                .getPassword())) {
                log.warn("Received password doesn't match the one stored for username {}", user.get()
                    .getUsername());
                valid = false;
            }
        }

        return valid;
    }

}
