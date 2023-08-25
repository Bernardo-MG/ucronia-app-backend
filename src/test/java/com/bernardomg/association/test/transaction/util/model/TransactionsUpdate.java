
package com.bernardomg.association.test.transaction.util.model;

import java.time.LocalDateTime;
import java.time.Month;

import com.bernardomg.association.transaction.model.request.TransactionUpdate;
import com.bernardomg.association.transaction.model.request.ValidatedTransactionUpdate;

public final class TransactionsUpdate {

    public static final TransactionUpdate decimal() {
        return ValidatedTransactionUpdate.builder()
            .description("Transaction")
            .amount(1.2f)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionUpdate descriptionChange() {
        return ValidatedTransactionUpdate.builder()
            .description("Transaction 123")
            .amount(1f)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionUpdate emptyDescription() {
        return ValidatedTransactionUpdate.builder()
            .description("")
            .amount(1f)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionUpdate inYear() {
        return ValidatedTransactionUpdate.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionUpdate missingAmount() {
        return ValidatedTransactionUpdate.builder()
            .description("Transaction")
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionUpdate missingDate() {
        return ValidatedTransactionUpdate.builder()
            .description("Transaction")
            .amount(1f)
            .build();
    }

    public static final TransactionUpdate missingDescription() {
        return ValidatedTransactionUpdate.builder()
            .amount(1f)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

}
