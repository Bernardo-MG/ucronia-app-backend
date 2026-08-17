
package com.bernardomg.association.calendar.game.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

public final class ScheduledGameConstants {

    public static final String  ALTERNATIVE_TITLE             = "Title 2";

    public static final String  CALENDAR_TYPE_CAMPAIGN_COLOR  = "#0000FF";

    public static final long    CALENDAR_TYPE_CAMPAIGN_ID     = 3L;

    public static final String  CALENDAR_TYPE_CAMPAIGN_NAME   = "Campaña";

    public static final long    CALENDAR_TYPE_CAMPAIGN_NUMBER = 3L;

    public static final String  CALENDAR_TYPE_ONESHOT_COLOR   = "#008000";

    public static final long    CALENDAR_TYPE_ONESHOT_ID      = 2L;

    public static final String  CALENDAR_TYPE_ONESHOT_NAME    = "Oneshot";

    public static final long    CALENDAR_TYPE_ONESHOT_NUMBER  = 2L;

    public static final String  DESCRIPTION                   = "Game description";

    public static final String  IMAGE                         = "image.png";

    public static final String  LOCATION                      = "Location";

    public static final int     MAX_PLAYERS                   = 5;

    public static final long    NUMBER                        = 10;

    public static final long    NUMBER_MASTER                 = 10;

    public static final boolean PUBLISHED                     = false;

    public static final Instant START                         = LocalDate.of(2025, Month.FEBRUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final String  TITLE                         = "Title";

}
