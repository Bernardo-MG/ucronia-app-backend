
package com.bernardomg.association.member.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberFeeTypeEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.ReadMemberContactChannelEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.ReadMemberEntity;

public final class ReadMemberEntities {

    public static final ReadMemberEntity active() {
        final ReadMemberEntity    entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);
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
        final ReadMemberEntity    entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setId(2L);
        entity.setNumber(MemberConstants.ALTERNATIVE_NUMBER);
        entity.setFirstName(MemberConstants.FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);
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
        final ReadMemberEntity    entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);
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
        final ReadMemberEntity    entity;
        final MemberFeeTypeEntity feeType;

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.CHANGED_FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);
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
        final MemberFeeTypeEntity            feeType;
        final ReadMemberContactChannelEntity contactChannelEntity;

        contactChannelEntity = new ReadMemberContactChannelEntity();
        contactChannelEntity.setContactMethod(MemberContactMethodEntities.email());
        contactChannelEntity.setDetail(MemberConstants.EMAIL);

        feeType = new MemberFeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new ReadMemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);
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

    private ReadMemberEntities() {
        super();
    }

}
