
package com.bernardomg.association.member.test.config.factory;

import java.time.Year;
import java.time.YearMonth;

public final class MemberCalendars {

    public static final YearMonth CURRENT_DATE         = YearMonth.now();

    public static final YearMonth NEXT_YEAR_DATE       = YearMonth.now()
        .plusYears(1);

    public static final YearMonth PREVIOUS_MONTH_DATE  = YearMonth.now()
        .minusMonths(1);

    public static final YearMonth TWO_MONTHS_BACK_DATE = YearMonth.now()
        .minusMonths(2);

    public static final int       YEAR                 = 2020;

    public static final int       YEAR_CURRENT         = Year.now()
        .getValue();

    public static final int       YEAR_PREVIOUS        = 2019;

    private MemberCalendars() {
        super();
    }

}
