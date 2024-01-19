
package com.bernardomg.association.transaction.test.config.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;

public final class TransactionMonthlyBalances {

    public static final YearMonth CURRENT = YearMonth.now();

    public static final YearMonth MONTH   = YearMonth.of(2020, Month.JANUARY);

    public static final TransactionMonthlyBalance currentMonth(final float amount) {
        return TransactionMonthlyBalance.builder()
            .date(CURRENT)
            .results(amount)
            .total(amount)
            .build();
    }

    public static final TransactionMonthlyBalance forAmount(final float amount) {
        return TransactionMonthlyBalance.builder()
            .date(MONTH)
            .results(amount)
            .total(amount)
            .build();
    }

    public static final TransactionMonthlyBalance forAmount(final Month month, final float amount) {
        return TransactionMonthlyBalance.builder()
            .date(YearMonth.of(2020, month))
            .results(amount)
            .total(amount)
            .build();
    }

    public static final TransactionMonthlyBalance forAmount(final Month month, final float amount, final float total) {
        return TransactionMonthlyBalance.builder()
            .date(YearMonth.of(2020, month))
            .results(amount)
            .total(total)
            .build();
    }

    public static final TransactionMonthlyBalance forAmount(final YearMonth month, final float amount) {
        return TransactionMonthlyBalance.builder()
            .date(month)
            .results(amount)
            .total(amount)
            .build();
    }

    private TransactionMonthlyBalances() {
        super();
    }

}
