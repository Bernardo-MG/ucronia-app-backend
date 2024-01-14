
package com.bernardomg.association.funds.test.transaction.config.factory;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.transaction.model.TransactionChange;

public final class TransactionChanges {

    public static final TransactionChange amount(final Float amount) {
        return TransactionChange.builder()
            .description(TransactionConstants.DESCRIPTION)
            .amount(amount)
            .date(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange decimal() {
        return TransactionChange.builder()
            .description(TransactionConstants.DESCRIPTION)
            .amount(1.2F)
            .date(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange descriptionChange() {
        return TransactionChange.builder()
            .description("Transaction 123")
            .amount(1F)
            .date(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange emptyDescription() {
        return TransactionChange.builder()
            .description("")
            .amount(1F)
            .date(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange firstDay() {
        return TransactionChange.builder()
            .description(TransactionConstants.DESCRIPTION)
            .amount(1F)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build();
    }

    public static final TransactionChange missingAmount() {
        return TransactionChange.builder()
            .description(TransactionConstants.DESCRIPTION)
            .date(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange missingDate() {
        return TransactionChange.builder()
            .description(TransactionConstants.DESCRIPTION)
            .amount(1F)
            .build();
    }

    public static final TransactionChange missingDescription() {
        return TransactionChange.builder()
            .amount(1F)
            .date(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange paddedWithWhitespaces() {
        return TransactionChange.builder()
            .description(" Transaction ")
            .amount(1F)
            .date(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange valid() {
        return TransactionChange.builder()
            .description(TransactionConstants.DESCRIPTION)
            .amount(1F)
            .date(TransactionConstants.DATE)
            .build();
    }

}
