
package com.bernardomg.association.member.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberContactChannelEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberFeeTypeEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberInnerProfileEntity;

public final class MemberEntities {

    public static final MemberEntity active() {
        final MemberEntity        entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setProfile(valid());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberEntity alternative() {
        final MemberEntity        entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setProfile(alternativeProfile());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberEntity created() {
        final MemberEntity        entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setProfile(valid());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberEntity firstNameChange() {
        final MemberEntity        entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setProfile(firstNameChangeProfile());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberEntity withEmail() {
        final MemberEntity        entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
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
        entity.setNumber(MemberConstants.ALTERNATIVE_NUMBER);
        entity.setFirstName(MemberConstants.FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    private static final MemberInnerProfileEntity firstNameChangeProfile() {
        final MemberInnerProfileEntity entity;

        entity = new MemberInnerProfileEntity();
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.CHANGED_FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    private static final MemberInnerProfileEntity valid() {
        final MemberInnerProfileEntity entity;

        entity = new MemberInnerProfileEntity();
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier(MemberConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        return entity;
    }

    private static final MemberInnerProfileEntity withEmailProfile() {
        final MemberInnerProfileEntity   entity;
        final MemberContactChannelEntity contactChannelEntity;

        contactChannelEntity = new MemberContactChannelEntity();
        contactChannelEntity.setContactMethod(MemberContactMethodEntities.email());
        contactChannelEntity.setDetail(MemberConstants.EMAIL);

        entity = new MemberInnerProfileEntity();
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);
        entity.setTypes(new HashSet<>());

        contactChannelEntity.setProfile(entity);

        return entity;
    }

    private MemberEntities() {
        super();
    }

}
