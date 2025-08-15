
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

import com.bernardomg.association.transaction.adapter.outbound.rest.model.TransactionChange;

public final class TransactionChanges {

    public static final TransactionChange amount(final Float amount) {
        return new TransactionChange(TransactionConstants.DATE, amount, TransactionConstants.DESCRIPTION);
    }

    public static final TransactionChange decimal() {
        return new TransactionChange(TransactionConstants.DATE, 1.2F, TransactionConstants.DESCRIPTION);
    }

    public static final TransactionChange descriptionChange() {
        return new TransactionChange(TransactionConstants.DATE, 1F, "Transaction 123");
    }

    public static final TransactionChange emptyDescription() {
        return new TransactionChange(TransactionConstants.DATE, 1F, "");
    }

    public static final TransactionChange firstDay() {
        return new TransactionChange(LocalDate.of(2020, Month.JANUARY, 1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), 1F, TransactionConstants.DESCRIPTION);
    }

    public static final TransactionChange missingAmount() {
        return new TransactionChange(TransactionConstants.DATE, null, TransactionConstants.DESCRIPTION);
    }

    public static final TransactionChange missingDate() {
        return new TransactionChange(null, 1F, TransactionConstants.DESCRIPTION);
    }

    public static final TransactionChange missingDescription() {
        return new TransactionChange(TransactionConstants.DATE, 1F, null);
    }

    public static final TransactionChange padded() {
        return new TransactionChange(TransactionConstants.DATE, 1F, " Transaction ");
    }

    public static final TransactionChange valid() {
        return new TransactionChange(TransactionConstants.DATE, 1F, TransactionConstants.DESCRIPTION);
    }

}
