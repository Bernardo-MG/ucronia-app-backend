
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.transaction.domain.model.Transaction;

public final class Transactions {

    public static final Transaction amount(final float amount) {
        return new Transaction(TransactionConstants.INDEX, TransactionConstants.DATE, amount,
            TransactionConstants.DESCRIPTION);
    }

    public static final Transaction decimal() {
        return new Transaction(TransactionConstants.INDEX, TransactionConstants.DATE,
            TransactionConstants.AMOUNT_DECIMAL, TransactionConstants.DESCRIPTION);
    }

    public static final Transaction descriptionChange() {
        return new Transaction(TransactionConstants.INDEX, TransactionConstants.DATE, TransactionConstants.AMOUNT,
            TransactionConstants.DESCRIPTION + " 123");
    }

    public static final Transaction february() {
        return new Transaction(2, LocalDate.of(2020, Month.FEBRUARY, 1), TransactionConstants.AMOUNT,
            "Transaction 2");
    }

    public static final Transaction forAmount(final Float amount) {
        return new Transaction(TransactionConstants.INDEX, TransactionConstants.DATE, amount,
            TransactionConstants.DESCRIPTION);
    }

    public static final Transaction forDate(final LocalDate date) {
        return new Transaction(TransactionConstants.INDEX, date, TransactionConstants.AMOUNT,
            TransactionConstants.DESCRIPTION);
    }

    public static final Transaction forIndex(final long index, final Month month) {
        return new Transaction(index, LocalDate.of(2020, month, 1), TransactionConstants.AMOUNT,
            TransactionConstants.DESCRIPTION + " " + index);
    }

    public static final Transaction forIndexAndMonth(final long index, final Month month) {
        return new Transaction(index, LocalDate.of(2020, month, Long.valueOf(index)
            .intValue()), TransactionConstants.AMOUNT, TransactionConstants.DESCRIPTION + " " + index);
    }

    public static final Transaction newlyCreated() {
        return new Transaction(1, TransactionConstants.DATE, TransactionConstants.AMOUNT,
            TransactionConstants.DESCRIPTION);
    }

    public static final Transaction padded() {
        return new Transaction(TransactionConstants.INDEX, TransactionConstants.DATE, TransactionConstants.AMOUNT,
            " Transaction ");
    }

    public static final Transaction positive() {
        return new Transaction(TransactionConstants.INDEX, TransactionConstants.DATE, TransactionConstants.AMOUNT,
            TransactionConstants.DESCRIPTION);
    }

}
