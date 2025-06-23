
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChange;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChange.Membership;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChangeName;

public final class PersonChanges {

    public static final PersonChange membershipActive() {
        final PersonChangeName name;
        final Membership       membership;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(true, true);
        return new PersonChange(PersonConstants.IDENTIFIER, name, membership, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE);
    }

    public static final PersonChange membershipInactive() {
        final PersonChangeName name;
        final Membership       membership;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(false, true);
        return new PersonChange(PersonConstants.IDENTIFIER, name, membership, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE);
    }

    public static final PersonChange noMembership() {
        final PersonChangeName name;

        name = new PersonChangeName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new PersonChange(PersonConstants.IDENTIFIER, name, null, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE);
    }

}
