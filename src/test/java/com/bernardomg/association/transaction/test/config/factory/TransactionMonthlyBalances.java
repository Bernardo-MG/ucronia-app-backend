
package com.bernardomg.association.transaction.test.config.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;

public final class TransactionMonthlyBalances {

    public static final YearMonth CURRENT = YearMonth.now();

    public static final YearMonth MONTH   = YearMonth.of(2020, Month.JANUARY);

    public static final TransactionMonthlyBalance currentMonth(final float amount) {
        return TransactionMonthlyBalance.builder()
            .withDate(CURRENT)
            .withResults(amount)
            .withTotal(amount)
            .build();
    }

    public static final TransactionMonthlyBalance forAmount(final float amount) {
        return TransactionMonthlyBalance.builder()
            .withDate(MONTH)
            .withResults(amount)
            .withTotal(amount)
            .build();
    }

    public static final TransactionMonthlyBalance forAmount(final Month month, final float amount) {
        return TransactionMonthlyBalance.builder()
            .withDate(YearMonth.of(2020, month))
            .withResults(amount)
            .withTotal(amount)
            .build();
    }

    public static final TransactionMonthlyBalance forAmount(final Month month, final float amount, final float total) {
        return TransactionMonthlyBalance.builder()
            .withDate(YearMonth.of(2020, month))
            .withResults(amount)
            .withTotal(total)
            .build();
    }

    public static final TransactionMonthlyBalance forAmount(final YearMonth month, final float amount) {
        return TransactionMonthlyBalance.builder()
            .withDate(month)
            .withResults(amount)
            .withTotal(amount)
            .build();
    }

    private TransactionMonthlyBalances() {
        super();
    }

}
