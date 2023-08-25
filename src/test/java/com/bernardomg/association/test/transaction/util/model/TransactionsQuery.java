
package com.bernardomg.association.test.transaction.util.model;

import java.time.LocalDateTime;

import com.bernardomg.association.transaction.model.request.TransactionQuery;
import com.bernardomg.association.transaction.model.request.ValidatedTransactionQuery;

public final class TransactionsQuery {

    public static final TransactionQuery date(final LocalDateTime date) {
        return ValidatedTransactionQuery.builder()
            .date(date)
            .build();
    }

    public static final TransactionQuery empty() {
        return ValidatedTransactionQuery.builder()
            .build();
    }

    public static final TransactionQuery endDate(final LocalDateTime date) {
        return ValidatedTransactionQuery.builder()
            .endDate(date)
            .build();
    }

    public static final TransactionQuery startDate(final LocalDateTime date) {
        return ValidatedTransactionQuery.builder()
            .startDate(date)
            .build();
    }

}
