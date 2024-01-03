
package com.bernardomg.association.membership.test.balance.config.factory;

import com.bernardomg.association.membership.balance.model.MemberBalanceQuery;

public final class MemberBalanceQueryRequests {

    public static final MemberBalanceQuery aroundCurrent() {
        return MemberBalanceQuery.builder()
            .startDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .endDate(MemberBalanceConstants.NEXT_MONTH)
            .build();
    }

    public static final MemberBalanceQuery aroundPrevious() {
        return MemberBalanceQuery.builder()
            .startDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .endDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .build();
    }

    public static final MemberBalanceQuery empty() {
        return MemberBalanceQuery.builder()
            .build();
    }

    public static final MemberBalanceQuery endBeforeStart() {
        return MemberBalanceQuery.builder()
            .startDate(MemberBalanceConstants.CURRENT_MONTH)
            .endDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .build();
    }

    public static final MemberBalanceQuery previousAndThis() {
        return MemberBalanceQuery.builder()
            .startDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .endDate(MemberBalanceConstants.CURRENT_MONTH)
            .build();
    }

    private MemberBalanceQueryRequests() {
        super();
    }

}
