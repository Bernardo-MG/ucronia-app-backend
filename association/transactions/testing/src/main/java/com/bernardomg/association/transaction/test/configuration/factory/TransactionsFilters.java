
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.Instant;
import java.util.Optional;

import com.bernardomg.association.transaction.domain.filter.TransactionFilter;

public final class TransactionsFilters {

    public static final TransactionFilter date(final Instant date) {
        return new TransactionFilter(Optional.of(date), Optional.empty(), Optional.empty());
    }

    public static final TransactionFilter empty() {
        return new TransactionFilter(Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static final TransactionFilter from(final Instant date) {
        return new TransactionFilter(Optional.empty(), Optional.of(date), Optional.empty());
    }

    public static final TransactionFilter to(final Instant date) {
        return new TransactionFilter(Optional.empty(), Optional.empty(), Optional.of(date));
    }

}
