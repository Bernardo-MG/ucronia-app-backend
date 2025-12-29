
package com.bernardomg.association.library.book.test.configuration.factory;

import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class Donors {

    public static final Donor valid() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Donor(ProfileConstants.NUMBER, name);
    }

    private Donors() {
        super();
    }

}
