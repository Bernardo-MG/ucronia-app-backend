
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public final class Fees {

    public static final YearMonth DATE         = YearMonth.of(2020, Month.FEBRUARY);

    public static final YearMonth NEXT_DATE    = YearMonth.of(2020, Month.MARCH);

    public static final LocalDate PAYMENT_DATE = LocalDate.of(2020, Month.JANUARY, 1);

    private Fees() {
        super();
    }

}
