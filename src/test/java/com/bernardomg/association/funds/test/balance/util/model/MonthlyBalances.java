
package com.bernardomg.association.funds.test.balance.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.funds.balance.model.MonthlyBalance;

public final class MonthlyBalances {

    public static final YearMonth CURRENT = YearMonth.now();

    public static final YearMonth MONTH   = YearMonth.of(2020, Month.JANUARY);

    public static final MonthlyBalance currentMonth(final Float amount) {
        return MonthlyBalance.builder()
            .month(CURRENT)
            .results(amount)
            .total(amount)
            .build();
    }

    public static final MonthlyBalance forAmount(final Float amount) {
        return MonthlyBalance.builder()
            .month(MONTH)
            .results(amount)
            .total(amount)
            .build();
    }

    public static final MonthlyBalance forAmount(final YearMonth month, final Float amount) {
        return MonthlyBalance.builder()
            .month(month)
            .results(amount)
            .total(amount)
            .build();
    }

    private MonthlyBalances() {
        super();
    }

}
