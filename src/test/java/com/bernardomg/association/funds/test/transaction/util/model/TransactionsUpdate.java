
package com.bernardomg.association.funds.test.transaction.util.model;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.transaction.model.request.TransactionUpdate;
import com.bernardomg.association.funds.transaction.model.request.TransactionUpdateRequest;

public final class TransactionsUpdate {

    public static final TransactionUpdate decimal() {
        return TransactionUpdateRequest.builder()
            .description("Transaction")
            .amount(1.2f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionUpdate descriptionChange() {
        return TransactionUpdateRequest.builder()
            .description("Transaction 123")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionUpdate emptyDescription() {
        return TransactionUpdateRequest.builder()
            .description("")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionUpdate inYear() {
        return TransactionUpdateRequest.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionUpdate missingAmount() {
        return TransactionUpdateRequest.builder()
            .description("Transaction")
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionUpdate missingDate() {
        return TransactionUpdateRequest.builder()
            .description("Transaction")
            .amount(1f)
            .build();
    }

    public static final TransactionUpdate missingDescription() {
        return TransactionUpdateRequest.builder()
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionUpdate paddedWithWhitespaces() {
        return TransactionUpdateRequest.builder()
            .description(" Transaction 123 ")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

}
