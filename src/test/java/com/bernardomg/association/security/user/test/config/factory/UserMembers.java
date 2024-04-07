
package com.bernardomg.association.security.user.test.config.factory;

import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.association.security.user.domain.model.UserMember;
import com.bernardomg.association.security.user.domain.model.UserMemberName;

public final class UserMembers {

    public static final UserMember valid() {
        final UserMemberName name;

        name = UserMemberName.builder()
            .withFirstName(MemberConstants.NAME)
            .withLastName(MemberConstants.SURNAME)
            .withFullName(MemberConstants.FULL_NAME)
            .build();
        return UserMember.builder()
            .withUsername(UserConstants.USERNAME)
            .withName(name)
            .withNumber(MemberConstants.NUMBER)
            .build();
    }

    private UserMembers() {
        super();
    }

}
