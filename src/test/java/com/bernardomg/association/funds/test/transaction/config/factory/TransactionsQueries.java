
package com.bernardomg.association.funds.test.transaction.config.factory;

import java.time.LocalDate;

import com.bernardomg.association.transaction.model.TransactionQuery;

public final class TransactionsQueries {

    public static final TransactionQuery date(final LocalDate date) {
        return TransactionQuery.builder()
            .date(date)
            .build();
    }

    public static final TransactionQuery empty() {
        return TransactionQuery.builder()
            .build();
    }

    public static final TransactionQuery endDate(final LocalDate date) {
        return TransactionQuery.builder()
            .endDate(date)
            .build();
    }

    public static final TransactionQuery startDate(final LocalDate date) {
        return TransactionQuery.builder()
            .startDate(date)
            .build();
    }

}
