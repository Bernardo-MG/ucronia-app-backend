
package com.bernardomg.association.funds.test.calendar.util.model;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.funds.calendar.model.CalendarFundsDate;

public final class CalendarFundsDates {

    public static final CalendarFundsDate february() {
        return CalendarFundsDate.builder()
            .index(2)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .amount(1)
            .description("Transaction 2")
            .build();
    }

    private CalendarFundsDates() {
        super();
    }

}
