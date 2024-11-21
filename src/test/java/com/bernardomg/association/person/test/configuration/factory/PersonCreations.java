
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChangeName;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonCreation;

public final class PersonCreations {

    public static final PersonCreation membershipActive() {
        final PersonChangeName name;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return PersonCreation.builder()
            .withName(name)
            .build();
    }

    public static final PersonCreation membershipInactive() {
        final PersonChangeName name;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return PersonCreation.builder()
            .withName(name)
            .build();
    }

    public static final PersonCreation noMembership() {
        final PersonChangeName name;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return PersonCreation.builder()
            .withName(name)
            .build();
    }

}
