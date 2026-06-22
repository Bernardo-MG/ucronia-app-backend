
package com.bernardomg.association.activity.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

public final class CalendarDayConstants {

    public static final Instant START = LocalDate.of(2020, Month.FEBRUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final String  TITLE = "Title";

}
