
package com.bernardomg.association.transaction.test.factory;

import java.time.Instant;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTransactionEntity;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;

public final class TransactionEntities {

    public static final FeeTransactionEntity februaryFee() {
        final FeeTransactionEntity entity = new FeeTransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT_BIGGER);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION_FEE_FEBRUARY);
        return entity;
    }

    public static final FeeTransactionEntity forAmount(final Float value, final Instant date) {
        final FeeTransactionEntity entity = new FeeTransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(value);
        entity.setDate(date);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }

    public static final FeeTransactionEntity forAmount(final Float value, final Instant date, final Long index) {
        final FeeTransactionEntity entity = new FeeTransactionEntity();
        entity.setIndex(index);
        entity.setAmount(value);
        entity.setDate(date);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }

    private TransactionEntities() {
        super();
    }

}
