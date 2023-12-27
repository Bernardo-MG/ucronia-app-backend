
package com.bernardomg.association.funds.test.transaction.util.model;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.transaction.model.Transaction;

public final class Transactions {

    public static final Transaction forAmount(final Float value) {
        return Transaction.builder()
            .amount(value)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
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
