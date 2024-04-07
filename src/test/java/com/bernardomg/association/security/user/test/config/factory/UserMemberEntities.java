
package com.bernardomg.association.security.user.test.config.factory;

import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserMemberEntity;

public final class UserMemberEntities {

    public static final UserMemberEntity valid() {
        return UserMemberEntity.builder()
            .withMemberId(1L)
            .withUserId(1L)
            .build();
    }

    private UserMemberEntities() {
        super();
    }

}
