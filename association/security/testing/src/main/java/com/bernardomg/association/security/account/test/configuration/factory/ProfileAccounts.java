
package com.bernardomg.association.security.account.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.security.account.domain.model.ProfileAccount;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;

public final class ProfileAccounts {

    public static final ProfileAccount noProfile() {
        return new ProfileAccount(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME, Optional.empty());
    }

    public static final ProfileAccount valid() {
        return new ProfileAccount(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME,
            Optional.of(AccountProfiles.valid()));
    }

    private ProfileAccounts() {
        super();
    }

}
