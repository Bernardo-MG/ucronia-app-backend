
package com.bernardomg.association.library.book.test.config.factory;

import java.time.LocalDate;
import java.time.Month;

public final class BookConstants {

    public static final String    INVALID_ISBN_10    = "0-306-40615-1";

    public static final String    INVALID_ISBN_13    = "123-4-567-89012-3";

    public static final String    ISBN_10            = "1-56619-909-3";

    public static final String    ISBN_10X           = "1-55404-295-X";

    public static final String    ISBN_13            = "978-1-56619-909-4";

    public static final String    LANGUAGE           = "en";

    public static final LocalDate LENT_DATE          = LocalDate.of(2020, Month.JANUARY, 1);

    public static final LocalDate LENT_DATE_LAST     = LocalDate.of(2020, Month.MAY, 10);

    public static final long      NEXT_NUMBER        = 2L;

    public static final long      NUMBER             = 1L;

    public static final LocalDate RETURNED_DATE      = LocalDate.of(2020, Month.JANUARY, 2);

    public static final LocalDate RETURNED_DATE_LAST = LocalDate.of(2020, Month.MAY, 12);

    public static final String    TITLE              = "Title";

}
