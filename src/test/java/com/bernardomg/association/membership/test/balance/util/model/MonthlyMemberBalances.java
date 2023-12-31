
package com.bernardomg.association.membership.test.balance.util.model;

import java.time.YearMonth;

import com.bernardomg.association.membership.balance.model.ImmutableMonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;

public final class MonthlyMemberBalances {

    public static final MonthlyMemberBalance currentMonth() {
        return ImmutableMonthlyMemberBalance.builder()
            .month(MemberBalanceConstants.CURRENT_MONTH)
            .total(1L)
            .build();
    }

    public static final MonthlyMemberBalance currentMonth(final Long total) {
        return ImmutableMonthlyMemberBalance.builder()
            .month(MemberBalanceConstants.CURRENT_MONTH)
            .total(total)
            .build();
    }

    public static final MonthlyMemberBalance forMonthAndTotal(final YearMonth month, final Long total) {
        return ImmutableMonthlyMemberBalance.builder()
            .month(month)
            .total(total)
            .build();
    }

    public static final MonthlyMemberBalance previousMonth() {
        return ImmutableMonthlyMemberBalance.builder()
            .month(MemberBalanceConstants.PREVIOUS_MONTH)
            .total(1L)
            .build();
    }

    public static final MonthlyMemberBalance twoMonthsBack() {
        return ImmutableMonthlyMemberBalance.builder()
            .month(MemberBalanceConstants.TWO_MONTHS_BACK)
            .total(1L)
            .build();
    }

    private MonthlyMemberBalances() {
        super();
    }

}
