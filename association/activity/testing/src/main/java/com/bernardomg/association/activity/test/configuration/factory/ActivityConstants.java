
package com.bernardomg.association.activity.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public final class ActivityConstants {

    public static final Instant DATE        = LocalDate.of(2020, Month.FEBRUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant DATE_FUTURE = Instant.now()
        .plus(2L, ChronoUnit.DAYS);

    public static final String  DESCRIPTION = "Activity description";

    public static final long    NEXT_NUMBER = 11;

    public static final long    NUMBER      = 10;

    public static final String  TITLE       = "Title";

}
