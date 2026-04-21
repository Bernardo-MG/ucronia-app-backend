
package com.bernardomg.association.member.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberFeeTypeEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.ReadMemberContactChannelEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.ReadMemberProfileEntity;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class ReadMemberProfileEntities {

    public static final ReadMemberProfileEntity active() {
        final ReadMemberProfileEntity entity;
        final MemberFeeTypeEntity     feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberProfileEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier(ProfileConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final ReadMemberProfileEntity alternative() {
        final ReadMemberProfileEntity entity;
        final MemberFeeTypeEntity     feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberProfileEntity();
        entity.setId(1L);
        entity.setId(2L);
        entity.setNumber(ProfileConstants.ALTERNATIVE_NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final ReadMemberProfileEntity created() {
        final ReadMemberProfileEntity entity;
        final MemberFeeTypeEntity     feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberProfileEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier(ProfileConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final ReadMemberProfileEntity firstNameChange() {
        final ReadMemberProfileEntity entity;
        final MemberFeeTypeEntity     feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberProfileEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.CHANGED_FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final ReadMemberProfileEntity withEmail() {
        final ReadMemberProfileEntity        entity;
        final MemberFeeTypeEntity            feeType;
        final ReadMemberContactChannelEntity contactChannelEntity;

        contactChannelEntity = new ReadMemberContactChannelEntity();
        contactChannelEntity.setContactMethod(MemberContactMethodEntities.email());
        contactChannelEntity.setDetail(ProfileConstants.EMAIL);

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberProfileEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(ProfileConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setAddress(ProfileConstants.ADDRESS);
        entity.setComments(ProfileConstants.COMMENTS);

        contactChannelEntity.setProfile(entity);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);

        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    private ReadMemberProfileEntities() {
        super();
    }

}
