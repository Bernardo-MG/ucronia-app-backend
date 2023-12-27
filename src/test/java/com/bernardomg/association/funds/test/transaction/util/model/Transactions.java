
package com.bernardomg.association.funds.test.transaction.util.model;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.transaction.model.Transaction;

public final class Transactions {

    public static final Transaction forAmount(final Float value) {
        return Transaction.builder()
            .index(1L)
            .amount(value)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

    public static final Transaction forIndex(final long index, final Month month) {
        return Transaction.builder()
            .index(index)
            .amount(1f)
            .date(LocalDate.of(2020, month, 1))
            .description("Transaction " + index)
            .build();
    }
    public static final Transaction forIndexAndDay(final long index, final Month month) {
        return Transaction.builder()
            .index(index)
            .amount(1f)
            .date(LocalDate.of(2020, month, Long.valueOf(index).intValue()))
            .description("Transaction " + index)
            .build();
    }

    public static final Transaction valid() {
        return Transaction.builder()
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

}
