
package com.bernardomg.association.transaction.model;

import java.util.Calendar;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public final class DtoTransactionForm implements TransactionForm {

    @NotNull
    private Float    amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Calendar date;

    @NotEmpty
    @NotNull
    private String   description;

}
