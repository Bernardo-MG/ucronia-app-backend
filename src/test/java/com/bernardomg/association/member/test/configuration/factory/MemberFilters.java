
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.filter.MemberFilter;

public final class MemberFilters {

    public static final MemberFilter empty() {
        return new MemberFilter(null, "");
    }

    private MemberFilters() {
        super();
    }

}
