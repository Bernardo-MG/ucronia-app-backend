
package com.bernardomg.association.security.account.test.configuration.factory;

import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile;
import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile.Name;

public final class Profiles {

    public static final Profile valid() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Profile(ProfileConstants.IDENTIFIER, ProfileConstants.NUMBER, name);
    }

    private Profiles() {
        super();
    }

}
