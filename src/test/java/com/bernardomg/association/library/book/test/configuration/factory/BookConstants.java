
package com.bernardomg.association.library.book.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;

public final class BookConstants {

    public static final String    INVALID_ISBN       = "abc";

    public static final String    ISBN_10            = "1-56619-909-3";

    public static final String    ISBN_13            = "978-1-56619-909-4";

    public static final String    LANGUAGE           = "en";

    public static final LocalDate LENT_DATE          = LocalDate.of(2020, Month.JANUARY, 1);

    public static final LocalDate LENT_DATE_LAST     = LocalDate.of(2020, Month.MAY, 10);

    public static final long      NEXT_NUMBER        = 2L;

    public static final long      NUMBER             = 1L;

    public static final LocalDate RETURNED_DATE      = LocalDate.of(2020, Month.JANUARY, 2);

    public static final LocalDate RETURNED_DATE_LAST = LocalDate.of(2020, Month.MAY, 12);

    public static final String    SUBTITLE           = "Subtitle";

    public static final String    SUPERTITLE         = "Supertitle";

    public static final String    TITLE              = "Title";

}
