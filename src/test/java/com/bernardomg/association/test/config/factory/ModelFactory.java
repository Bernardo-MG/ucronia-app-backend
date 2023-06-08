
package com.bernardomg.association.test.config.factory;

import java.util.GregorianCalendar;

import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;

public final class ModelFactory {

    public static final PersistentTransaction transaction(final Float value) {
        return PersistentTransaction.builder()
            .amount(value)
            .date(new GregorianCalendar(2020, 1, 1))
            .description("Transaction")
            .build();
    }

}
