
package com.bernardomg.association.funds.test.transaction.util.model;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;

public final class PersistentTransactions {

    public static final PersistentTransaction transaction(final Float value) {
        return PersistentTransaction.builder()
            .amount(value)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

}