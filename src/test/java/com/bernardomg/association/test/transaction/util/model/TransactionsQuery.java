
package com.bernardomg.association.test.transaction.util.model;

import java.util.Calendar;

import com.bernardomg.association.transaction.model.request.TransactionQuery;
import com.bernardomg.association.transaction.model.request.ValidatedTransactionQuery;

public final class TransactionsQuery {

    public static final TransactionQuery date(final Calendar date) {
        return ValidatedTransactionQuery.builder()
            .date(date)
            .build();
    }

    public static final TransactionQuery empty() {
        return ValidatedTransactionQuery.builder()
            .build();
    }

    public static final TransactionQuery endDate(final Calendar date) {
        return ValidatedTransactionQuery.builder()
            .endDate(date)
            .build();
    }

    public static final TransactionQuery startDate(final Calendar date) {
        return ValidatedTransactionQuery.builder()
            .startDate(date)
            .build();
    }

}
