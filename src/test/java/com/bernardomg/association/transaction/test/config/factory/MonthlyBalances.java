
package com.bernardomg.association.transaction.test.config.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.transaction.model.MonthlyBalance;

public final class MonthlyBalances {

    public static final YearMonth CURRENT = YearMonth.now();

    public static final YearMonth MONTH   = YearMonth.of(2020, Month.JANUARY);

    public static final MonthlyBalance currentMonth(final float amount) {
        return MonthlyBalance.builder()
            .month(CURRENT)
            .results(amount)
            .total(amount)
            .build();
    }

    public static final MonthlyBalance forAmount(final float amount) {
        return MonthlyBalance.builder()
            .month(MONTH)
            .results(amount)
            .total(amount)
            .build();
    }

    public static final MonthlyBalance forAmount(final Month month, final float amount) {
        return MonthlyBalance.builder()
            .month(YearMonth.of(2020, month))
            .results(amount)
            .total(amount)
            .build();
    }

    public static final MonthlyBalance forAmount(final Month month, final float amount, final float total) {
        return MonthlyBalance.builder()
            .month(YearMonth.of(2020, month))
            .results(amount)
            .total(total)
            .build();
    }

    public static final MonthlyBalance forAmount(final YearMonth month, final float amount) {
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
