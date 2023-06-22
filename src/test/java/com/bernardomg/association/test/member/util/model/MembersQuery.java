
package com.bernardomg.association.test.member.util.model;

import com.bernardomg.association.member.model.request.MemberQuery;
import com.bernardomg.association.member.model.request.ValidatedMemberQuery;

public final class MembersQuery {

    public static final MemberQuery active() {
        return ValidatedMemberQuery.builder()
            .active(true)
            .build();
    }

    public static final MemberQuery empty() {
        return ValidatedMemberQuery.builder()
            .build();
    }

    public static final MemberQuery notActive() {
        return ValidatedMemberQuery.builder()
            .active(false)
            .build();
    }

}
