
package com.bernardomg.association.member.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberContactChannelEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberProfileEntity;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class QueryMemberContactEntities {

    public static final QueryMemberProfileEntity alternative() {
        final QueryMemberProfileEntity entity;

        entity = new QueryMemberProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ProfileConstants.ALTERNATIVE_LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    public static final QueryMemberProfileEntity created() {
        final QueryMemberProfileEntity entity;

        entity = new QueryMemberProfileEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    public static final QueryMemberProfileEntity valid() {
        final QueryMemberProfileEntity entity;

        entity = new QueryMemberProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    public static final QueryMemberProfileEntity withEmail() {
        final QueryMemberProfileEntity        entity;
        final QueryMemberContactChannelEntity contactChannelEntity;

        contactChannelEntity = new QueryMemberContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ProfileConstants.EMAIL);

        entity = new QueryMemberProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(List.of(contactChannelEntity));
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.CONTACT_TYPE)));

        return entity;
    }

    private QueryMemberContactEntities() {
        super();
    }

}
