
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.PublicMemberEntity;

public final class PublicMemberEntities {

    public static final PublicMemberEntity alternative() {
        final PublicMemberEntity entity;

        entity = new PublicMemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberProfileConstants.NUMBER);
        entity.setFirstName(MemberProfileConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(MemberProfileConstants.ALTERNATIVE_LAST_NAME);

        return entity;
    }

    public static final PublicMemberEntity valid() {
        final PublicMemberEntity entity;

        entity = new PublicMemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberProfileConstants.NUMBER);
        entity.setFirstName(MemberProfileConstants.FIRST_NAME);
        entity.setLastName(MemberProfileConstants.LAST_NAME);

        return entity;
    }

    private PublicMemberEntities() {
        super();
    }

}
