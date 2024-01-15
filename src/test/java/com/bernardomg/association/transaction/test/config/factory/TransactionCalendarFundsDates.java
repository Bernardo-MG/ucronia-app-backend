
package com.bernardomg.association.transaction.test.config.factory;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.transaction.model.TransactionCalendarDate;

public final class TransactionCalendarFundsDates {

    public static final TransactionCalendarDate february() {
        return TransactionCalendarDate.builder()
            .index(2)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .amount(1)
            .description("Transaction 2")
            .build();
    }

    private TransactionCalendarFundsDates() {
        super();
    }

}
