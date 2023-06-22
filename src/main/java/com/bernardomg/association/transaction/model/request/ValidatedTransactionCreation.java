
package com.bernardomg.association.transaction.model.request;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class ValidatedTransactionCreation implements TransactionCreation {

    @NotNull
    private Float    amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Calendar date;

    @NotEmpty
    @NotNull
    private String   description;

    private Long     id;

}
