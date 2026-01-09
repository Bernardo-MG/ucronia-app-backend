
package com.bernardomg.association.member.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberContactChannelEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberProfileEntity;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class QueryMemberProfileEntities {

    public static final QueryMemberProfileEntity active() {
        final QueryMemberProfileEntity entity;
        final FeeTypeEntity            feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new QueryMemberProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFeeType(feeType);
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
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final QueryMemberProfileEntity alternative() {
        final QueryMemberProfileEntity entity;
        final FeeTypeEntity            feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new QueryMemberProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFeeType(feeType);
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
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final QueryMemberProfileEntity created() {
        final QueryMemberProfileEntity entity;
        final FeeTypeEntity            feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new QueryMemberProfileEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFeeType(feeType);
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
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final QueryMemberProfileEntity withEmail() {
        final QueryMemberProfileEntity        entity;
        final QueryMemberContactChannelEntity contactChannelEntity;
        final FeeTypeEntity                   feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        contactChannelEntity = new QueryMemberContactChannelEntity();
        contactChannelEntity.setContactMethod(ContactMethodEntities.email());
        contactChannelEntity.setDetail(ProfileConstants.EMAIL);

        entity = new QueryMemberProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFeeType(feeType);
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
        entity.setTypes(new HashSet<>(Set.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    private QueryMemberProfileEntities() {
        super();
    }

}
