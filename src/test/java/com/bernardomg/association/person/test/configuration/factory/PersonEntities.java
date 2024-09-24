
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;

public final class PersonEntities {

    public static final PersonEntity alternative() {
        return PersonEntity.builder()
            .withId(2L)
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .withFirstName(PersonConstants.NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final PersonEntity firstNameChange() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName("Person 123")
            .withLastName("Last name")
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final PersonEntity minimal() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName(PersonConstants.NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .withPhone("")
            .withIdentifier("")
            .build();
    }

    public static final PersonEntity missingLastName() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName(PersonConstants.NAME)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final PersonEntity valid() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName(PersonConstants.NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

    public static final PersonEntity valid(final int index) {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName("Member " + index)
            .withLastName("Last name " + index)
            .withPhone("12345")
            .withIdentifier("6789")
            .build();
    }

}
