
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.YearMonth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class FeeChange {

    @NotNull
    private YearMonth             month;

    @NotNull
    private FeePaymentMember      person;

    @NotNull
    private FeePaymentTransaction transaction;

}
