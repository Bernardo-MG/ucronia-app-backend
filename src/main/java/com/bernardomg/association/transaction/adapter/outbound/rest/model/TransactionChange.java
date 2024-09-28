
package com.bernardomg.association.transaction.adapter.outbound.rest.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class TransactionChange {

    @NotNull
    private Float     amount;

    @NotNull
    private LocalDate date;

    @NotEmpty
    private String    description;

}
