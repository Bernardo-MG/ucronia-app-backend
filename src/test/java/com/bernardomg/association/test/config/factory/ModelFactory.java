
package com.bernardomg.association.test.config.factory;

import java.util.GregorianCalendar;

import com.bernardomg.association.transaction.model.PersistentTransaction;

public final class ModelFactory {

    public static final PersistentTransaction transaction(final Float value) {
        final PersistentTransaction entity;

        entity = new PersistentTransaction();
        entity.setAmount(value);
        entity.setDate(new GregorianCalendar(2020, 1, 1));
        entity.setDescription("Transaction");

        return entity;
    }

}
