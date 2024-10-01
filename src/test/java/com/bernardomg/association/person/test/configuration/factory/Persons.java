
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;

public final class Persons {

    public static final Person alternative() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.ALTERNATIVE_FIRST_NAME)
            .withLastName(PersonConstants.ALTERNATIVE_LAST_NAME)
            .build();
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.ALTERNATIVE_NUMBER, name, PersonConstants.PHONE);
    }

    public static final Person emptyName() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(" ")
            .withLastName(" ")
            .build();
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.PHONE);
    }

    public static final Person nameChange() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person 123")
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.PHONE);
    }

    public static final Person paddedWithWhitespaces() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(" " + PersonConstants.FIRST_NAME + " ")
            .withLastName(" " + PersonConstants.LAST_NAME + " ")
            .build();
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.PHONE);
    }

    public static final Person valid() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return new Person(PersonConstants.IDENTIFIER, PersonConstants.NUMBER, name, PersonConstants.PHONE);
    }

}
