
package com.bernardomg.association.fee.adapter.outbound.rest.model;

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

    /**
     * TODO: rename to months
     */
    @NotNull
    private Collection<@NotNull YearMonth> feeDates;

    @NotNull
    private FeePaymentMember               member;

    @NotNull
    private FeePaymentTransaction          transaction;

}
