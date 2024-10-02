
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.transaction.adapter.outbound.rest.model.TransactionChange;

public final class TransactionChanges {

    public static final TransactionChange amount(final Float amount) {
        return TransactionChange.builder()
            .withDescription(TransactionConstants.DESCRIPTION)
            .withAmount(amount)
            .withDate(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange decimal() {
        return TransactionChange.builder()
            .withDescription(TransactionConstants.DESCRIPTION)
            .withAmount(1.2F)
            .withDate(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange descriptionChange() {
        return TransactionChange.builder()
            .withDescription("Transaction 123")
            .withAmount(1F)
            .withDate(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange emptyDescription() {
        return TransactionChange.builder()
            .withDescription("")
            .withAmount(1F)
            .withDate(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange firstDay() {
        return TransactionChange.builder()
            .withDescription(TransactionConstants.DESCRIPTION)
            .withAmount(1F)
            .withDate(LocalDate.of(2020, Month.JANUARY, 1))
            .build();
    }

    public static final TransactionChange missingAmount() {
        return TransactionChange.builder()
            .withDescription(TransactionConstants.DESCRIPTION)
            .withDate(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange missingDate() {
        return TransactionChange.builder()
            .withDescription(TransactionConstants.DESCRIPTION)
            .withAmount(1F)
            .build();
    }

    public static final TransactionChange missingDescription() {
        return TransactionChange.builder()
            .withAmount(1F)
            .withDate(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange paddedWithWhitespaces() {
        return TransactionChange.builder()
            .withDescription(" Transaction ")
            .withAmount(1F)
            .withDate(TransactionConstants.DATE)
            .build();
    }

    public static final TransactionChange valid() {
        return TransactionChange.builder()
            .withDescription(TransactionConstants.DESCRIPTION)
            .withAmount(1F)
            .withDate(TransactionConstants.DATE)
            .build();
    }

}
