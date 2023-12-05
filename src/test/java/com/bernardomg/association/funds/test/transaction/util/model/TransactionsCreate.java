
package com.bernardomg.association.funds.test.transaction.util.model;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.transaction.model.request.TransactionCreate;
import com.bernardomg.association.funds.transaction.model.request.TransactionCreateRequest;

public final class TransactionsCreate {

    public static final TransactionCreate amount(final Float amount) {
        return TransactionCreateRequest.builder()
            .description("Transaction")
            .amount(amount)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionCreate emptyDescription() {
        return TransactionCreateRequest.builder()
            .description("")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionCreate firstDay() {
        return TransactionCreateRequest.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build();
    }

    public static final TransactionCreate inYear() {
        return TransactionCreateRequest.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionCreate missingAmount() {
        return TransactionCreateRequest.builder()
            .description("Transaction")
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionCreate missingDate() {
        return TransactionCreateRequest.builder()
            .description("Transaction")
            .amount(1f)
            .build();
    }

    public static final TransactionCreate missingDescription() {
        return TransactionCreateRequest.builder()
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionCreate paddedWithWhitespaces() {
        return TransactionCreateRequest.builder()
            .description(" Transaction ")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionCreate valid() {
        return TransactionCreateRequest.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

}
