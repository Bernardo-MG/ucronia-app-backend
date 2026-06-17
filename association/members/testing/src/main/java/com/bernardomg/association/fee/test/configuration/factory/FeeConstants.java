
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

    public static final Instant CURRENT_MONTH               = YearMonth.now()
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant DATE                        = YearMonth.of(2020, Month.FEBRUARY)
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


    public static final Instant LAST_YEAR_DATE              = YearMonth.of(FeeConstants.YEAR_VALUE, Month.DECEMBER)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant NEXT_DATE                   = YearMonth.of(FeeConstants.YEAR_VALUE, Month.MARCH)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant NEXT_YEAR_MONTH             = YearMonth.now()
        .plusYears(1)
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

    public static final Year    PREVIOUS_YEAR_TO_DEFAULT    = Year.of(2019);

    public static final Instant TWO_MONTHS_BACK             = YearMonth.now()
        .minusMonths(2)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final int     YEAR_VALUE                  = 2020;

    private FeeConstants() {
        super();
    }

}
