
package com.bernardomg.association.transaction.test.factory;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionTypeEntity;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypeConstants;

public final class TransactionTypeEntities {

    public static final TransactionTypeEntity valid() {
        final TransactionTypeEntity entity;

        entity = new TransactionTypeEntity();
        entity.setNumber(TransactionTypeConstants.NUMBER);
        entity.setDescription(TransactionTypeConstants.DESCRIPTION);
        entity.setColor(TransactionTypeConstants.COLOR);

        return entity;
    }

    private TransactionTypeEntities() {
        super();
    }

}
