
package com.bernardomg.association.security.account.test.configuration.factory;

import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.association.security.account.domain.model.ProfileAccount;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;

public final class ProfileAccounts {

    public static final ProfileAccount noProfile() {
        return new ProfileAccount(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME, null);
    }

    public static final ProfileAccount valid() {
        return new ProfileAccount(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME, Profiles.valid());
    }

    private ProfileAccounts() {
        super();
    }

}
