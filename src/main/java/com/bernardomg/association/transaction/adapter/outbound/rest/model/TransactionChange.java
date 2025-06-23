
package com.bernardomg.association.transaction.adapter.outbound.rest.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public final record TransactionChange(@NotNull LocalDate date, @NotNull Float amount, @NotEmpty String description) {

}
