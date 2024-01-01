
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

public final class FeeConstants {

    public static final YearMonth CURRENT_MONTH       = YearMonth.now();

    public static final Year      CURRENT_YEAR        = Year.now();

    public static final YearMonth DATE                = YearMonth.of(2020, Month.FEBRUARY);

    public static final YearMonth NEXT_DATE           = YearMonth.of(2020, Month.MARCH);

    public static final YearMonth NEXT_MONTH          = YearMonth.now()
        .plusMonths(1);

    public static final Year      NEXT_YEAR           = Year.now()
        .plusYears(1);

    public static final YearMonth NEXT_YEAR_MONTH     = YearMonth.now()
        .plusYears(1);

    public static final LocalDate PAYMENT_DATE        = LocalDate.of(2020, Month.JANUARY, 1);

    public static final YearMonth PREVIOUS_MONTH      = YearMonth.now()
        .minusMonths(1);

    public static final Year      PREVIOUS_YEAR       = Year.now()
        .minusYears(1);

    public static final YearMonth PREVIOUS_YEAR_MONTH = YearMonth.now()
        .minusYears(1);

    public static final YearMonth TWO_MONTHS_BACK     = YearMonth.now()
        .minusMonths(2);

    public static final YearMonth TWO_YEARS_BACK      = YearMonth.now()
        .minusYears(2);

    public static final int       YEAR                = 2020;

    private FeeConstants() {
        super();
    }

}
