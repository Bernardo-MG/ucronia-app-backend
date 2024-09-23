
package com.bernardomg.association.transaction.test.configuration.factory;

import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;

public final class TransactionCalendarMonthsRanges {

    public static final List<YearMonth> CONSECUTIVE_FULL_YEAR_MONTHS     = List.of(YearMonth.of(2020, Month.JANUARY),
        YearMonth.of(2020, Month.FEBRUARY), YearMonth.of(2020, Month.MARCH), YearMonth.of(2020, Month.APRIL),
        YearMonth.of(2020, Month.MAY), YearMonth.of(2020, Month.JUNE), YearMonth.of(2020, Month.JULY),
        YearMonth.of(2020, Month.AUGUST), YearMonth.of(2020, Month.SEPTEMBER), YearMonth.of(2020, Month.OCTOBER),
        YearMonth.of(2020, Month.NOVEMBER), YearMonth.of(2020, Month.DECEMBER), YearMonth.of(2021, Month.JANUARY),
        YearMonth.of(2021, Month.FEBRUARY), YearMonth.of(2021, Month.MARCH), YearMonth.of(2021, Month.APRIL),
        YearMonth.of(2021, Month.MAY), YearMonth.of(2021, Month.JUNE), YearMonth.of(2021, Month.JULY),
        YearMonth.of(2021, Month.AUGUST), YearMonth.of(2021, Month.SEPTEMBER), YearMonth.of(2021, Month.OCTOBER),
        YearMonth.of(2021, Month.NOVEMBER), YearMonth.of(2021, Month.DECEMBER));

    public static final List<YearMonth> FULL_YEAR_MONTHS                 = List.of(YearMonth.of(2020, Month.JANUARY),
        YearMonth.of(2020, Month.FEBRUARY), YearMonth.of(2020, Month.MARCH), YearMonth.of(2020, Month.APRIL),
        YearMonth.of(2020, Month.MAY), YearMonth.of(2020, Month.JUNE), YearMonth.of(2020, Month.JULY),
        YearMonth.of(2020, Month.AUGUST), YearMonth.of(2020, Month.SEPTEMBER), YearMonth.of(2020, Month.OCTOBER),
        YearMonth.of(2020, Month.NOVEMBER), YearMonth.of(2020, Month.DECEMBER));

    public static final List<YearMonth> NOT_CONSECUTIVE_FULL_YEAR_MONTHS = List.of(YearMonth.of(2020, Month.JANUARY),
        YearMonth.of(2020, Month.FEBRUARY), YearMonth.of(2020, Month.MARCH), YearMonth.of(2020, Month.APRIL),
        YearMonth.of(2020, Month.MAY), YearMonth.of(2020, Month.JUNE), YearMonth.of(2020, Month.JULY),
        YearMonth.of(2020, Month.AUGUST), YearMonth.of(2020, Month.SEPTEMBER), YearMonth.of(2020, Month.OCTOBER),
        YearMonth.of(2020, Month.NOVEMBER), YearMonth.of(2020, Month.DECEMBER), YearMonth.of(2022, Month.JANUARY),
        YearMonth.of(2022, Month.FEBRUARY), YearMonth.of(2022, Month.MARCH), YearMonth.of(2022, Month.APRIL),
        YearMonth.of(2022, Month.MAY), YearMonth.of(2022, Month.JUNE), YearMonth.of(2022, Month.JULY),
        YearMonth.of(2022, Month.AUGUST), YearMonth.of(2022, Month.SEPTEMBER), YearMonth.of(2022, Month.OCTOBER),
        YearMonth.of(2022, Month.NOVEMBER), YearMonth.of(2022, Month.DECEMBER));

    public static final TransactionCalendarMonthsRange empty() {
        return TransactionCalendarMonthsRange.builder()
            .withMonths(FULL_YEAR_MONTHS)
            .build();
    }

    public static final TransactionCalendarMonthsRange fullYear() {
        return TransactionCalendarMonthsRange.builder()
            .withMonths(FULL_YEAR_MONTHS)
            .build();
    }

    private TransactionCalendarMonthsRanges() {
        super();
    }

}
