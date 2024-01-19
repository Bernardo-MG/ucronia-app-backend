
package com.bernardomg.association.transaction.test.config.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;

public final class TransactionBalanceQueries {

    public static final TransactionBalanceQuery empty() {
        return TransactionBalanceQuery.builder()
            .build();
    }

    public static final TransactionBalanceQuery endDate(final int year, final Month month) {
        return TransactionBalanceQuery.builder()
            .endDate(YearMonth.of(year, month))
            .build();
    }

    public static final TransactionBalanceQuery range(final int year, final Month start, final Month end) {
        return TransactionBalanceQuery.builder()
            .startDate(YearMonth.of(year, start))
            .endDate(YearMonth.of(year, end))
            .build();
    }

    public static final TransactionBalanceQuery startDate(final int year, final Month month) {
        return TransactionBalanceQuery.builder()
            .startDate(YearMonth.of(year, month))
            .build();
    }

    public TransactionBalanceQueries() {
        super();
    }

}
