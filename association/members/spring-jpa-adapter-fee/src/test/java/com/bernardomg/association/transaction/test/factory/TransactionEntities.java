
package com.bernardomg.association.transaction.test.factory;

import java.time.Instant;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;

public final class TransactionEntities {

    public static final TransactionEntity februaryFee() {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT_BIGGER);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION_FEE_FEBRUARY);
        return entity;
    }

    public static final TransactionEntity forAmount(final Float value, final Instant date) {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(value);
        entity.setDate(date);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }

    public static final TransactionEntity forAmount(final Float value, final Instant date, final Long index) {
        final TransactionEntity entity = new TransactionEntity();
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
