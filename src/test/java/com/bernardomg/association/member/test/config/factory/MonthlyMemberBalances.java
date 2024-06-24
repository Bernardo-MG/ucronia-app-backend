
package com.bernardomg.association.member.test.config.factory;

import java.time.YearMonth;

import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;

public final class MonthlyMemberBalances {

    public static final MonthlyMemberBalance currentMonth() {
        return MonthlyMemberBalance.builder()
            .withDate(MemberBalanceConstants.CURRENT_MONTH)
            .withTotal(1L)
            .build();
    }

    public static final MonthlyMemberBalance currentMonth(final Long total) {
        return MonthlyMemberBalance.builder()
            .withDate(MemberBalanceConstants.CURRENT_MONTH)
            .withTotal(total)
            .build();
    }

    public static final MonthlyMemberBalance forMonth(final YearMonth month) {
        return MonthlyMemberBalance.builder()
            .withDate(month)
            .withTotal(1)
            .build();
    }

    public static final MonthlyMemberBalance forMonthAndTotal(final YearMonth month, final Long total) {
        return MonthlyMemberBalance.builder()
            .withDate(month)
            .withTotal(total)
            .build();
    }

    public static final MonthlyMemberBalance nextMonth() {
        return MonthlyMemberBalance.builder()
            .withDate(MemberBalanceConstants.NEXT_MONTH)
            .withTotal(1L)
            .build();
    }

    public static final MonthlyMemberBalance previousMonth() {
        return MonthlyMemberBalance.builder()
            .withDate(MemberBalanceConstants.PREVIOUS_MONTH)
            .withTotal(1L)
            .build();
    }

    public static final MonthlyMemberBalance twoMonthsBack() {
        return MonthlyMemberBalance.builder()
            .withDate(MemberBalanceConstants.TWO_MONTHS_BACK)
            .withTotal(1L)
            .build();
    }

    private MonthlyMemberBalances() {
        super();
    }

}
