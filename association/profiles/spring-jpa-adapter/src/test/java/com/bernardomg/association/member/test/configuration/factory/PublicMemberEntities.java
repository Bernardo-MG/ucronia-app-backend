
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.PublicMemberEntity;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class PublicMemberEntities {

    public static final PublicMemberEntity alternative() {
        final PublicMemberEntity entity;

        entity = new PublicMemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(ProfileConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ProfileConstants.ALTERNATIVE_LAST_NAME);

        return entity;
    }

    public static final PublicMemberEntity valid() {
        final PublicMemberEntity entity;

        entity = new PublicMemberEntity();
        entity.setId(1L);
        entity.setNumber(MemberConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);

        return entity;
    }

    private PublicMemberEntities() {
        super();
    }

}
