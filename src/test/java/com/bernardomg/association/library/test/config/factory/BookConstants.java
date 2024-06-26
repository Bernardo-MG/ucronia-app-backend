
package com.bernardomg.association.library.test.config.factory;

import java.time.LocalDate;

public final class BookConstants {

    public static final String    ISBN          = "isbn";

    public static final String    LANGUAGE      = "en";

    public static final LocalDate LENT_DATE     = LocalDate.now();

    public static final long      NEXT_NUMBER   = 2L;

    public static final long      NUMBER        = 1L;

    public static final LocalDate RETURNED_DATE = LocalDate.now()
        .minusDays(1);

    public static final String    TITLE         = "Title";

}
