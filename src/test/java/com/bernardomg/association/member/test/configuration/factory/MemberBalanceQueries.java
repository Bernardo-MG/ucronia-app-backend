
package com.bernardomg.association.member.test.configuration.factory;

import java.time.ZoneOffset;

import com.bernardomg.association.member.domain.filter.MemberBalanceQuery;

public final class MemberBalanceQueries {

    public static final MemberBalanceQuery aroundCurrent() {
        return new MemberBalanceQuery(MemberBalanceConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MemberBalanceConstants.NEXT_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final MemberBalanceQuery aroundPrevious() {
        return new MemberBalanceQuery(MemberBalanceConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MemberBalanceConstants.PREVIOUS_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final MemberBalanceQuery empty() {
        return new MemberBalanceQuery(null, null);
    }

    public static final MemberBalanceQuery endBeforeStart() {
        return new MemberBalanceQuery(MemberBalanceConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MemberBalanceConstants.PREVIOUS_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final MemberBalanceQuery previousAndThis() {
        return new MemberBalanceQuery(MemberBalanceConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MemberBalanceConstants.CURRENT_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    private MemberBalanceQueries() {
        super();
    }

}
