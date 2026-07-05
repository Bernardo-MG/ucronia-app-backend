
package com.bernardomg.association.transaction.test.configuration.factory;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTransactionEntity;
import com.bernardomg.association.fee.test.configuration.factory.TransactionConstants;

public final class TransactionEntities {

    public static final FeeTransactionEntity decimal() {
        final FeeTransactionEntity entity = new FeeTransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT_DECIMAL);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }

    public static final FeeTransactionEntity forAmount(final Float value) {
        final FeeTransactionEntity entity = new FeeTransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(value);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }

    public static final FeeTransactionEntity valid() {
        final FeeTransactionEntity entity = new FeeTransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }
}
