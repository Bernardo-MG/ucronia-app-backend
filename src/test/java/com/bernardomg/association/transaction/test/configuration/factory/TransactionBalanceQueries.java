
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;

public final class TransactionBalanceQueries {

    public static final TransactionBalanceQuery empty() {
        return new TransactionBalanceQuery(null, null);
    }

    public static final TransactionBalanceQuery endDate(final int year, final Month month) {
        return new TransactionBalanceQuery(null, YearMonth.of(year, month));
    }

    public static final TransactionBalanceQuery range(final int year, final Month start, final Month end) {
        return new TransactionBalanceQuery(YearMonth.of(year, start), YearMonth.of(year, end));
    }

    public static final TransactionBalanceQuery startDate(final int year, final Month month) {
        return new TransactionBalanceQuery(YearMonth.of(year, month), null);
    }

    public TransactionBalanceQueries() {
        super();
    }

}
