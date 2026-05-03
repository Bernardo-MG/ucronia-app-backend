
package com.bernardomg.association.member.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberContactChannelEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberFeeTypeEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberInnerProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberProfileEntity;

public final class MemberProfileEntities {

    public static final MemberProfileEntity active() {
        final MemberProfileEntity entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberProfileEntity();
        entity.setId(1L);
        entity.setProfile(valid());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberProfileEntity alternative() {
        final MemberProfileEntity entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberProfileEntity();
        entity.setId(1L);
        entity.setProfile(alternativeProfile());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberProfileEntity created() {
        final MemberProfileEntity entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberProfileEntity();
        entity.setId(1L);
        entity.setProfile(valid());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberProfileEntity firstNameChange() {
        final MemberProfileEntity entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberProfileEntity();
        entity.setId(1L);
        entity.setProfile(firstNameChangeProfile());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberProfileEntity withEmail() {
        final MemberProfileEntity entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberProfileEntity();
        entity.setId(1L);
        entity.setProfile(withEmailProfile());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    private static final MemberInnerProfileEntity alternativeProfile() {
        final MemberInnerProfileEntity entity;

        entity = new MemberInnerProfileEntity();
        entity.setId(2L);
        entity.setNumber(MemberProfileConstants.ALTERNATIVE_NUMBER);
        entity.setFirstName(MemberProfileConstants.FIRST_NAME);
        entity.setLastName(MemberProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(MemberProfileConstants.ADDRESS);
        entity.setComments(MemberProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    private static final MemberInnerProfileEntity firstNameChangeProfile() {
        final MemberInnerProfileEntity entity;

        entity = new MemberInnerProfileEntity();
        entity.setId(1L);
        entity.setNumber(MemberProfileConstants.NUMBER);
        entity.setFirstName(MemberProfileConstants.CHANGED_FIRST_NAME);
        entity.setLastName(MemberProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(MemberProfileConstants.ADDRESS);
        entity.setComments(MemberProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    private static final MemberInnerProfileEntity valid() {
        final MemberInnerProfileEntity entity;

        entity = new MemberInnerProfileEntity();
        entity.setId(1L);
        entity.setNumber(MemberProfileConstants.NUMBER);
        entity.setFirstName(MemberProfileConstants.FIRST_NAME);
        entity.setLastName(MemberProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberProfileConstants.BIRTH_DATE);
        entity.setIdentifier(MemberProfileConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(MemberProfileConstants.ADDRESS);
        entity.setComments(MemberProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    private static final MemberInnerProfileEntity withEmailProfile() {
        final MemberInnerProfileEntity   entity;
        final MemberContactChannelEntity contactChannelEntity;

        contactChannelEntity = new MemberContactChannelEntity();
        contactChannelEntity.setContactMethod(MemberContactMethodEntities.email());
        contactChannelEntity.setDetail(MemberProfileConstants.EMAIL);

        entity = new MemberInnerProfileEntity();
        entity.setId(1L);
        entity.setNumber(MemberProfileConstants.NUMBER);
        entity.setFirstName(MemberProfileConstants.FIRST_NAME);
        entity.setLastName(MemberProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setAddress(MemberProfileConstants.ADDRESS);
        entity.setComments(MemberProfileConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        contactChannelEntity.setProfile(entity);

        return entity;
    }

    private MemberProfileEntities() {
        super();
    }

}
