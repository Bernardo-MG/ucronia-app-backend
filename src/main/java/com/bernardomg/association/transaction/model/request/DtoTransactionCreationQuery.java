
package com.bernardomg.association.transaction.model.request;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoTransactionCreationQuery implements TransactionCreationQuery {

    @NotNull
    private Float    amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Calendar date;

    @NotEmpty
    @NotNull
    private String   description;

}
