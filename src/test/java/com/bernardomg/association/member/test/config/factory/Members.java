
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.config.factory.PersonConstants;

public final class Members {

    public static final Member active() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.NAME)
            .withLastName(PersonConstants.SURNAME)
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(true)
            .build();
    }

    public static final Member active(final int index) {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person " + index)
            .withLastName("Surname " + index)
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(true)
            .build();
    }

    public static final Member emptyName() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(" ")
            .withLastName(PersonConstants.SURNAME)
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(true)
            .build();
    }

    public static final Member forIndex(final long index) {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person " + index)
            .withLastName("Surname " + index)
            .build();
        return Member.builder()
            .withNumber(index)
            .withName(name)
            .withPhone(String.valueOf(12344 + index))
            .withIdentifier(String.valueOf(6788 + index))
            .withActive(true)
            .build();
    }

    public static final Member forIndex(final long index, final boolean active) {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person " + index)
            .withLastName("Surname " + index)
            .build();
        return Member.builder()
            .withNumber(index * 10)
            .withName(name)
            .withPhone(String.valueOf(12344 + index))
            .withIdentifier(String.valueOf(6788 + index))
            .withActive(active)
            .build();
    }

    public static final Member inactive() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.NAME)
            .withLastName(PersonConstants.SURNAME)
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(false)
            .build();
    }

    public static final Member inactive(final int index) {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person " + index)
            .withLastName("Surname " + index)
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(false)
            .build();
    }

    public static final Member inactiveAlternative() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.ALTERNATIVE_NAME)
            .withLastName(PersonConstants.ALTERNATIVE_SURNAME)
            .build();
        return Member.builder()
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(false)
            .build();
    }

    public static final Member inactiveWithNumber(final long number) {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.NAME)
            .withLastName(PersonConstants.SURNAME)
            .build();
        return Member.builder()
            .withNumber(number)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(false)
            .build();
    }

    public static final Member missingName() {
        final PersonName name;

        name = PersonName.builder()
            .withLastName(PersonConstants.SURNAME)
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(false)
            .build();
    }

    public static final Member missingSurname() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.NAME)
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(false)
            .build();
    }

    public static final Member nameChange() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person 123")
            .withLastName("Surname")
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(true)
            .build();
    }

    public static final Member nameChangeActive() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName("Person 123")
            .withLastName("Surname")
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(true)
            .build();
    }

    public static final Member paddedWithWhitespaces() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(" " + PersonConstants.NAME + " ")
            .withLastName(" " + PersonConstants.SURNAME + " ")
            .build();
        return Member.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withPhone(PersonConstants.PHONE)
            .withIdentifier(PersonConstants.IDENTIFIER)
            .withActive(true)
            .build();
    }

}
