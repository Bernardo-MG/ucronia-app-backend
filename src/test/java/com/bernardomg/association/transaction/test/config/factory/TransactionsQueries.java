
package com.bernardomg.association.transaction.test.config.factory;

import java.time.LocalDate;

import com.bernardomg.association.transaction.domain.model.TransactionQuery;

public final class TransactionsQueries {

    public static final TransactionQuery date(final LocalDate date) {
        return TransactionQuery.builder()
            .withDate(date)
            .build();
    }

    public static final TransactionQuery empty() {
        return TransactionQuery.builder()
            .build();
    }

    public static final TransactionQuery endDate(final LocalDate date) {
        return TransactionQuery.builder()
            .withEndDate(date)
            .build();
    }

    public static final TransactionQuery startDate(final LocalDate date) {
        return TransactionQuery.builder()
            .withStartDate(date)
            .build();
    }

}
