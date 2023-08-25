
package com.bernardomg.association.test.config.factory;

import java.time.LocalDateTime;
import java.time.Month;

import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;

public final class ModelFactory {

    public static final PersistentTransaction transaction(final Float value) {
        return PersistentTransaction.builder()
            .amount(value)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .description("Transaction")
            .build();
    }

}
