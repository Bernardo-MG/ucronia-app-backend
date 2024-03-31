
package com.bernardomg.association.auth.user.test.config.factory;

import com.bernardomg.association.auth.user.domain.model.UserMember;
import com.bernardomg.association.auth.user.domain.model.UserMemberName;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class UserMembers {

    public static final UserMember alternative() {
        final UserMemberName name;

        name = UserMemberName.builder()
            .withFirstName(MemberConstants.ALTERNATIVE_NAME)
            .withLastName(MemberConstants.ALTERNATIVE_SURNAME)
            .withFullName(MemberConstants.ALTERNATIVE_FULL_NAME)
            .build();
        return UserMember.builder()
            .withUsername(UserConstants.USERNAME)
            .withName(name)
            .withNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .build();
    }

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
