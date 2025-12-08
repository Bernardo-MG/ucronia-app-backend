
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.filter.MemberFilter;

public final class MemberQueries {

    public static final MemberFilter empty() {
        return new MemberFilter(null, "");
    }

    private MemberQueries() {
        super();
    }

}
