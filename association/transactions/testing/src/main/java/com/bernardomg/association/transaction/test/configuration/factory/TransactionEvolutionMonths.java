
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.Instant;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneOffset;

import com.bernardomg.association.transaction.domain.model.TransactionEvolutionMonth;

public final class TransactionEvolutionMonths {

    public static final TransactionEvolutionMonth currentMonth(final float amount) {
        return new TransactionEvolutionMonth(TransactionConstants.CURRENT, amount, amount);
    }

    public static final TransactionEvolutionMonth forAmount(final float amount) {
        return new TransactionEvolutionMonth(TransactionConstants.MONTH, amount, amount);
    }

    public static final TransactionEvolutionMonth forAmount(final Instant month, final float amount) {
        return new TransactionEvolutionMonth(month, amount, amount);
    }

    public static final TransactionEvolutionMonth forAmount(final Month month, final float amount, final float total) {
        return new TransactionEvolutionMonth(YearMonth.of(2020, month)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), amount, total);
    }

    private TransactionEvolutionMonths() {
        super();
    }

}
