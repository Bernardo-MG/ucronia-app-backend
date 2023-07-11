
package com.bernardomg.association.transaction.model.request;

import java.util.Calendar;

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
    private Calendar date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar startDate;

}
