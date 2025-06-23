
package com.bernardomg.association.person.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.Person.Membership;
import com.bernardomg.association.person.domain.model.PersonName;

public final class Persons {

    public static final Person alternative() {
        final PersonName name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.ALTERNATIVE_NUMBER, name,
            PersonConstants.BIRTH_DATE, PersonConstants.PHONE, Optional.empty());
    }

    public static final Person alternativeMembershipInactive() {
        final PersonName name;
        final Membership membership;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        membership = new Membership(false, true);
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.ALTERNATIVE_NUMBER, name,
            PersonConstants.BIRTH_DATE, PersonConstants.PHONE, Optional.of(membership));
    }

    public static final Person emptyName() {
        final PersonName name;

        name = new PersonName(" ", " ");
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE, Optional.empty());
    }

    public static final Person membershipActive() {
        final PersonName name;
        final Membership membership;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(true, true);
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE, Optional.of(membership));
    }

    public static final Person membershipActiveNew() {
        final PersonName name;
        final Membership membership;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(true, true);
        return new Person("", PersonConstants.NUMBER, name, null, "", Optional.of(membership));
    }

    public static final Person membershipActiveNoRenew() {
        final PersonName name;
        final Membership membership;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(true, false);
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE, Optional.of(membership));
    }

    public static final Person membershipInactive() {
        final PersonName name;
        final Membership membership;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(false, true);
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE, Optional.of(membership));
    }

    public static final Person membershipInactiveNew() {
        final PersonName name;
        final Membership membership;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        membership = new Membership(false, false);
        return new Person("", PersonConstants.NUMBER, name, null, "", Optional.of(membership));
    }

    public static final Person nameChange() {
        final PersonName name;

        name = new PersonName("Person 123", "Last name");
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE, Optional.empty());
    }

    public static final Person nameChangePatch() {
        final PersonName name;

        name = new PersonName("Person 123", "Last name");
        return new Person(null, PersonConstants.NUMBER, name, null, null, null);
    }

    public static final Person noMembership() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE, Optional.empty());
    }

    public static final Person noMembershipNew() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Person("", PersonConstants.NUMBER, name, null, "", Optional.empty());
    }

    public static final Person padded() {
        final PersonName name;

        name = new PersonName(" " + PersonConstants.FIRST_NAME + " ", " " + PersonConstants.LAST_NAME + " ");
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.BIRTH_DATE,
            PersonConstants.PHONE, Optional.empty());
    }

    public static final Person toCreate() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Person(PersonConstants.IDENTIFIER, -1L, name, PersonConstants.BIRTH_DATE, PersonConstants.PHONE,
            Optional.empty());
    }

}
