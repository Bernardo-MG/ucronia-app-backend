
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

import com.bernardomg.association.transaction.domain.model.FeeTransaction;

public final class Transactions {

    public static final FeeTransaction amount(final float amount) {
        return new FeeTransaction(TransactionConstants.INDEX, TransactionConstants.DATE, amount,
            TransactionConstants.DESCRIPTION);
    }

    public static final FeeTransaction decimal() {
        return new FeeTransaction(TransactionConstants.INDEX, TransactionConstants.DATE,
            TransactionConstants.AMOUNT_DECIMAL, TransactionConstants.DESCRIPTION);
    }

    public static final FeeTransaction forAmount(final Float amount) {
        return new FeeTransaction(TransactionConstants.INDEX, TransactionConstants.DATE, amount,
            TransactionConstants.DESCRIPTION);
    }

    public static final FeeTransaction forDate(final Instant date) {
        return new FeeTransaction(TransactionConstants.INDEX, date, TransactionConstants.AMOUNT,
            TransactionConstants.DESCRIPTION);
    }

    public static final FeeTransaction forIndex(final long index, final Month month) {
        return new FeeTransaction(index, LocalDate.of(2020, month, 1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), TransactionConstants.AMOUNT, TransactionConstants.DESCRIPTION + " " + index);
    }

    public static final FeeTransaction forIndexAndMonth(final long index, final Month month) {
        // TODO: looks too similar to the previous one
        return new FeeTransaction(index, LocalDate.of(2020, month, Long.valueOf(index)
            .intValue())
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), TransactionConstants.AMOUNT, TransactionConstants.DESCRIPTION + " " + index);
    }

    public static final FeeTransaction future() {
        return new FeeTransaction(TransactionConstants.INDEX, TransactionConstants.DATE_FUTURE,
            TransactionConstants.AMOUNT, TransactionConstants.DESCRIPTION);
    }

    public static final FeeTransaction padded() {
        return new FeeTransaction(TransactionConstants.INDEX, TransactionConstants.DATE, TransactionConstants.AMOUNT,
            " Transaction ");
    }

    public static final FeeTransaction positive() {
        return new FeeTransaction(TransactionConstants.INDEX, TransactionConstants.DATE, TransactionConstants.AMOUNT,
            TransactionConstants.DESCRIPTION);
    }

    public static final FeeTransaction toCreate() {
        return new FeeTransaction(0, TransactionConstants.DATE, TransactionConstants.AMOUNT,
            TransactionConstants.DESCRIPTION);
    }

}
