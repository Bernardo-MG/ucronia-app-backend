
package com.bernardomg.association.member.test.configuration.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberContactChannelEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;

public final class MemberEntities {

    public static final MemberEntity active() {
        final MemberEntity  entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
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
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberEntity alternative() {
        final MemberEntity  entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
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
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberEntity created() {
        final MemberEntity  entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
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
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberEntity firstNameChange() {
        final MemberEntity  entity;
        final FeeTypeEntity feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
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
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberEntity withEmail() {
        final MemberEntity               entity;
        final FeeTypeEntity              feeType;
        final MemberContactChannelEntity contactChannelEntity;

        contactChannelEntity = new MemberContactChannelEntity();
        contactChannelEntity.setContactMethod(MemberContactMethodEntities.email());
        contactChannelEntity.setDetail(MemberConstants.EMAIL);

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberEntity();
        entity.setId(1L);
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
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        contactChannelEntity.setProfile(entity);

        return entity;
    }

    private MemberEntities() {
        super();
    }

}
