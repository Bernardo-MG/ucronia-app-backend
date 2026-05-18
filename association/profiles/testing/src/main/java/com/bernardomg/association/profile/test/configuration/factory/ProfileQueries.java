
package com.bernardomg.association.profile.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.profile.domain.filter.ProfileQuery;

public final class ProfileQueries {

    public static final ProfileQuery alternativeFirstName() {
        return new ProfileQuery(Optional.of(ProfileConstants.ALTERNATIVE_FIRST_NAME));
    }

    public static final ProfileQuery empty() {
        return new ProfileQuery(Optional.empty());
    }

    public static final ProfileQuery firstName() {
        return new ProfileQuery(Optional.of(ProfileConstants.FIRST_NAME));
    }

    public static final ProfileQuery fullName() {
        return new ProfileQuery(Optional.of(ProfileConstants.FULL_NAME));
    }

    public static final ProfileQuery lastName() {
        return new ProfileQuery(Optional.of(ProfileConstants.LAST_NAME));
    }

    public static final ProfileQuery partialName() {
        return new ProfileQuery(
            Optional.of(ProfileConstants.FIRST_NAME.substring(0, ProfileConstants.FIRST_NAME.length() - 2)));
    }

    private ProfileQueries() {
        super();
    }

}
