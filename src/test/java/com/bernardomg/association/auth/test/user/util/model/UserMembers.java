
package com.bernardomg.association.auth.test.user.util.model;

import com.bernardomg.association.auth.user.model.UserMember;
import com.bernardomg.association.membership.test.member.util.model.MemberConstants;

public final class UserMembers {

    public static final UserMember valid() {
        return UserMember.builder()
            .username(UserConstants.USERNAME)
            .name(UserConstants.NAME)
            .memberName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
    }

    private UserMembers() {
        super();
    }

}
