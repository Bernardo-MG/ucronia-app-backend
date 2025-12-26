
package com.bernardomg.association.member.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberContactChannelEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberContactEntity;

public final class QueryMemberContactEntities {

    public static final QueryMemberContactEntity alternative() {
        final QueryMemberContactEntity entity;

        entity = new QueryMemberContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ContactConstants.ALTERNATIVE_LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ContactConstants.COMMENTS);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    public static final QueryMemberContactEntity created() {
        final QueryMemberContactEntity entity;

        entity = new QueryMemberContactEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ContactConstants.COMMENTS);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    public static final QueryMemberContactEntity valid() {
        final QueryMemberContactEntity entity;

        entity = new QueryMemberContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ContactConstants.COMMENTS);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    public static final QueryMemberContactEntity withEmail() {
        final QueryMemberContactEntity        entity;
        final QueryMemberContactChannelEntity contactChannelEntity;

        contactChannelEntity = new QueryMemberContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ContactConstants.EMAIL);

        entity = new QueryMemberContactEntity();
        entity.setId(1L);
        entity.setNumber(ContactConstants.NUMBER);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setFirstName(ContactConstants.FIRST_NAME);
        entity.setLastName(ContactConstants.LAST_NAME);
        entity.setBirthDate(ContactConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of(contactChannelEntity));
        entity.setComments(ContactConstants.COMMENTS);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    private QueryMemberContactEntities() {
        super();
    }

}
