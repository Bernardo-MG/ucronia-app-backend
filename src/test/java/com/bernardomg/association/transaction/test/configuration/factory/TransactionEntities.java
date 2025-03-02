
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.LocalDate;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;

public final class TransactionEntities {

    public static final TransactionEntity decimal() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(TransactionConstants.AMOUNT_DECIMAL)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity descriptionChange() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(TransactionConstants.AMOUNT)
            .withDate(TransactionConstants.DATE)
            .withDescription("Transaction 123")
            .build();
    }

    public static final TransactionEntity februaryFee() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(TransactionConstants.AMOUNT_BIGGER)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION_FEE_FEBRUARY)
            .build();
    }

    public static final TransactionEntity forAmount(final Float value) {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(value)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity forAmount(final Float value, final LocalDate date) {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(value)
            .withDate(date)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity forAmount(final Float value, final LocalDate date, final Long index) {
        return TransactionEntity.builder()
            .withIndex(index)
            .withAmount(value)
            .withDate(date)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity index(final long index) {
        return TransactionEntity.builder()
            .withIndex(index)
            .withAmount(TransactionConstants.AMOUNT)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity multipleFeesEndYear() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(TransactionConstants.AMOUNT_FEES)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION_FEE_DECEMBER_JANUARY)
            .build();
    }

    public static final TransactionEntity multipleFeesSpanYears() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(TransactionConstants.AMOUNT_FEES)
            .withDate(TransactionConstants.DATE)
            .withDescription("Cuota de Person 1 Last name 1 para Diciembre 2020, Enero 2021")
            .build();
    }

    public static final TransactionEntity multipleFeesStartYear() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(TransactionConstants.AMOUNT_FEES)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION_FEE_FEBRUARY_MARCH)
            .build();
    }

    public static final TransactionEntity singleFeeNoAmount() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(0F)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION_FEE_FEBRUARY)
            .build();
    }

    public static final TransactionEntity valid() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(TransactionConstants.AMOUNT)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

}
