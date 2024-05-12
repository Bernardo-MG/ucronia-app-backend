
package com.bernardomg.association.person.test.config.factory;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;

public final class Persons {

    public static final Person alternative() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.ALTERNATIVE_NAME)
            .withLastName(PersonConstants.ALTERNATIVE_SURNAME)
            .build();
        return Person.builder()
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .build();
    }

    public static final Person emptyName() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(" ")
            .withLastName(" ")
            .build();
        return Person.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .build();
    }

    public static final Person nameChange() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person 123")
            .withLastName(PersonConstants.SURNAME)
            .build();
        return Person.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .build();
    }

    public static final Person paddedWithWhitespaces() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(" " + PersonConstants.NAME + " ")
            .withLastName(" " + PersonConstants.SURNAME + " ")
            .build();
        return Person.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .build();
    }

    public static final Person valid() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.NAME)
            .withLastName(PersonConstants.SURNAME)
            .build();
        return Person.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .build();
    }

}
