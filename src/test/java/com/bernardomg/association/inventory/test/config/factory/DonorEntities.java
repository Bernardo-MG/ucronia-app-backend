
package com.bernardomg.association.inventory.test.config.factory;

import com.bernardomg.association.inventory.adapter.inbound.jpa.model.DonorEntity;

public final class DonorEntities {

    public static final DonorEntity noMember() {
        return DonorEntity.builder()
            .withName(DonorConstants.NAME)
            .withMember(null)
            .withNumber(DonorConstants.NUMBER)
            .build();
    }

}
