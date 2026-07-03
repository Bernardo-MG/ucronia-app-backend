
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.PublicMemberEntity;

public final class PublicMemberEntities {

    public static final PublicMemberEntity alternative() {
        final PublicMemberEntity entity;

        entity = new PublicMemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(MemberConstants.ALTERNATIVE_LAST_NAME);

        return entity;
    }

    public static final PublicMemberEntity valid() {
        final PublicMemberEntity entity;

        entity = new PublicMemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(MemberConstants.FIRST_NAME);
        entity.setLastName(MemberConstants.LAST_NAME);

        return entity;
    }

    private PublicMemberEntities() {
        super();
    }

}
