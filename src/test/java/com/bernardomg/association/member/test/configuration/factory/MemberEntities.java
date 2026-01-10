
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class MemberEntities {

    public static final MemberEntity alternative() {
        final MemberEntity entity;

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ProfileConstants.ALTERNATIVE_LAST_NAME);

        return entity;
    }

    public static final MemberEntity valid() {
        final MemberEntity entity;

        entity = new MemberEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);

        return entity;
    }

    private MemberEntities() {
        super();
    }

}
