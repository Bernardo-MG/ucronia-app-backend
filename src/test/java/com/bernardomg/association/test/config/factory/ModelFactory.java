
package com.bernardomg.association.test.config.factory;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;

public final class ModelFactory {

    public static final PersistentTransaction transaction(final Float value) {
        return PersistentTransaction.builder()
            .amount(value)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

}
