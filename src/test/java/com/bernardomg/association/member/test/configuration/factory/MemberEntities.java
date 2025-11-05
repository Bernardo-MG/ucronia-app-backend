
package com.bernardomg.association.member.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactChannelEntity;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;

public final class MemberEntities {

    public static final MemberEntity active() {
        final MemberEntity entity = new MemberEntity();
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

    public static final MemberEntity inactive() {
        final MemberEntity entity = new MemberEntity();
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

    public static final MemberEntity withEmail() {
        final MemberEntity         entity;
        final ContactChannelEntity personContactMethodEntity;

        personContactMethodEntity = new ContactChannelEntity();
        personContactMethodEntity.setContactMethod(ContactMethodEntities.email());
        personContactMethodEntity.setDetail(ContactConstants.EMAIL);

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setActive(true);
        entity.setRenewMembership(true);
        entity.setContactChannels(List.of(personContactMethodEntity));

        personContactMethodEntity.setContact(entity);

        return entity;
    }

    private MemberEntities() {
        super();
    }

}
