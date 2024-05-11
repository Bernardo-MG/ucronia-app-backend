
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.GuestEntity;

public final class GuestEntities {

    public static final GuestEntity valid() {
        return GuestEntity.builder()
            .withNumber(GuestConstants.NUMBER)
            .withName(GuestConstants.NAME)
            .build();
    }

}
