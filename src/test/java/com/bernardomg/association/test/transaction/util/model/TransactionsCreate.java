
package com.bernardomg.association.test.transaction.util.model;

import java.util.GregorianCalendar;

import com.bernardomg.association.transaction.model.request.TransactionCreation;
import com.bernardomg.association.transaction.model.request.ValidatedTransactionCreation;

public final class TransactionsCreate {

    public static final TransactionCreation amount(final Float amount) {
        return ValidatedTransactionCreation.builder()
            .description("Transaction")
            .amount(amount)
            .date(new GregorianCalendar(2020, 1, 1))
            .build();
    }

    public static final TransactionCreation firstDay() {
        return ValidatedTransactionCreation.builder()
            .description("Transaction")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build();
    }

    public static final TransactionCreation inYear() {
        return ValidatedTransactionCreation.builder()
            .description("Transaction")
            .amount(1f)
            .date(new GregorianCalendar(2020, 1, 1))
            .build();
    }

    public static final TransactionCreation missingAmount() {
        return ValidatedTransactionCreation.builder()
            .description("Transaction")
            .date(new GregorianCalendar(2020, 1, 1))
            .build();
    }

    public static final TransactionCreation missingDate() {
        return ValidatedTransactionCreation.builder()
            .description("Transaction")
            .amount(1f)
            .build();
    }

    public static final TransactionCreation missingDescription() {
        return ValidatedTransactionCreation.builder()
            .amount(1f)
            .date(new GregorianCalendar(2020, 1, 1))
            .build();
    }
}
