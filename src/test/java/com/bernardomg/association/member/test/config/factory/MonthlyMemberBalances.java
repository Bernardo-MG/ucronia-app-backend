
package com.bernardomg.association.member.test.config.factory;

import java.time.YearMonth;

import com.bernardomg.association.member.model.MonthlyMemberBalance;

public final class MonthlyMemberBalances {

    public static final MonthlyMemberBalance currentMonth() {
        return MonthlyMemberBalance.builder()
            .date(MemberBalanceConstants.CURRENT_MONTH)
            .total(1L)
            .build();
    }

    public static final MonthlyMemberBalance currentMonth(final Long total) {
        return MonthlyMemberBalance.builder()
            .date(MemberBalanceConstants.CURRENT_MONTH)
            .total(total)
            .build();
    }

    public static final MonthlyMemberBalance forMonthAndTotal(final YearMonth month, final Long total) {
        return MonthlyMemberBalance.builder()
            .date(month)
            .total(total)
            .build();
    }

    public static final MonthlyMemberBalance previousMonth() {
        return MonthlyMemberBalance.builder()
            .date(MemberBalanceConstants.PREVIOUS_MONTH)
            .total(1L)
            .build();
    }

    public static final MonthlyMemberBalance twoMonthsBack() {
        return MonthlyMemberBalance.builder()
            .date(MemberBalanceConstants.TWO_MONTHS_BACK)
            .total(1L)
            .build();
    }

    private MonthlyMemberBalances() {
        super();
    }

}
