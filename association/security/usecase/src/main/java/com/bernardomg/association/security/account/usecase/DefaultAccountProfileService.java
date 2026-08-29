
package com.bernardomg.association.security.account.usecase;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.security.user.domain.model.UserProfile;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.usecase.service.DefaultUserProfileService;
import com.bernardomg.security.usecase.session.UsernameInSessionProvider;

public final class DefaultAccountProfileService implements AccountProfileService {

    /**
     * Logger for the class.
     */
    private static final Logger             log = LoggerFactory.getLogger(DefaultUserProfileService.class);

    private final UsernameInSessionProvider usernameProvider;

    private final UserProfileRepository     userProfileRepository;

    public DefaultAccountProfileService(final UserProfileRepository userProfileRepo,
            final UsernameInSessionProvider usernameProv) {
        super();

        userProfileRepository = Objects.requireNonNull(userProfileRepo);
        usernameProvider = Objects.requireNonNull(usernameProv);
    }

    @Override
    public final Optional<UserProfile> getCurrentProfile() {
        final Optional<String>      username;
        final Optional<UserProfile> profile;

        log.trace("Getting profile for user in session");

        username = usernameProvider.getCurrentUsername();

        if (username.isPresent()) {
            profile = userProfileRepository.findByUsername(username.get());
        } else {
            profile = Optional.empty();
        }

        log.trace("Found profile for user in session: {}", profile);

        return profile;
    }

}
