
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.Instant;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;

public final class TransactionEntities {

    public static final TransactionEntity decimal() {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT_DECIMAL);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }

    public static final TransactionEntity descriptionChange() {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription("Transaction 123");
        return entity;
    }

    public static final TransactionEntity februaryFee() {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT_BIGGER);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION_FEE_FEBRUARY);
        return entity;
    }

    public static final TransactionEntity forAmount(final Float value) {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(value);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION);
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

    public static final TransactionEntity index(final long index) {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(index);
        entity.setAmount(TransactionConstants.AMOUNT);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }

    public static final TransactionEntity multipleFeesEndYear() {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT_FEES);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION_FEE_DECEMBER_JANUARY);
        return entity;
    }

    public static final TransactionEntity multipleFeesSpanYears() {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT_FEES);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription("Cuota de Profile 1 Last name 1 para Diciembre 2020, Enero 2021");
        return entity;
    }

    public static final TransactionEntity multipleFeesStartYear() {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT_FEES);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION_FEE_FEBRUARY_MARCH);
        return entity;
    }

    public static final TransactionEntity singleFeeNoAmount() {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(0F);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION_FEE_FEBRUARY);
        return entity;
    }

    public static final TransactionEntity valid() {
        final TransactionEntity entity = new TransactionEntity();
        entity.setIndex(TransactionConstants.INDEX);
        entity.setAmount(TransactionConstants.AMOUNT);
        entity.setDate(TransactionConstants.DATE);
        entity.setDescription(TransactionConstants.DESCRIPTION);
        return entity;
    }
}
