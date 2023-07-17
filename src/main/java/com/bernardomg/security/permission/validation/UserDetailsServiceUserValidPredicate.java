
package com.bernardomg.security.permission.validation;

import java.util.Locale;
import java.util.function.Predicate;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * User validator which checks if the user for a username is active with the help if {@link UserDetailsService}.
 * <h2>Validations</h2>
 * <p>
 * If any of these fails, then the username is invalid.
 * <ul>
 * <li>User should be enabled</li>
 * <li>User should not be locked</li>
 * <li>User should not be expired</li>
 * <li>User should not have the credentials expired</li>
 * </ul>
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class UserDetailsServiceUserValidPredicate implements Predicate<String> {

    private final UserDetailsService userDetailsService;

    public UserDetailsServiceUserValidPredicate(@NonNull final UserDetailsService userDetService) {
        super();

        userDetailsService = userDetService;
    }

    @Override
    public final boolean test(final String username) {
        final Boolean valid;
        UserDetails   user;

        // Find the user
        // TODO: handle exception
        user = userDetailsService.loadUserByUsername(username.toLowerCase(Locale.getDefault()));
        if (isValid(user)) {
            // User exists
            valid = true;
        } else {
            // Invalid user
            log.debug("Username {} is in an invalid state", username);
            if (!user.isAccountNonExpired()) {
                log.debug("User {} expired", username);
            }
            if (!user.isAccountNonLocked()) {
                log.debug("User {} is locked", username);
            }
            if (!user.isCredentialsNonExpired()) {
                log.debug("User {} credentials expired", username);
            }
            if (!user.isEnabled()) {
                log.debug("User {} is disabled", username);
            }
            valid = false;
        }

        return valid;
    }

    /**
     * Checks if the user is valid. This means it has no flag marking it as not usable.
     *
     * @param user
     *            user to check
     * @return {@code true} if the user is valid, {@code false} otherwise
     */
    private final Boolean isValid(final UserDetails user) {
        return (user.isAccountNonExpired()) && (user.isAccountNonLocked()) && (user.isCredentialsNonExpired())
                && (user.isEnabled());
    }

}
