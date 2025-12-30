
package com.bernardomg.association.profile.test.configuration.factory;

import com.bernardomg.association.profile.domain.filter.ProfileQuery;

public final class ProfileQueries {

    public static final ProfileQuery empty() {
        return new ProfileQuery("");
    }

    private ProfileQueries() {
        super();
    }

}
