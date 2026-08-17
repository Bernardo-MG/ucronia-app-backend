
package com.bernardomg.association.security.user.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.profile.domain.model.Name;
import com.bernardomg.association.security.user.domain.model.UserProfile;

public final class UserProfiles {

    public static final UserProfile alternativeProfile() {
        final Name name;

        name = new Name(UserProfileConstants.ALTERNATIVE_FIRST_NAME, UserProfileConstants.ALTERNATIVE_LAST_NAME,
            Optional.empty());
        return new UserProfile(Optional.of(UserProfileConstants.ALTERNATIVE_IDENTIFIER),
            UserProfileConstants.ALTERNATIVE_NUMBER, name);
    }

    public static final UserProfile valid() {
        final Name name;

        name = new Name(UserProfileConstants.FIRST_NAME, UserProfileConstants.LAST_NAME, Optional.empty());
        return new UserProfile(Optional.of(UserProfileConstants.IDENTIFIER), UserProfileConstants.NUMBER, name);
    }

    private UserProfiles() {
        super();
    }

}
