
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.LocalDate;
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class FeeChangePayment {

        private LocalDate date;

        @NotNull
        private Long      index;

    }

    @NotNull
    private YearMonth        month;

    @NotNull
    private FeeChangePayment payment;

}
