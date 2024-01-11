
package com.bernardomg.association.membership.test.balance.config.factory;

import java.time.YearMonth;

import com.bernardomg.association.model.member.MonthlyMemberBalance;

public final class MonthlyMemberBalances {

    public static final MonthlyMemberBalance currentMonth() {
        return MonthlyMemberBalance.builder()
            .month(MemberBalanceConstants.CURRENT_MONTH)
            .total(1L)
            .build();
    }

    public static final MonthlyMemberBalance currentMonth(final Long total) {
        return MonthlyMemberBalance.builder()
            .month(MemberBalanceConstants.CURRENT_MONTH)
            .total(total)
            .build();
    }

    public static final MonthlyMemberBalance forMonthAndTotal(final YearMonth month, final Long total) {
        return MonthlyMemberBalance.builder()
            .month(month)
            .total(total)
            .build();
    }

    public static final MonthlyMemberBalance previousMonth() {
        return MonthlyMemberBalance.builder()
            .month(MemberBalanceConstants.PREVIOUS_MONTH)
            .total(1L)
            .build();
    }

    public static final MonthlyMemberBalance twoMonthsBack() {
        return MonthlyMemberBalance.builder()
            .month(MemberBalanceConstants.TWO_MONTHS_BACK)
            .total(1L)
            .build();
    }

    private MonthlyMemberBalances() {
        super();
    }

}
