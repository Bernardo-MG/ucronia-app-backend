
package com.bernardomg.association.test.transaction.util.model;

import java.time.LocalDateTime;
import java.time.Month;

import com.bernardomg.association.transaction.model.request.TransactionCreate;
import com.bernardomg.association.transaction.model.request.ValidatedTransactionCreate;

public final class TransactionsCreate {

    public static final TransactionCreate amount(final Float amount) {
        return ValidatedTransactionCreate.builder()
            .description("Transaction")
            .amount(amount)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionCreate emptyDescription() {
        return ValidatedTransactionCreate.builder()
            .description("")
            .amount(1f)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionCreate firstDay() {
        return ValidatedTransactionCreate.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionCreate inYear() {
        return ValidatedTransactionCreate.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionCreate missingAmount() {
        return ValidatedTransactionCreate.builder()
            .description("Transaction")
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final TransactionCreate missingDate() {
        return ValidatedTransactionCreate.builder()
            .description("Transaction")
            .amount(1f)
            .build();
    }

    public static final TransactionCreate missingDescription() {
        return ValidatedTransactionCreate.builder()
            .amount(1f)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

}
