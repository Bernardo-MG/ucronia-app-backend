
package com.bernardomg.association.transaction.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public final class DtoTransactionRequest implements TransactionRequest {

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
