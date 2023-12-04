
package com.bernardomg.association.membership.test.member.util.model;

import com.bernardomg.association.membership.member.model.MemberStatus;
import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.model.request.MemberQueryRequest;

public final class MembersQuery {

    public static final MemberQuery active() {
        return MemberQueryRequest.builder()
            .status(MemberStatus.ACTIVE)
            .build();
    }

    public static final MemberQuery empty() {
        return MemberQueryRequest.builder()
            .build();
    }

    public static final MemberQuery inactive() {
        return MemberQueryRequest.builder()
            .status(MemberStatus.INACTIVE)
            .build();
    }

}
