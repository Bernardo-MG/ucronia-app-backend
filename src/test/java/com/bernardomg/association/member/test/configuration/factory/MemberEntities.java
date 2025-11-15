
package com.bernardomg.association.member.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberContactEntity;

public final class MemberEntities {

    public static final MemberContactEntity active() {
        final MemberContactEntity entity = new MemberContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setActive(true);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final MemberContactEntity inactive() {
        final MemberContactEntity entity = new MemberContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setActive(false);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of());
        return entity;
    }

    public static final MemberContactEntity withEmail() {
        final MemberContactEntity  entity;
        final ContactChannelEntity contactContactMethodEntity;

        contactContactMethodEntity = new ContactChannelEntity();
        contactContactMethodEntity.setContactMethod(ContactMethodEntities.email());
        contactContactMethodEntity.setDetail(ContactConstants.EMAIL);

        entity = new MemberContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setActive(true);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of(contactContactMethodEntity));

        contactContactMethodEntity.setContact(entity);

        return entity;
    }

    private MemberEntities() {
        super();
    }

}
