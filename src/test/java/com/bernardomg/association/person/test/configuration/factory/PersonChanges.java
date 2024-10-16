
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChange;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChange.Membership;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChangeName;

public final class PersonChanges {

    public static final PersonChange membershipActive() {
        final PersonChangeName name;
        final Membership       membership;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(true);
        return PersonChange.builder()
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withMembership(membership)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .build();
    }

    public static final PersonChange membershipInactive() {
        final PersonChangeName name;
        final Membership       membership;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(false);
        return PersonChange.builder()
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withMembership(membership)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .build();
    }

    public static final PersonChange noMembership() {
        final PersonChangeName name;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return PersonChange.builder()
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .build();
    }

}
