
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.Instant;
import java.time.YearMonth;

import jakarta.validation.constraints.NotNull;

public final record FeeChange(@NotNull YearMonth month, @NotNull FeeChangePayment payment) {

    public FeeChange(final YearMonth month, final FeeChangePayment payment) {
        this.month = month;
        if (payment == null) {
            this.payment = new FeeChangePayment(null, null);
        } else {
            this.payment = payment;
        }
    }

    public static final record FeeChangePayment(Long index, Instant date) {}

}
