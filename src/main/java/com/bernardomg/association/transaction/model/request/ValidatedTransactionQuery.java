
package com.bernardomg.association.transaction.model.request;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ValidatedTransactionQuery implements TransactionQuery {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

}
