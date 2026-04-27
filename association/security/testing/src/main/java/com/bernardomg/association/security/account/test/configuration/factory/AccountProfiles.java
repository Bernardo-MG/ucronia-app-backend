
package com.bernardomg.association.security.account.test.configuration.factory;

import java.util.List;
import java.util.Set;

import com.bernardomg.association.security.account.domain.model.AccountProfile;
import com.bernardomg.association.security.account.domain.model.AccountProfile.Name;

public final class AccountProfiles {

    public static final AccountProfile valid() {
        final Name name;

        name = new Name(AccountProfileConstants.FIRST_NAME, AccountProfileConstants.LAST_NAME);
        return new AccountProfile(AccountProfileConstants.IDENTIFIER, AccountProfileConstants.NUMBER, name,
            AccountProfileConstants.BIRTH_DATE, List.of(), AccountProfileConstants.ADDRESS,
            AccountProfileConstants.COMMENTS, Set.of());
    }

    private AccountProfiles() {
        super();
    }

}
