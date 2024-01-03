
package com.bernardomg.association.funds.test.balance.config.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.funds.balance.model.BalanceQuery;

public final class BalanceQueries {

    public static final BalanceQuery empty() {
        return BalanceQuery.builder()
            .build();
    }

    public static final BalanceQuery endDate(final int year, final Month month) {
        return BalanceQuery.builder()
            .endDate(YearMonth.of(year, month))
            .build();
    }

    public static final BalanceQuery range(final int year, final Month start, final Month end) {
        return BalanceQuery.builder()
            .startDate(YearMonth.of(year, start))
            .endDate(YearMonth.of(year, end))
            .build();
    }

    public static final BalanceQuery startDate(final int year, final Month month) {
        return BalanceQuery.builder()
            .startDate(YearMonth.of(year, month))
            .build();
    }

    public BalanceQueries() {
        super();
    }

}
