
package com.bernardomg.association.transaction.test.configuration.factory;

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
            .withEndDate(YearMonth.of(year, month))
            .build();
    }

    public static final TransactionBalanceQuery range(final int year, final Month start, final Month end) {
        return TransactionBalanceQuery.builder()
            .withStartDate(YearMonth.of(year, start))
            .withEndDate(YearMonth.of(year, end))
            .build();
    }

    public static final TransactionBalanceQuery startDate(final int year, final Month month) {
        return TransactionBalanceQuery.builder()
            .withStartDate(YearMonth.of(year, month))
            .build();
    }

    public TransactionBalanceQueries() {
        super();
    }

}
