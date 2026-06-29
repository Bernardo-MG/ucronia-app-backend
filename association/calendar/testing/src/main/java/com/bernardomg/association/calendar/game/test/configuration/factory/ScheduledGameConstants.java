
package com.bernardomg.association.calendar.game.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

public final class ScheduledGameConstants {

    public static final String  DESCRIPTION = "Description";

    public static final String  IMAGE       = "image.png";

    public static final String  LOCATION    = "location";

    public static final int     MAX_PLAYERS = 5;

    public static final long    NUMBER      = 10;

    public static final Instant START       = LocalDate.of(2025, Month.FEBRUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final String  TITLE       = "Title";

}
