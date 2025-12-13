
package com.bernardomg.association.contact.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;

public final class ContactEntities {

    public static final ContactEntity alternative() {
        final ContactEntity entity;

        entity = new ContactEntity();
        entity.setId(2L);
        entity.setNumber(ContactConstants.ALTERNATIVE_NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of());
        entity.setComments(ContactConstants.COMMENTS);

        return entity;
    }

    public static final ContactEntity createdWithEmail() {
        final ContactEntity        entity;
        final ContactChannelEntity contactChannelEntity;

        contactChannelEntity = new ContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ContactConstants.EMAIL);

        entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of(contactChannelEntity));
        entity.setComments(ContactConstants.COMMENTS);

        contactChannelEntity.setContact(entity);

        return entity;
    }

    public static final ContactEntity firstNameChange() {
        final ContactEntity entity;

        entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName("Contact 123");
        entity.setLastName("Last name");
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of());
        entity.setComments(ContactConstants.COMMENTS);

        return entity;
    }

    public static final ContactEntity missingLastName() {
        final ContactEntity entity;

        entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of());
        entity.setComments(ContactConstants.COMMENTS);

        return entity;
    }

    public static final ContactEntity valid() {
        final ContactEntity entity;

        entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier(ContactConstants.IDENTIFIER);
        entity.setContactChannels(List.of());
        entity.setComments(ContactConstants.COMMENTS);

        return entity;
    }

    public static final ContactEntity withEmail() {
        final ContactEntity        entity;
        final ContactChannelEntity contactChannelEntity;

        contactChannelEntity = new ContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ContactConstants.EMAIL);

        entity = new ContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of(contactChannelEntity));
        entity.setComments(ContactConstants.COMMENTS);

        contactChannelEntity.setContact(entity);

        return entity;
    }

    private ContactEntities() {
        super();
    }

}
