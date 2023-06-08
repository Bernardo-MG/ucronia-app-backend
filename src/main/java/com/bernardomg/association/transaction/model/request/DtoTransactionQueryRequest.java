
package com.bernardomg.association.transaction.model.request;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoTransactionQueryRequest implements TransactionQueryRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Calendar date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Calendar endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Calendar startDate;

}
