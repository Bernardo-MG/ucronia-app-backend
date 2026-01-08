
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntity;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class QueryMemberEntities {

    public static final QueryMemberEntity alternative() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ProfileConstants.ALTERNATIVE_LAST_NAME);

        return entity;
    }

    public static final QueryMemberEntity valid() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);

        return entity;
    }

    private QueryMemberEntities() {
        super();
    }

}
