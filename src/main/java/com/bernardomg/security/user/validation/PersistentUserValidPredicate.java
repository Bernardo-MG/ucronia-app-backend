
package com.bernardomg.security.user.validation;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * User validator which checks if the user for a username is active.
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
public final class PersistentUserValidPredicate implements Predicate<String> {

    /**
     * Repository for the user data.
     */
    private final UserRepository userRepo;

    public PersistentUserValidPredicate(@NonNull final UserRepository userRepository) {
        super();

        userRepo = userRepository;
    }

    @Override
    public final boolean test(final String username) {
        final Boolean                  valid;
        final Optional<PersistentUser> userOpt;
        PersistentUser                 user;

        // Find the user
        userOpt = userRepo.findOneByUsername(username.toLowerCase(Locale.getDefault()));

        if (userOpt.isEmpty()) {
            // No user found for username
            log.debug("Username {} is invalid as there is no user registered for it", username);
            valid = false;
        } else if (isValid(userOpt.get())) {
            // User exists
            valid = true;
        } else {
            // Invalid user
            log.debug("Username {} is in an invalid state", username);
            user = userOpt.get();
            if (user.getExpired()) {
                log.debug("User {} expired", username);
            }
            if (user.getLocked()) {
                log.debug("User {} is locked", username);
            }
            if (user.getCredentialsExpired()) {
                log.debug("User {} credentials expired", username);
            }
            if (!user.getEnabled()) {
                log.debug("User {} is disabled", username);
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
    private final Boolean isValid(final PersistentUser userDetails) {
        return (!userDetails.getExpired()) && (!userDetails.getLocked()) && (!userDetails.getCredentialsExpired())
                && userDetails.getEnabled();
    }

}
