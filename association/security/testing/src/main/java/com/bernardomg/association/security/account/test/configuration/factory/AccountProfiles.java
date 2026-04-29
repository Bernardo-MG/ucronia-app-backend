
package com.bernardomg.association.security.account.test.configuration.factory;

import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile;
import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile.Name;

public final class AccountProfiles {

    public static final Profile valid() {
        final Name name;

        name = new Name(AccountProfileConstants.FIRST_NAME, AccountProfileConstants.LAST_NAME);
        return new Profile(AccountProfileConstants.IDENTIFIER, AccountProfileConstants.NUMBER, name);
    }

    private AccountProfiles() {
        super();
    }

}
