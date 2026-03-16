
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;

public final class TransactionMonthlyBalances {

    public static final YearMonth CURRENT = YearMonth.now();

    public static final YearMonth MONTH   = YearMonth.of(2020, Month.JANUARY);

    public static final TransactionMonthlyBalance currentMonth(final float amount) {
        return new TransactionMonthlyBalance(CURRENT, amount, amount);
    }

    public static final TransactionMonthlyBalance forAmount(final float amount) {
        return new TransactionMonthlyBalance(MONTH, amount, amount);
    }

    public static final TransactionMonthlyBalance forAmount(final Month month, final float amount) {
        return new TransactionMonthlyBalance(YearMonth.of(2020, month), amount, amount);
    }

    public static final TransactionMonthlyBalance forAmount(final Month month, final float amount, final float total) {
        return new TransactionMonthlyBalance(YearMonth.of(2020, month), amount, total);
    }

    public static final TransactionMonthlyBalance forAmount(final YearMonth month, final float amount) {
        return new TransactionMonthlyBalance(month, amount, amount);
    }

    private TransactionMonthlyBalances() {
        super();
    }

}
