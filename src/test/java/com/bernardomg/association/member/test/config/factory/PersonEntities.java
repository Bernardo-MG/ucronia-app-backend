
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.PersonEntity;

public final class PersonEntities {

    public static final PersonEntity alternative() {
        return PersonEntity.builder()
            .withId(2L)
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .withName(PersonConstants.NAME)
            .withSurname(PersonConstants.SURNAME)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final PersonEntity minimal() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withName(PersonConstants.NAME)
            .withSurname(PersonConstants.SURNAME)
            .withPhone("")
            .withIdentifier("")
            .build();
    }

    public static final PersonEntity missingSurname() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withName(PersonConstants.NAME)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final PersonEntity nameChange() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withName("Person 123")
            .withSurname("Surname")
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final PersonEntity valid() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withName(PersonConstants.NAME)
            .withSurname(PersonConstants.SURNAME)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final PersonEntity valid(final int index) {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withName("Member " + index)
            .withSurname("Surname " + index)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

}
