
package com.bernardomg.association.membership.test.member.config.factory;

import com.bernardomg.association.membership.member.model.MemberQuery;
import com.bernardomg.association.membership.member.model.MemberStatus;

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
