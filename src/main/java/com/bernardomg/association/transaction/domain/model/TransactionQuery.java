
package com.bernardomg.association.transaction.domain.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class TransactionQuery {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

}
