
package com.bernardomg.association.transaction.model.request;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import com.bernardomg.association.transaction.model.Transaction;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoTransactionCreationQuery implements Transaction {

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
