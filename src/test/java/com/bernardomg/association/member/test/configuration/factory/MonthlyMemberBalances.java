
package com.bernardomg.association.member.test.configuration.factory;

import java.time.YearMonth;

import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;

public final class MonthlyMemberBalances {

    public static final MonthlyMemberBalance currentMonth() {
        return new MonthlyMemberBalance(MemberBalanceConstants.CURRENT_MONTH, 1L);
    }

    public static final MonthlyMemberBalance currentMonth(final Long total) {
        return new MonthlyMemberBalance(MemberBalanceConstants.CURRENT_MONTH, total);
    }

    public static final MonthlyMemberBalance forMonth(final YearMonth month) {
        return new MonthlyMemberBalance(month, 1L);
    }

    public static final MonthlyMemberBalance forMonthAndTotal(final YearMonth month, final Long total) {
        return new MonthlyMemberBalance(month, total);
    }

    public static final MonthlyMemberBalance nextMonth() {
        return new MonthlyMemberBalance(MemberBalanceConstants.NEXT_MONTH, 1L);
    }

    public static final MonthlyMemberBalance previousMonth() {
        return new MonthlyMemberBalance(MemberBalanceConstants.PREVIOUS_MONTH, 1L);
    }

    public static final MonthlyMemberBalance twoMonthsBack() {
        return new MonthlyMemberBalance(MemberBalanceConstants.TWO_MONTHS_BACK, 1L);
    }

    private MonthlyMemberBalances() {
        super();
    }

}
