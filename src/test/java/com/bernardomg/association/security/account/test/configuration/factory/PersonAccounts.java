
package com.bernardomg.association.security.account.test.configuration.factory;

import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.association.security.account.domain.model.PersonAccount;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;

public final class PersonAccounts {

    public static final PersonAccount noPerson() {
        return PersonAccount.builder()
            .withUsername(UserConstants.USERNAME)
            .withName(UserConstants.NAME)
            .withEmail(UserConstants.EMAIL)
            .build();
    }

    public static final PersonAccount valid() {
        return PersonAccount.builder()
            .withUsername(UserConstants.USERNAME)
            .withName(UserConstants.NAME)
            .withEmail(UserConstants.EMAIL)
            .withPerson(Persons.valid())
            .build();
    }

    private PersonAccounts() {
        super();
    }

}
