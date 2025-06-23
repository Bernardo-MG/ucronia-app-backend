
package com.bernardomg.association.security.account.test.configuration.factory;

import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.association.security.account.domain.model.PersonAccount;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;

public final class PersonAccounts {

    public static final PersonAccount noPerson() {
        return new PersonAccount(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME, null);
    }

    public static final PersonAccount valid() {
        return new PersonAccount(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME,
            Persons.noMembership());
    }

    private PersonAccounts() {
        super();
    }

}
