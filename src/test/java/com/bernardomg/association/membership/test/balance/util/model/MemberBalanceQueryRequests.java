
package com.bernardomg.association.membership.test.balance.util.model;

import com.bernardomg.association.membership.balance.model.request.MemberBalanceQueryRequest;

public final class MemberBalanceQueryRequests {

    public static final MemberBalanceQueryRequest aroundCurrent() {
        return MemberBalanceQueryRequest.builder()
            .startDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .endDate(MemberBalanceConstants.NEXT_MONTH)
            .build();
    }

    public static final MemberBalanceQueryRequest aroundPrevious() {
        return MemberBalanceQueryRequest.builder()
            .startDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .endDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .build();
    }

    public static final MemberBalanceQueryRequest empty() {
        return MemberBalanceQueryRequest.builder()
            .build();
    }

    public static final MemberBalanceQueryRequest endBeforeStart() {
        return MemberBalanceQueryRequest.builder()
            .startDate(MemberBalanceConstants.CURRENT_MONTH)
            .endDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .build();
    }

    public static final MemberBalanceQueryRequest previousAndThis() {
        return MemberBalanceQueryRequest.builder()
            .startDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .endDate(MemberBalanceConstants.CURRENT_MONTH)
            .build();
    }

    private MemberBalanceQueryRequests() {
        super();
    }

}
