
package com.bernardomg.association.transaction.test.config.factory;

import java.time.LocalDate;

import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.transaction.infra.inbound.jpa.model.TransactionEntity;

public final class TransactionEntities {

    public static final TransactionEntity decimal() {
        return TransactionEntity.builder()
            .index(TransactionConstants.INDEX)
            .amount(1.2f)
            .date(TransactionConstants.DATE)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity descriptionChange() {
        return TransactionEntity.builder()
            .index(TransactionConstants.INDEX)
            .amount(1F)
            .date(TransactionConstants.DATE)
            .description("Transaction 123")
            .build();
    }

    public static final TransactionEntity forAmount(final Float value) {
        return TransactionEntity.builder()
            .id(TransactionConstants.INDEX)
            .index(TransactionConstants.INDEX)
            .amount(value)
            .date(TransactionConstants.DATE)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity forAmount(final Float value, final LocalDate date) {
        return TransactionEntity.builder()
            .index(TransactionConstants.INDEX)
            .amount(value)
            .date(date)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity forAmount(final Float value, final LocalDate date, final Long index) {
        return TransactionEntity.builder()
            .index(index)
            .amount(value)
            .date(date)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity index(final long index) {
        return TransactionEntity.builder()
            .index(index)
            .amount(1F)
            .date(TransactionConstants.DATE)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

    public static final TransactionEntity multipleFees() {
        return TransactionEntity.builder()
            .id(2L)
            .index(TransactionConstants.ALTERNATIVE_INDEX)
            .amount(2F)
            .date(FeeConstants.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Febrero 2020, Marzo 2020")
            .build();
    }

    public static final TransactionEntity multipleFeesSpanYears() {
        return TransactionEntity.builder()
            .id(2L)
            .index(TransactionConstants.ALTERNATIVE_INDEX)
            .amount(2F)
            .date(FeeConstants.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Diciembre 2020, Enero 2021")
            .build();
    }

    public static final TransactionEntity singleFee() {
        return TransactionEntity.builder()
            .id(2L)
            .index(TransactionConstants.ALTERNATIVE_INDEX)
            .amount(1F)
            .date(FeeConstants.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Febrero 2020")
            .build();
    }

    public static final TransactionEntity singleFeeNoAmount() {
        return TransactionEntity.builder()
            .id(2L)
            .index(TransactionConstants.ALTERNATIVE_INDEX)
            .amount(0F)
            .date(FeeConstants.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Febrero 2020")
            .build();
    }

    public static final TransactionEntity valid() {
        return TransactionEntity.builder()
            .index(TransactionConstants.INDEX)
            .amount(1F)
            .date(TransactionConstants.DATE)
            .description(TransactionConstants.DESCRIPTION)
            .build();
    }

}
