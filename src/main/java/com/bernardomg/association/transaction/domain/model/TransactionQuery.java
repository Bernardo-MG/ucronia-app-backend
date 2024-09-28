
package com.bernardomg.association.transaction.domain.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class TransactionQuery {

    private LocalDate date;

    private LocalDate endDate;

    private LocalDate startDate;

}
