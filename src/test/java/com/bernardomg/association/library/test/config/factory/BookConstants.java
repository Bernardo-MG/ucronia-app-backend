
package com.bernardomg.association.library.test.config.factory;

import java.time.LocalDate;
import java.time.Month;

public final class BookConstants {

    public static final String    ISBN          = "isbn";

    public static final String    LANGUAGE      = "en";

    public static final LocalDate LENT_DATE     = LocalDate.of(2020, Month.JANUARY, 1);

    public static final long      NEXT_NUMBER   = 2L;

    public static final long      NUMBER        = 1L;

    public static final LocalDate RETURNED_DATE = LocalDate.now()
        .minusDays(1);

    public static final String    TITLE         = "Title";

}
