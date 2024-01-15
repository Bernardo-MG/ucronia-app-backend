
package com.bernardomg.association.transaction.test.config.factory;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.transaction.model.Transaction;

public final class Transactions {

    public static final Transaction decimal() {
        return Transaction.builder()
            .index(TransactionConstants.INDEX)
            .amount(1.2F)
            .date(TransactionConstants.DATE)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final Transaction descriptionChange() {
        return Transaction.builder()
            .index(TransactionConstants.INDEX)
            .amount(1)
            .date(TransactionConstants.DATE)
            .description(TransactionConstants.DESCRIPTION + " 123")
            .build();
    }

    public static final Transaction february() {
        return Transaction.builder()
            .index(2)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .amount(1)
            .description("Transaction 2")
            .build();
    }

    public static final Transaction forAmount(final Float value) {
        return Transaction.builder()
            .index(TransactionConstants.INDEX)
            .amount(value)
            .date(TransactionConstants.DATE)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final Transaction forIndex(final long index, final Month month) {
        return Transaction.builder()
            .index(index)
            .amount(1)
            .date(LocalDate.of(2020, month, 1))
            .description(TransactionConstants.DESCRIPTION + " " + index)
            .build();
    }

    public static final Transaction forIndexAndDay(final long index, final Month month) {
        return Transaction.builder()
            .index(index)
            .amount(1)
            .date(LocalDate.of(2020, month, Long.valueOf(index)
                .intValue()))
            .description(TransactionConstants.DESCRIPTION + " " + index)
            .build();
    }

    public static final Transaction newlyCreated() {
        return Transaction.builder()
            .index(1)
            .amount(1)
            .date(TransactionConstants.DATE)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final Transaction valid() {
        return Transaction.builder()
            .index(TransactionConstants.INDEX)
            .amount(1)
            .date(TransactionConstants.DATE)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

}
