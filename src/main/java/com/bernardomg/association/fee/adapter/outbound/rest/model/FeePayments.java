
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;

import jakarta.validation.constraints.NotNull;

/**
 * TODO: rename feeMonths to months
 */
public final record FeePayments(@NotNull FeePaymentPerson person, @NotNull FeePaymentPayment payment,
        @NotNull Collection<@NotNull YearMonth> feeMonths) {

    public static final record FeePaymentPayment(@NotNull LocalDate date) {

    }

    public static final record FeePaymentPerson(@NotNull Long number) {

    }

}
