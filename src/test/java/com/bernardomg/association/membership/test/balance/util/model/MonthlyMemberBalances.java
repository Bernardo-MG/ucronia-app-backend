
package com.bernardomg.association.membership.test.balance.util.model;

import java.time.YearMonth;

import com.bernardomg.association.membership.balance.model.ImmutableMonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;

public final class MonthlyMemberBalances {

    public static final MonthlyMemberBalance forMonth(final YearMonth month) {
        return ImmutableMonthlyMemberBalance.builder()
            .month(month)
            .total(1L)
            .build();
    }

    public static final MonthlyMemberBalance forMonthAndTotal(final YearMonth month, final Long total) {
        return ImmutableMonthlyMemberBalance.builder()
            .month(month)
            .total(total)
            .build();
    }

    private MonthlyMemberBalances() {
        super();
    }

}
