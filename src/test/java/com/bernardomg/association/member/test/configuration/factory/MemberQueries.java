
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.filter.MemberQuery;

public final class MemberQueries {

    public static final MemberQuery empty() {
        return new MemberQuery(null, "");
    }

    private MemberQueries() {
        super();
    }

}
