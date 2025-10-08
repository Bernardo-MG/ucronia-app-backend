
package com.bernardomg.association.library.book.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

public final class BookConstants {

    public static final Instant DONATION_DATE      = LocalDate.of(1995, Month.JANUARY, 2)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final String  INVALID_ISBN       = "abc";

    public static final String  ISBN_10            = "1-56619-909-3";

    public static final String  ISBN_13            = "978-1-56619-909-4";

    public static final String  LANGUAGE           = "en";

    public static final Instant LENT_DATE          = LocalDate.of(2020, Month.JANUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant LENT_DATE_LAST     = LocalDate.of(2020, Month.MAY, 10)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant LENT_DATE_TODAY    = Instant.now();

    public static final long    NEXT_NUMBER        = 2L;

    public static final long    NUMBER             = 1L;

    public static final Instant PUBLISH_DATE       = LocalDate.of(1990, Month.JANUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant RETURNED_DATE      = LocalDate.of(2020, Month.JANUARY, 2)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant RETURNED_DATE_LAST = LocalDate.of(2020, Month.MAY, 12)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final String  SUBTITLE           = "Subtitle";

    public static final String  SUPERTITLE         = "Supertitle";

    public static final String  TITLE              = "Title";

}
