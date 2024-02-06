
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.domain.model.MemberBalanceQuery;

public final class MemberBalanceQueryRequests {

    public static final MemberBalanceQuery aroundCurrent() {
        return MemberBalanceQuery.builder()
            .withStartDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .withEndDate(MemberBalanceConstants.NEXT_MONTH)
            .build();
    }

    public static final MemberBalanceQuery aroundPrevious() {
        return MemberBalanceQuery.builder()
            .withStartDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .withEndDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .build();
    }

    public static final MemberBalanceQuery empty() {
        return MemberBalanceQuery.builder()
            .build();
    }

    public static final MemberBalanceQuery endBeforeStart() {
        return MemberBalanceQuery.builder()
            .withStartDate(MemberBalanceConstants.CURRENT_MONTH)
            .withEndDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .build();
    }

    public static final MemberBalanceQuery previousAndThis() {
        return MemberBalanceQuery.builder()
            .withStartDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .withEndDate(MemberBalanceConstants.CURRENT_MONTH)
            .build();
    }

    private MemberBalanceQueryRequests() {
        super();
    }

}
