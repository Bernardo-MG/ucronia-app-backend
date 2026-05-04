
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.filter.PublicMemberFilter;

public final class PublicMemberFilters {

    public static final PublicMemberFilter empty() {
        return new PublicMemberFilter("");
    }

    private PublicMemberFilters() {
        super();
    }

}
