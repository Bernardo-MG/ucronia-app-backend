
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.fee.domain.model.FeeProfile;
import com.bernardomg.association.fee.domain.model.FeeProfile.Name;

public final class FeeProfiles {

    public static final FeeProfile valid() {
        final Name name;

        name = new Name(FeeProfileConstants.FIRST_NAME, FeeProfileConstants.LAST_NAME);
        return new FeeProfile(Optional.of(FeeProfileConstants.IDENTIFIER), FeeProfileConstants.NUMBER, name);
    }

    private FeeProfiles() {
        super();
    }

}
