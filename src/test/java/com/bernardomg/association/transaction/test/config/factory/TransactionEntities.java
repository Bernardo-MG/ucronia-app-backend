
package com.bernardomg.association.transaction.test.config.factory;

import java.time.LocalDate;

import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;

public final class TransactionEntities {

    public static final TransactionEntity decimal() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(1.2f)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity descriptionChange() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(1F)
            .withDate(TransactionConstants.DATE)
            .withDescription("Transaction 123")
            .build();
    }

    public static final TransactionEntity forAmount(final Float value) {
        return TransactionEntity.builder()
            .withId(TransactionConstants.INDEX)
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
            .withAmount(1F)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity multipleFees() {
        return TransactionEntity.builder()
            .withId(2L)
            .withIndex(TransactionConstants.ALTERNATIVE_INDEX)
            .withAmount(2F)
            .withDate(FeeConstants.PAYMENT_DATE)
            .withDescription("Cuota de Member 1 Surname 1 para Febrero 2020, Marzo 2020")
            .build();
    }

    public static final TransactionEntity multipleFeesSpanYears() {
        return TransactionEntity.builder()
            .withId(2L)
            .withIndex(TransactionConstants.ALTERNATIVE_INDEX)
            .withAmount(2F)
            .withDate(FeeConstants.PAYMENT_DATE)
            .withDescription("Cuota de Member 1 Surname 1 para Diciembre 2020, Enero 2021")
            .build();
    }

    public static final TransactionEntity singleFee() {
        return TransactionEntity.builder()
            .withId(2L)
            .withIndex(TransactionConstants.ALTERNATIVE_INDEX)
            .withAmount(1F)
            .withDate(FeeConstants.PAYMENT_DATE)
            .withDescription("Cuota de Member 1 Surname 1 para Febrero 2020")
            .build();
    }

    public static final TransactionEntity singleFeeNoAmount() {
        return TransactionEntity.builder()
            .withId(2L)
            .withIndex(TransactionConstants.ALTERNATIVE_INDEX)
            .withAmount(0F)
            .withDate(FeeConstants.PAYMENT_DATE)
            .withDescription("Cuota de Member 1 Surname 1 para Febrero 2020")
            .build();
    }

    public static final TransactionEntity valid() {
        return TransactionEntity.builder()
            .withIndex(TransactionConstants.INDEX)
            .withAmount(1F)
            .withDate(TransactionConstants.DATE)
            .withDescription(TransactionConstants.DESCRIPTION)
            .build();
    }

}
