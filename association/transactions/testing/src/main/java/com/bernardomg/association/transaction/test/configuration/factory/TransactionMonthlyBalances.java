
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.Instant;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneOffset;

import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;

public final class TransactionMonthlyBalances {

    public static final TransactionMonthlyBalance currentMonth(final float amount) {
        return new TransactionMonthlyBalance(TransactionConstants.CURRENT, amount, amount);
    }

    public static final TransactionMonthlyBalance forAmount(final float amount) {
        return new TransactionMonthlyBalance(TransactionConstants.MONTH, amount, amount);
    }

    public static final TransactionMonthlyBalance forAmount(final Instant month, final float amount) {
        return new TransactionMonthlyBalance(month, amount, amount);
    }

    public static final TransactionMonthlyBalance forAmount(final Month month, final float amount, final float total) {
        return new TransactionMonthlyBalance(YearMonth.of(2020, month)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), amount, total);
    }

    private TransactionMonthlyBalances() {
        super();
    }

}
