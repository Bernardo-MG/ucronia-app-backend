
package com.bernardomg.association.security.account.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile;
import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile.Name;

public final class AccountProfiles {

    public static final Profile alternative() {
        final Name name;

        name = new Name(AccountProfileConstants.FIRST_NAME, AccountProfileConstants.LAST_NAME);
        return new Profile(Optional.of(AccountProfileConstants.IDENTIFIER), AccountProfileConstants.NUMBER, name);
    }

    public static final Profile valid() {
        final Name name;

        name = new Name(AccountProfileConstants.FIRST_NAME, AccountProfileConstants.LAST_NAME);
        return new Profile(Optional.of(AccountProfileConstants.IDENTIFIER), AccountProfileConstants.NUMBER, name);
    }

    private AccountProfiles() {
        super();
    }

}
