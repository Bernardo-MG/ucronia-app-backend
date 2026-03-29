
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.MemberSummary;

public final class MemberSummaries {

    public static final MemberSummary valid() {
        return new MemberSummary(2L, 1L);
    }

}
