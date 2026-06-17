
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public final class FeeConstants {

    public static final Instant APRIL_DATE                  = YearMonth.of(FeeConstants.YEAR_VALUE, Month.APRIL)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant CURRENT_MONTH               = YearMonth.now()
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Year    CURRENT_YEAR                = Year.now();

    public static final Instant DATE                        = YearMonth.of(2020, Month.FEBRUARY)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant DECEMBER_DATE               = YearMonth.of(FeeConstants.YEAR_VALUE, Month.DECEMBER)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant FEBRUARY_DATE               = YearMonth.of(FeeConstants.YEAR_VALUE, Month.FEBRUARY)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Long    FEE_TYPE_ALTERNATIVE_NUMBER = 20L;

    public static final Float   FEE_TYPE_AMOUNT             = 1F;

    public static final String  FEE_TYPE_NAME               = "Fee Type";

    public static final Long    FEE_TYPE_NUMBER             = 10L;

    public static final Instant FIRST_NEXT_YEAR_DATE        = YearMonth.of(2021, Month.JANUARY)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant JANUARY_DATE                = YearMonth.of(FeeConstants.YEAR_VALUE, Month.JANUARY)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant JULY_DATE                   = YearMonth.of(FeeConstants.YEAR_VALUE, Month.JULY)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant JUNE_DATE                   = YearMonth.of(FeeConstants.YEAR_VALUE, Month.JUNE)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant LAST_YEAR_DATE              = YearMonth.of(FeeConstants.YEAR_VALUE, Month.DECEMBER)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant MARCH_DATE                  = YearMonth.of(FeeConstants.YEAR_VALUE, Month.MARCH)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant MAY_DATE                    = YearMonth.of(FeeConstants.YEAR_VALUE, Month.MAY)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant MONTH_END                   = FeeConstants.DATE.atZone(ZoneOffset.UTC)
        .withDayOfMonth(FeeConstants.DATE.atZone(ZoneOffset.UTC)
            .toLocalDate()
            .lengthOfMonth())
        .toLocalDate()
        .atTime(LocalTime.MAX)
        .atZone(ZoneOffset.UTC)
        .toInstant();

    public static final Instant MONTH_START                 = FeeConstants.DATE.atZone(ZoneOffset.UTC)
        .withDayOfMonth(1)
        .toLocalDate()
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant NEXT_DATE                   = YearMonth.of(FeeConstants.YEAR_VALUE, Month.MARCH)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant NEXT_MONTH                  = YearMonth.now()
        .plusMonths(1)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant NEXT_MONTH_END              = FeeConstants.NEXT_MONTH.atZone(ZoneOffset.UTC)
        .withDayOfMonth(FeeConstants.DATE.atZone(ZoneOffset.UTC)
            .toLocalDate()
            .lengthOfMonth())
        .toLocalDate()
        .atTime(LocalTime.MAX)
        .atZone(ZoneOffset.UTC)
        .toInstant();

    public static final Instant NEXT_MONTH_START            = FeeConstants.NEXT_MONTH.atZone(ZoneOffset.UTC)
        .withDayOfMonth(1)
        .toLocalDate()
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Year    NEXT_YEAR                   = Year.now()
        .plusYears(1);

    public static final Instant NEXT_YEAR_MONTH             = YearMonth.now()
        .plusYears(1)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant NOVEMBER_DATE               = YearMonth.of(FeeConstants.YEAR_VALUE, Month.NOVEMBER)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    /**
     * TODO: rename, this is confusing with the payment date
     */
    public static final Instant PAYMENT_DATE                = LocalDate.of(FeeConstants.YEAR_VALUE, Month.FEBRUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant PAYMENT_DATE_FUTURE         = Instant.now()
        .plus(2L, ChronoUnit.DAYS);

    public static final Instant PREVIOUS_MONTH              = YearMonth.now()
        .minusMonths(1)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Year    PREVIOUS_YEAR               = Year.now()
        .minusYears(1);

    public static final Instant PREVIOUS_YEAR_MONTH         = YearMonth.now()
        .minusYears(1)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Year    PREVIOUS_YEAR_TO_DEFAULT    = Year.of(2019);

    public static final Instant TWO_MONTHS_BACK             = YearMonth.now()
        .minusMonths(2)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant TWO_YEARS_BACK              = YearMonth.now()
        .minusYears(2)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Year    YEAR                        = Year.of(2020);

    public static final int     YEAR_VALUE                  = 2020;

    private FeeConstants() {
        super();
    }

}
