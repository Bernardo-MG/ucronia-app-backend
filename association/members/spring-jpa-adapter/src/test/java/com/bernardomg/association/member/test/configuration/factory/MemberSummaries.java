
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.MemberCount;

public final class MemberSummaries {

    public static final MemberCount valid() {
        return new MemberCount(2L, 1L);
    }

}
