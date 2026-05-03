
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;

public final class MemberEntities {

    public static final MemberEntity alternative() {
        final MemberEntity entity;

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberProfileConstants.NUMBER);
        entity.setFirstName(MemberProfileConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(MemberProfileConstants.ALTERNATIVE_LAST_NAME);

        return entity;
    }

    public static final MemberEntity valid() {
        final MemberEntity entity;

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberProfileConstants.NUMBER);
        entity.setFirstName(MemberProfileConstants.FIRST_NAME);
        entity.setLastName(MemberProfileConstants.LAST_NAME);

        return entity;
    }

    private MemberEntities() {
        super();
    }

}
