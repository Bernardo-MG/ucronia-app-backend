
package com.bernardomg.association.funds.test.transaction.util.model;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.membership.test.fee.util.model.Fees;

public final class PersistentTransactions {

    public static final PersistentTransaction forAmount(final Float value) {
        return PersistentTransaction.builder()
            .id(1L)
            .amount(value)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

    public static final PersistentTransaction multipleFees() {
        return PersistentTransaction.builder()
            .id(2L)
            .amount(2F)
            .date(Fees.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Febrero 2020, Marzo 2020")
            .build();
    }

    public static final PersistentTransaction singleFee() {
        return PersistentTransaction.builder()
            .id(2L)
            .amount(1F)
            .date(Fees.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Febrero 2020")
            .build();
    }

    public static final PersistentTransaction singleFeeNoAmount() {
        return PersistentTransaction.builder()
            .id(2L)
            .amount(0F)
            .date(Fees.PAYMENT_DATE)
            .description("Cuota de Member 1 Surname 1 para Febrero 2020")
            .build();
    }

    public static final PersistentTransaction valid() {
        return PersistentTransaction.builder()
            .amount(1F)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .description("Transaction")
            .build();
    }

}
