
package com.bernardomg.association.profile.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.profile.domain.filter.ProfileFilter;

public final class ProfileQueries {

    public static final ProfileFilter alternativeFirstName() {
        return new ProfileFilter(Optional.of(ProfileConstants.ALTERNATIVE_FIRST_NAME));
    }

    public static final ProfileFilter empty() {
        return new ProfileFilter(Optional.empty());
    }

    public static final ProfileFilter firstName() {
        return new ProfileFilter(Optional.of(ProfileConstants.FIRST_NAME));
    }

    public static final ProfileFilter fullName() {
        return new ProfileFilter(Optional.of(ProfileConstants.FULL_NAME));
    }

    public static final ProfileFilter lastName() {
        return new ProfileFilter(Optional.of(ProfileConstants.LAST_NAME));
    }

    public static final ProfileFilter partialName() {
        return new ProfileFilter(
            Optional.of(ProfileConstants.FIRST_NAME.substring(0, ProfileConstants.FIRST_NAME.length() - 2)));
    }

    private ProfileQueries() {
        super();
    }

}
