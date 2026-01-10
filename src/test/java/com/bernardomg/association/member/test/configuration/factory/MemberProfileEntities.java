
package com.bernardomg.association.member.test.configuration.factory;

import java.util.Set;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberProfileEntity;
import com.bernardomg.association.profile.test.configuration.factory.ProfileEntities;

public final class MemberProfileEntities {

    public static final MemberProfileEntity active() {
        final MemberProfileEntity entity;
        final FeeTypeEntity       feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberProfileEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.valid());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberProfileEntity alternative() {
        final MemberProfileEntity entity;
        final FeeTypeEntity       feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberProfileEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.valid());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberProfileEntity created() {
        final MemberProfileEntity entity;
        final FeeTypeEntity       feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberProfileEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.valid());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    public static final MemberProfileEntity withEmail() {
        final MemberProfileEntity entity;
        final FeeTypeEntity       feeType;

        feeType = new FeeTypeEntity();
        feeType.setId(1L);
        feeType.setNumber(FeeConstants.FEE_TYPE_NUMBER);
        feeType.setName(FeeConstants.FEE_TYPE_NAME);
        feeType.setAmount(FeeConstants.FEE_TYPE_AMOUNT);

        entity = new MemberProfileEntity();
        entity.setId(1L);
        entity.setProfile(ProfileEntities.withEmail());
        entity.setFeeType(feeType);
        entity.setActive(true);
        entity.setRenew(true);
        entity.getProfile()
            .setTypes(Set.of(MemberEntityConstants.PROFILE_TYPE));

        return entity;
    }

    private MemberProfileEntities() {
        super();
    }

}
