
package com.bernardomg.security.password.change.service;

import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.password.exception.InvalidPasswordChangeException;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SpringSecurityPasswordChangeService implements PasswordChangeService {

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

    public SpringSecurityPasswordChangeService(@NonNull final UserRepository userRepo,
            @NonNull final UserDetailsService userDetsService, @NonNull final PasswordEncoder passEncoder) {
        super();

        repository = userRepo;
        userDetailsService = userDetsService;
        passwordEncoder = passEncoder;
    }

    @Override
    public final void changePasswordForUserInSession(final String currentPassword, final String password) {
        final Optional<PersistentUser> userEntityOptional;
        final PersistentUser           userEntity;
        final String                   encodedPassword;
        final String                   username;

        username = getCurrentUsername();

        log.debug("Changing password for user {}", username);

        userEntityOptional = repository.findOneByUsername(username);

        // Validate the user exists
        if (!userEntityOptional.isPresent()) {
            log.error("Couldn't change password for user {}, as it doesn't exist", username);
            throw new UsernameNotFoundException(
                String.format("Couldn't change password for user %s, as it doesn't exist", username));
        }

        // Make sure the user can change the password
        authorizePasswordChange(username, currentPassword);

        userEntity = userEntityOptional.get();
        encodedPassword = passwordEncoder.encode(password);
        userEntity.setPassword(encodedPassword);

        repository.save(userEntity);

        log.debug("Changed password for user {}", username);
    }

    /**
     * Authenticates the password change attempt. If the user is not authenticated, then an exception is thrown.
     *
     * @param username
     *            username for which the password is changed
     * @param currentPassword
     *            current user's password
     */
    private final void authorizePasswordChange(final String username, final String currentPassword) {
        final UserDetails userDetails;

        // TODO: Avoid this second query
        userDetails = userDetailsService.loadUserByUsername(username);

        // Verify the current password matches the original one
        if (!passwordEncoder.matches(currentPassword, userDetails.getPassword())) {
            log.warn("Received a password which doesn't match the one stored for username {}",
                userDetails.getUsername());
            throw new BadCredentialsException(String
                .format("Received a password which doesn't match the one stored for %s", userDetails.getUsername()));
        }

        // Verify the user is enabled
        if (!isValid(userDetails)) {
            log.warn("User {} is not enabled", userDetails.getUsername());
            throw new UsernameNotFoundException(String.format("User %s is not enabled", userDetails.getUsername()));
        }
    }

    private final String getCurrentUsername() {
        final Authentication auth;

        auth = SecurityContextHolder.getContext()
            .getAuthentication();
        if ((auth == null) || (!auth.isAuthenticated())) {
            throw new InvalidPasswordChangeException("No user authenticated", "");
        }

        return auth.getName();
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
