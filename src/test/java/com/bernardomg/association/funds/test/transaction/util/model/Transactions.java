
package com.bernardomg.association.funds.test.transaction.util.model;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.transaction.model.ImmutableTransaction;
import com.bernardomg.association.funds.transaction.model.Transaction;

public final class Transactions {

    public static final Transaction valid() {
        return ImmutableTransaction.builder()
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Transaction")
            .build();
    }

}
