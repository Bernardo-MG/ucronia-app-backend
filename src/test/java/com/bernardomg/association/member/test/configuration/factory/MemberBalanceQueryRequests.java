
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.MemberBalanceQuery;

public final class MemberBalanceQueryRequests {

    public static final MemberBalanceQuery aroundCurrent() {
        return new MemberBalanceQuery(MemberBalanceConstants.PREVIOUS_MONTH, MemberBalanceConstants.NEXT_MONTH);
    }

    public static final MemberBalanceQuery aroundPrevious() {
        return new MemberBalanceQuery(MemberBalanceConstants.PREVIOUS_MONTH, MemberBalanceConstants.PREVIOUS_MONTH);
    }

    public static final MemberBalanceQuery empty() {
        return new MemberBalanceQuery(null, null);
    }

    public static final MemberBalanceQuery endBeforeStart() {
        return new MemberBalanceQuery(MemberBalanceConstants.CURRENT_MONTH, MemberBalanceConstants.PREVIOUS_MONTH);
    }

    public static final MemberBalanceQuery previousAndThis() {
        return new MemberBalanceQuery(MemberBalanceConstants.PREVIOUS_MONTH, MemberBalanceConstants.CURRENT_MONTH);
    }

    private MemberBalanceQueryRequests() {
        super();
    }

}
