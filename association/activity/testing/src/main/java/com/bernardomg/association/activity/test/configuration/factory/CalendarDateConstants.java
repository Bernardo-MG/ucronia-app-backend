
package com.bernardomg.association.activity.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public final class CalendarDateConstants {

    public static final Instant END          = LocalDateTime.of(2020, Month.FEBRUARY, 1, 21, 0)
        .toInstant(ZoneOffset.UTC);

    public static final Instant END_FUTURE   = Instant.now()
        .plus(2L, ChronoUnit.DAYS)
        .plus(5L, ChronoUnit.HOURS);

    public static final Instant START        = LocalDateTime.of(2020, Month.FEBRUARY, 1, 14, 0)
        .toInstant(ZoneOffset.UTC);

    public static final Instant START_FUTURE = Instant.now()
        .plus(2L, ChronoUnit.DAYS);

    public static final String  TITLE        = "Title";

}
