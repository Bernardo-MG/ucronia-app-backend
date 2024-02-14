
package com.bernardomg.association.transaction.test.config.factory;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.transaction.domain.model.Transaction;

public final class Transactions {

    public static final Transaction amount(final float amount) {
        return Transaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(amount)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final Transaction decimal() {
        return Transaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(1.2F)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final Transaction descriptionChange() {
        return Transaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(1)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION + " 123")
            .build();
    }

    public static final Transaction february() {
        return Transaction.builder()
            .withIndex(2)
            .withDate(LocalDate.of(2020, Month.FEBRUARY, 1))
            .withAmount(1)
            .withDescription("Transaction 2")
            .build();
    }

    public static final Transaction forAmount(final Float value) {
        return Transaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(value)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final Transaction forIndex(final long index, final Month month) {
        return Transaction.builder()
            .withIndex(index)
            .withAmount(1)
            .withDate(LocalDate.of(2020, month, 1))
            .withDescription(TransactionConstants.DESCRIPTION + " " + index)
            .build();
    }

    public static final Transaction forIndexAndDay(final long index, final Month month) {
        return Transaction.builder()
            .withIndex(index)
            .withAmount(1)
            .withDate(LocalDate.of(2020, month, Long.valueOf(index)
                .intValue()))
            .withDescription(TransactionConstants.DESCRIPTION + " " + index)
            .build();
    }

    public static final Transaction newlyCreated() {
        return Transaction.builder()
            .withIndex(1)
            .withAmount(1)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final Transaction paddedWithWhitespaces() {
        return Transaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDescription(" Transaction ")
            .withAmount(1F)
            .withDate(TransactionConstants.DATE)
            .build();
    }

    public static final Transaction valid() {
        return Transaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(1)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

}
