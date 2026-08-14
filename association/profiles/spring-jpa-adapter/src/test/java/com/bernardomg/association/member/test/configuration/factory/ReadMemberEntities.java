
package com.bernardomg.association.member.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.ReadMemberContactChannelEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.ReadMemberEntity;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class ReadMemberEntities {

    public static final ReadMemberEntity active() {
        final ReadMemberEntity entity;
        final FeeTypeEntity    feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier(MemberConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final ReadMemberEntity alternative() {
        final ReadMemberEntity entity;
        final FeeTypeEntity    feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setId(2L);
        entity.setNumber(MemberConstants.ALTERNATIVE_NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final ReadMemberEntity created() {
        final ReadMemberEntity entity;
        final FeeTypeEntity    feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier(MemberConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final ReadMemberEntity firstNameChange() {
        final ReadMemberEntity entity;
        final FeeTypeEntity    feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.CHANGED_FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>());
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final ReadMemberEntity withEmail() {
        final ReadMemberEntity               entity;
        final FeeTypeEntity                  feeType;
        final ReadMemberContactChannelEntity contactChannelEntity;

        contactChannelEntity = new ReadMemberContactChannelEntity();
        contactChannelEntity.setContactMethod(MemberContactMethodEntities.email());
        contactChannelEntity.setDetail(ProfileConstants.EMAIL);

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier("6789");
        entity.setContactChannels(new ArrayList<>(List.of(contactChannelEntity)));
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);

        contactChannelEntity.setProfile(entity);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);

        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    public static final ReadMemberEntity withKey() {
        final ReadMemberEntity entity;
        final FeeTypeEntity    feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setBirthDate(MemberConstants.BIRTH_DATE);
        entity.setIdentifier(MemberConstants.IDENTIFIER);
        entity.setContactChannels(new ArrayList<>(List.of()));
        entity.setAddress(MemberConstants.ADDRESS);
        entity.setComments(MemberConstants.COMMENTS);
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setKey(KeyEntities.available());
        entity.setTypes(new HashSet<>(List.of(MemberEntityConstants.PROFILE_TYPE)));

        return entity;
    }

    private ReadMemberEntities() {
        super();
    }

}
