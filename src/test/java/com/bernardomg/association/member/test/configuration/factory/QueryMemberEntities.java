
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntity;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class QueryMemberEntities {

    public static final QueryMemberEntity active() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setActive(true);
        entity.setRenew(true);

        return entity;
    }

    public static final QueryMemberEntity alternative() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.ALTERNATIVE_FIRST_NAME);
        entity.setLastName(ProfileConstants.ALTERNATIVE_LAST_NAME);
        entity.setActive(true);
        entity.setRenew(true);

        return entity;
    }

    public static final QueryMemberEntity created() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(1L);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setActive(true);
        entity.setRenew(true);

        return entity;
    }

    public static final QueryMemberEntity inactive() {
        final QueryMemberEntity entity;

        entity = new QueryMemberEntity();
        entity.setId(1L);
        entity.setNumber(ProfileConstants.NUMBER);
        entity.setFirstName(ProfileConstants.FIRST_NAME);
        entity.setLastName(ProfileConstants.LAST_NAME);
        entity.setActive(false);
        entity.setRenew(true);

        return entity;
    }

    private QueryMemberEntities() {
        super();
    }

}
