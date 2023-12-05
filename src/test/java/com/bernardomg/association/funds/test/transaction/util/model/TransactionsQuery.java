
package com.bernardomg.association.funds.test.transaction.util.model;

import java.time.LocalDate;

import com.bernardomg.association.funds.transaction.model.request.TransactionQuery;
import com.bernardomg.association.funds.transaction.model.request.TransactionQueryRequest;

public final class TransactionsQuery {

    public static final TransactionQuery date(final LocalDate date) {
        return TransactionQueryRequest.builder()
            .date(date)
            .build();
    }

    public static final TransactionQuery empty() {
        return TransactionQueryRequest.builder()
            .build();
    }

    public static final TransactionQuery endDate(final LocalDate date) {
        return TransactionQueryRequest.builder()
            .endDate(date)
            .build();
    }

    public static final TransactionQuery startDate(final LocalDate date) {
        return TransactionQueryRequest.builder()
            .startDate(date)
            .build();
    }

}
