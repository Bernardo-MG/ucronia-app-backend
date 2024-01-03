
package com.bernardomg.association.funds.test.transaction.config.factory;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.transaction.persistence.model.TransactionEntity;
import com.bernardomg.association.membership.test.fee.config.factory.FeeConstants;

public final class TransactionEntities {

    public static final TransactionEntity decimal() {
        return TransactionEntity.builder()
            .index(1L)
            .amount(1.2f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

    public static final TransactionEntity descriptionChange() {
        return TransactionEntity.builder()
            .index(1L)
            .amount(1F)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction 123")
            .build();
    }

    public static final TransactionEntity forAmount(final Float value) {
        return TransactionEntity.builder()
            .id(1L)
            .index(1L)
            .amount(value)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

    public static final TransactionEntity forAmount(final Float value, final LocalDate date) {
        return TransactionEntity.builder()
            .index(1L)
            .amount(value)
            .date(date)
            .description("Transaction")
            .build();
    }

    public static final TransactionEntity forAmount(final Float value, final LocalDate date, final Long index) {
        return TransactionEntity.builder()
            .index(index)
            .amount(value)
            .date(date)
            .description("Transaction")
            .build();
    }

    public static final TransactionEntity index(final long index) {
        return TransactionEntity.builder()
            .index(index)
            .amount(1F)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

    public static final TransactionEntity multipleFees() {
        return TransactionEntity.builder()
            .id(2L)
            .index(2L)
            .amount(2F)
            .date(FeeConstants.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Febrero 2020, Marzo 2020")
            .build();
    }

    public static final TransactionEntity singleFee() {
        return TransactionEntity.builder()
            .id(2L)
            .index(2L)
            .amount(1F)
            .date(FeeConstants.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Febrero 2020")
            .build();
    }

    public static final TransactionEntity singleFeeNoAmount() {
        return TransactionEntity.builder()
            .id(2L)
            .index(2L)
            .amount(0F)
            .date(FeeConstants.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Febrero 2020")
            .build();
    }

    public static final TransactionEntity valid() {
        return TransactionEntity.builder()
            .index(1L)
            .amount(1F)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

}
