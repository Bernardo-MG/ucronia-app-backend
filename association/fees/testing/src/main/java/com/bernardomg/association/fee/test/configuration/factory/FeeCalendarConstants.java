
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Year;
import java.time.YearMonth;

public final class FeeCalendarConstants {

    public static final YearMonth CURRENT_DATE         = YearMonth.now();

    public static final Year      CURRENT_YEAR         = Year.now();

    public static final YearMonth NEXT_YEAR_DATE       = YearMonth.now()
        .plusYears(1);

    public static final YearMonth PREVIOUS_MONTH_DATE  = YearMonth.now()
        .minusMonths(1);

    public static final Year      PREVIOUS_YEAR        = Year.of(2019);

    public static final YearMonth TWO_MONTHS_BACK_DATE = YearMonth.now()
        .minusMonths(2);

    public static final Year      YEAR                 = Year.of(2020);

    private FeeCalendarConstants() {
        super();
    }

}
