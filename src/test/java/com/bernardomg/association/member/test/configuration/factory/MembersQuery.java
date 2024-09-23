
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.MemberQuery;
import com.bernardomg.association.member.domain.model.MemberStatus;

public final class MembersQuery {

    public static final MemberQuery active() {
        return MemberQuery.builder()
            .withStatus(MemberStatus.ACTIVE)
            .build();
    }

    public static final MemberQuery empty() {
        return MemberQuery.builder()
            .build();
    }

    public static final MemberQuery inactive() {
        return MemberQuery.builder()
            .withStatus(MemberStatus.INACTIVE)
            .build();
    }

}
