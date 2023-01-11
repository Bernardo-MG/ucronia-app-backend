
package com.bernardomg.association.transaction.model;

import java.util.Calendar;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoTransactionForm implements TransactionForm {

    @NotNull
    private Float    amount;

    @NotNull
    private Calendar date;

    @NotEmpty
    @NotNull
    private String   description;

}
