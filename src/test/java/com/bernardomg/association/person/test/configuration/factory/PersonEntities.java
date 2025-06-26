
package com.bernardomg.association.person.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;

public final class PersonEntities {

    public static final PersonEntity alternative() {
        final PersonEntity entity = new PersonEntity();
        entity.setId(2L);
        entity.setNumber(PersonConstants.ALTERNATIVE_NUMBER);
        entity.setFirstName(PersonConstants.FIRST_NAME);
        entity.setLastName(PersonConstants.LAST_NAME);
        entity.setBirthDate(PersonConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setRenewMembership(true);
        entity.setContacts(List.of());
        return entity;
    }

    public static final PersonEntity firstNameChange() {
        final PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setNumber(PersonConstants.NUMBER);
        entity.setFirstName("Person 123");
        entity.setLastName("Last name");
        entity.setBirthDate(PersonConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setRenewMembership(true);
        entity.setContacts(List.of());
        return entity;
    }

    public static final PersonEntity membershipActive() {
        final PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setNumber(PersonConstants.NUMBER);
        entity.setFirstName(PersonConstants.FIRST_NAME);
        entity.setLastName(PersonConstants.LAST_NAME);
        entity.setBirthDate(PersonConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(true);
        entity.setActive(true);
        entity.setRenewMembership(true);
        entity.setContacts(List.of());
        return entity;
    }

    public static final PersonEntity membershipInactive() {
        final PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setNumber(PersonConstants.NUMBER);
        entity.setFirstName(PersonConstants.FIRST_NAME);
        entity.setLastName(PersonConstants.LAST_NAME);
        entity.setBirthDate(PersonConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(true);
        entity.setActive(false);
        entity.setRenewMembership(true);
        entity.setContacts(List.of());
        return entity;
    }

    public static final PersonEntity minimal() {
        final PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setNumber(PersonConstants.NUMBER);
        entity.setFirstName(PersonConstants.FIRST_NAME);
        entity.setLastName(PersonConstants.LAST_NAME);
        entity.setBirthDate(PersonConstants.BIRTH_DATE);
        entity.setIdentifier("");
        entity.setMember(false);
        entity.setActive(false);
        entity.setRenewMembership(true);
        entity.setContacts(List.of());
        return entity;
    }

    public static final PersonEntity missingLastName() {
        final PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setNumber(PersonConstants.NUMBER);
        entity.setFirstName(PersonConstants.FIRST_NAME);
        entity.setBirthDate(PersonConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(false);
        entity.setActive(false);
        entity.setRenewMembership(true);
        entity.setContacts(List.of());
        return entity;
    }

    public static final PersonEntity noMembership() {
        final PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setNumber(PersonConstants.NUMBER);
        entity.setFirstName(PersonConstants.FIRST_NAME);
        entity.setLastName(PersonConstants.LAST_NAME);
        entity.setBirthDate(PersonConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(false);
        entity.setActive(true);
        entity.setRenewMembership(true);
        entity.setContacts(List.of());
        return entity;
    }

    public static final PersonEntity noMembership(final int index) {
        final PersonEntity entity = new PersonEntity();
        entity.setId(1L);
        entity.setNumber(PersonConstants.NUMBER);
        entity.setFirstName("Member " + index);
        entity.setLastName("Last name " + index);
        entity.setBirthDate(PersonConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(false);
        entity.setActive(false);
        entity.setRenewMembership(true);
        entity.setContacts(List.of());
        return entity;
    }

    private PersonEntities() {
        super();
    }

}
