
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneOffset;

import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;

public final class TransactionBalanceQueries {

    public static final TransactionBalanceQuery empty() {
        return new TransactionBalanceQuery(null, null);
    }

    public static final TransactionBalanceQuery from(final int year, final Month month) {
        return new TransactionBalanceQuery(YearMonth.of(year, month)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), null);
    }

    public static final TransactionBalanceQuery range(final int year, final Month start, final Month end) {
        return new TransactionBalanceQuery(YearMonth.of(year, start)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            YearMonth.of(year, end)
                .atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final TransactionBalanceQuery to(final int year, final Month month) {
        return new TransactionBalanceQuery(null, YearMonth.of(year, month)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
    }

    public TransactionBalanceQueries() {
        super();
    }

}
