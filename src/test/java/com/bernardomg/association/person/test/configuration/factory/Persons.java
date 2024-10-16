
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;

public final class Persons {

    public static final Person alternative() {
        final PersonName name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.ALTERNATIVE_NUMBER, name, PersonConstants.PHONE);
    }

    public static final Person emptyName() {
        final PersonName name;

        name = new PersonName(" ", " ");
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.PHONE);
    }

    public static final Person nameChange() {
        final PersonName name;

        name = new PersonName("Person 123", PersonConstants.LAST_NAME);
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.PHONE);
    }

    public static final Person paddedWithWhitespaces() {
        final PersonName name;

        name = new PersonName(" " + PersonConstants.FIRST_NAME + " ", " " + PersonConstants.LAST_NAME + " ");
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.PHONE);
    }

    public static final Person toCreate() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Person(PersonConstants.IDENTIFIER, -1L, name, PersonConstants.PHONE);
    }

    public static final Person valid() {
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.PHONE);
    }

}
