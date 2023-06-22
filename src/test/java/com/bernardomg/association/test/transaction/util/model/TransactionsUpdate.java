
package com.bernardomg.association.test.transaction.util.model;

import java.util.GregorianCalendar;

import com.bernardomg.association.transaction.model.request.TransactionUpdate;
import com.bernardomg.association.transaction.model.request.ValidatedTransactionUpdate;

public final class TransactionsUpdate {

    public static final TransactionUpdate decimal() {
        return ValidatedTransactionUpdate.builder()
            .id(1L)
            .description("Transaction")
            .amount(1.2f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build();
    }

    public static final TransactionUpdate descriptionChange() {
        return ValidatedTransactionUpdate.builder()
            .id(1L)
            .description("Transaction 123")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build();
    }

}
