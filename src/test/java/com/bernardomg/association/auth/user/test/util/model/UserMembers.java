
package com.bernardomg.association.auth.user.test.util.model;

import com.bernardomg.association.auth.user.domain.model.UserMember;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class UserMembers {

    public static final UserMember alternative() {
        return UserMember.builder()
            .username(UserConstants.USERNAME)
            .name(UserConstants.NAME)
            .fullName(MemberConstants.ALTERNATIVE_FULL_NAME)
            .number(MemberConstants.ALTERNATIVE_NUMBER)
            .build();
    }

    public static final UserMember valid() {
        return UserMember.builder()
            .username(UserConstants.USERNAME)
            .name(UserConstants.NAME)
            .fullName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
    }

    private UserMembers() {
        super();
    }

}
