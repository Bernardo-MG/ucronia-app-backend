
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChangeName;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonCreation;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonCreation.Membership;

public final class PersonCreations {

    public static final PersonCreation membershipActive() {
        final PersonChangeName name;
        final Membership       membership;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(true);
        return new PersonCreation(name, membership);
    }

    public static final PersonCreation membershipInactive() {
        final PersonChangeName name;
        final Membership       membership;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(false);
        return new PersonCreation(name, membership);
    }

    public static final PersonCreation noMembership() {
        final PersonChangeName name;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new PersonCreation(name, null);
    }

}
