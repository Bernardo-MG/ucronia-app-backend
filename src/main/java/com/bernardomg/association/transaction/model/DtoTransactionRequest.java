
package com.bernardomg.association.transaction.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoTransactionRequest implements TransactionRequest {

    @NotNull
    private Calendar EndDate;

    @NotNull
    private Calendar startDate;

}
