
package com.bernardomg.association.security.user.test.config.factory;

import java.util.List;

import com.bernardomg.association.member.test.config.factory.MemberEntities;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserMemberEntity;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.model.UserEntity;

public final class UserMemberEntities {

    private static final String ENCODED_PASSWORD = "$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW";

    public static final UserMemberEntity valid() {
        return UserMemberEntity.builder()
            .withMember(MemberEntities.valid())
            .withUser(enabledUser())
            .build();
    }

    private static final UserEntity enabledUser() {
        return UserEntity.builder()
            .withId(1L)
            .withName(UserConstants.NAME)
            .withUsername(UserConstants.USERNAME)
            .withEmail(UserConstants.EMAIL)
            .withPassword(ENCODED_PASSWORD)
            .withEnabled(true)
            .withExpired(false)
            .withPasswordExpired(false)
            .withLocked(false)
            .withRoles(List.of())
            .build();
    }

    private UserMemberEntities() {
        super();
    }

}
