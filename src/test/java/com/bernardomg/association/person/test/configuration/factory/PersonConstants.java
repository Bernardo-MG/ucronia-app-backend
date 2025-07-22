
package com.bernardomg.association.person.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;

public final class PersonConstants {

    public static final String    ALTERNATIVE_FIRST_NAME = "Person 2";

    public static final String    ALTERNATIVE_FULL_NAME  = "Person 2 Last name 2";

    public static final String    ALTERNATIVE_IDENTIFIER = "67890";

    public static final String    ALTERNATIVE_LAST_NAME  = "Last name 2";

    public static final long      ALTERNATIVE_NUMBER     = 20;

    public static final LocalDate BIRTH_DATE             = LocalDate.of(1990, Month.JANUARY, 1);

    public static final String    EMAIL                  = "email@somewhere.com";

    public static final String    FIRST_NAME             = "Person 1";

    public static final String    FULL_NAME              = "Person 1 Last name 1";

    public static final String    IDENTIFIER             = "6789";

    public static final String    LAST_NAME              = "Last name 1";

    public static final long      NUMBER                 = 10;

    private PersonConstants() {
        super();
    }

}
