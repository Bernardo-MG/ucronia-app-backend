
package com.bernardomg.association.funds.test.transaction.util.model;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.transaction.model.TransactionChange;

public final class TransactionChanges {

    public static final TransactionChange amount(final Float amount) {
        return TransactionChange.builder()
            .description("Transaction")
            .amount(amount)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionChange descriptionChange() {
        return TransactionChange.builder()
            .description("Transaction 123")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionChange decimal() {
        return TransactionChange.builder()
            .description("Transaction")
            .amount(1.2f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionChange emptyDescription() {
        return TransactionChange.builder()
            .description("")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionChange firstDay() {
        return TransactionChange.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build();
    }

    public static final TransactionChange inYear() {
        return TransactionChange.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionChange missingAmount() {
        return TransactionChange.builder()
            .description("Transaction")
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionChange missingDate() {
        return TransactionChange.builder()
            .description("Transaction")
            .amount(1f)
            .build();
    }

    public static final TransactionChange missingDescription() {
        return TransactionChange.builder()
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionChange paddedWithWhitespaces() {
        return TransactionChange.builder()
            .description(" Transaction ")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

    public static final TransactionChange valid() {
        return TransactionChange.builder()
            .description("Transaction")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build();
    }

}
