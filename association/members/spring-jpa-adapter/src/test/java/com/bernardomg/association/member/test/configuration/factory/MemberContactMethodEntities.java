
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberContactMethodEntity;

public final class MemberContactMethodEntities {

    public static final MemberContactMethodEntity email() {
        final MemberContactMethodEntity entity;

        entity = new MemberContactMethodEntity();
        entity.setId(1L);
        entity.setNumber(MemberContactMethodConstants.NUMBER);
        entity.setName(MemberContactMethodConstants.EMAIL);

        return entity;
    }

    private MemberContactMethodEntities() {
        super();
    }

}
