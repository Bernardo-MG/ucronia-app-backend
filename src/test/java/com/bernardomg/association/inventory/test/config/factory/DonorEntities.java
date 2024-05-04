
package com.bernardomg.association.inventory.test.config.factory;

import com.bernardomg.association.inventory.adapter.inbound.jpa.model.DonorEntity;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.association.member.test.config.factory.MemberEntities;

public final class DonorEntities {

    public static final DonorEntity withMember() {
        return DonorEntity.builder()
            .withName(MemberConstants.NAME_WITH_MEMBER)
            .withMember(MemberEntities.valid())
            .withNumber(DonorConstants.NUMBER)
            .build();
    }

    public static final DonorEntity withoutMember() {
        return DonorEntity.builder()
            .withName(DonorConstants.NAME)
            .withMember(null)
            .withNumber(DonorConstants.NUMBER)
            .build();
    }

}
