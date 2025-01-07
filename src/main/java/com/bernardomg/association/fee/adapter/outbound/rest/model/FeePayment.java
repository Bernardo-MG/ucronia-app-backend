
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class FeePayment {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class FeePaymentPerson {

        @NotNull
        private Long number;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class FeePaymentTransaction {

        @NotNull
        private LocalDate date;

    }

    /**
     * TODO: rename to months
     */
    @NotNull
    private Collection<@NotNull YearMonth> feeMonths;

    @NotNull
    private FeePaymentPerson               person;

    @NotNull
    private FeePaymentTransaction          transaction;
}
