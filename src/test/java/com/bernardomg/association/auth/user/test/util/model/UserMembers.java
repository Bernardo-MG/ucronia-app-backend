
package com.bernardomg.association.auth.user.test.util.model;

import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.auth.association.user.domain.model.UserMember;

public final class UserMembers {

    public static final UserMember alternative() {
        return UserMember.builder()
            .withUsername(UserConstants.USERNAME)
            .withName(UserConstants.NAME)
            .withFullName(MemberConstants.ALTERNATIVE_FULL_NAME)
            .withNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .build();
    }

    public static final UserMember valid() {
        return UserMember.builder()
            .withUsername(UserConstants.USERNAME)
            .withName(UserConstants.NAME)
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
    }

    private UserMembers() {
        super();
    }

}
