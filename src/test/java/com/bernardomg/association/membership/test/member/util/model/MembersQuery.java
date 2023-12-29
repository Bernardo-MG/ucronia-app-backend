
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.MemberStatus;
import com.bernardomg.association.membership.member.model.request.MemberQuery;

public final class MembersQuery {

    public static final MemberQuery active() {
        return MemberQuery.builder()
            .status(MemberStatus.ACTIVE)
            .build();
    }

    public static final MemberQuery empty() {
        return MemberQuery.builder()
            .build();
    }

    public static final MemberQuery inactive() {
        return MemberQuery.builder()
            .status(MemberStatus.INACTIVE)
            .build();
    }

}
