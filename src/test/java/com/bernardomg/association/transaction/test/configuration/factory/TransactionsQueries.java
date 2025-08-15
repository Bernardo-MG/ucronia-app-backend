
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.Instant;

import com.bernardomg.association.transaction.domain.model.TransactionQuery;

public final class TransactionsQueries {

    public static final TransactionQuery date(final Instant date) {
        return new TransactionQuery(date, null, null);
    }

    public static final TransactionQuery empty() {
        return new TransactionQuery(null, null, null);
    }

    public static final TransactionQuery endDate(final Instant date) {
        return new TransactionQuery(null, null, date);
    }

    public static final TransactionQuery startDate(final Instant date) {
        return new TransactionQuery(null, date, null);
    }

}
