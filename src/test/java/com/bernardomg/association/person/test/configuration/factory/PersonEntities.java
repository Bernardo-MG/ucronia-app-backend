
package com.bernardomg.association.person.test.configuration.factory;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;

public final class PersonEntities {

    public static final PersonEntity alternative() {
        return PersonEntity.builder()
            .withId(2L)
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .withBirthDate(PersonConstants.BIRTH_DATE)
            .withPhone("12345")
            .withIdentifier("6789")
            .withRenewMembership(true)
            .build();
    }

    public static final PersonEntity firstNameChange() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName("Person 123")
            .withLastName("Last name")
            .withBirthDate(PersonConstants.BIRTH_DATE)
            .withPhone("12345")
            .withIdentifier("6789")
            .withRenewMembership(true)
            .build();
    }

    public static final PersonEntity membershipActive() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .withBirthDate(PersonConstants.BIRTH_DATE)
            .withPhone("12345")
            .withIdentifier("6789")
            .withMember(true)
            .withActive(true)
            .withRenewMembership(true)
            .build();
    }

    public static final PersonEntity membershipInactive() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .withBirthDate(PersonConstants.BIRTH_DATE)
            .withPhone("12345")
            .withIdentifier("6789")
            .withMember(true)
            .withActive(false)
            .withRenewMembership(true)
            .build();
    }

    public static final PersonEntity minimal() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .withBirthDate(PersonConstants.BIRTH_DATE)
            .withPhone("")
            .withIdentifier("")
            .withMember(false)
            .withActive(false)
            .withRenewMembership(true)
            .build();
    }

    public static final PersonEntity missingLastName() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName(PersonConstants.FIRST_NAME)
            .withBirthDate(PersonConstants.BIRTH_DATE)
            .withPhone("12345")
            .withIdentifier("6789")
            .withMember(false)
            .withActive(false)
            .withRenewMembership(true)
            .build();
    }

    public static final PersonEntity noMembership() {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName(PersonConstants.FIRST_NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .withBirthDate(PersonConstants.BIRTH_DATE)
            .withPhone("12345")
            .withIdentifier("6789")
            .withMember(false)
            .withActive(true)
            .withRenewMembership(true)
            .build();
    }

    public static final PersonEntity noMembership(final int index) {
        return PersonEntity.builder()
            .withId(1L)
            .withNumber(PersonConstants.NUMBER)
            .withFirstName("Member " + index)
            .withLastName("Last name " + index)
            .withBirthDate(PersonConstants.BIRTH_DATE)
            .withPhone("12345")
            .withIdentifier("6789")
            .withMember(false)
            .withActive(false)
            .withRenewMembership(true)
            .build();
    }

}
