
package com.bernardomg.association.person.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactEntity;

public final class ContactEntities {

    public static final ContactEntity alternative() {
        final ContactEntity entity = new ContactEntity();
        entity.setId(2L);
        entity.setNumber(ContactConstants.ALTERNATIVE_NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final ContactEntity firstNameChange() {
        final ContactEntity entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName("Contact 123");
        entity.setLastName("Last name");
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final ContactEntity membershipActive() {
        final ContactEntity entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(true);
        entity.setActive(true);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final ContactEntity membershipInactive() {
        final ContactEntity entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(true);
        entity.setActive(false);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final ContactEntity minimal() {
        final ContactEntity entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("");
        entity.setMember(false);
        entity.setActive(false);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final ContactEntity missingLastName() {
        final ContactEntity entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(false);
        entity.setActive(false);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final ContactEntity noMembership() {
        final ContactEntity entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(false);
        entity.setActive(true);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final ContactEntity noMembership(final int index) {
        final ContactEntity entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName("Member " + index);
        entity.setLastName("Last name " + index);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(false);
        entity.setActive(false);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final ContactEntity withEmail() {
        final ContactEntity        entity;
        final ContactChannelEntity personContactMethodEntity;

        personContactMethodEntity = new ContactChannelEntity();
        personContactMethodEntity.setContactMethod(ContactMethodEntities.email());
        personContactMethodEntity.setDetail(ContactConstants.EMAIL);

        entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setMember(true);
        entity.setActive(true);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of(personContactMethodEntity));

        personContactMethodEntity.setContact(entity);

        return entity;
    }

    private ContactEntities() {
        super();
    }

}
