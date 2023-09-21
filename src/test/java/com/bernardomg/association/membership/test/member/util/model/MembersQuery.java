
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.MemberStatus;
import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.model.request.ValidatedMemberQuery;

public final class MembersQuery {

    public static final MemberQuery active() {
        return ValidatedMemberQuery.builder()
            .status(MemberStatus.ACTIVE)
            .build();
    }

    public static final MemberQuery empty() {
        return ValidatedMemberQuery.builder()
            .build();
    }

    public static final MemberQuery notActive() {
        return ValidatedMemberQuery.builder()
            .status(MemberStatus.INACTIVE)
            .build();
    }

}
