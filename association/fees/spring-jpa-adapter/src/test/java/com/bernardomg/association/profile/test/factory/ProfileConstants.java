
package com.bernardomg.association.profile.test.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

public final class ProfileConstants {

    public static final String  ADDRESS                = "Address";

    public static final long    ALTERNATIVE_NUMBER     = 20;

    public static final Instant BIRTH_DATE             = LocalDate.of(1990, Month.JANUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final String  CHANGED_FIRST_NAME     = "Name 3";

    public static final String  COMMENTS               = "Comments";

    public static final String  EMAIL                  = "email@somewhere.com";

    public static final String  FIRST_NAME             = "Name 1";

    public static final String  IDENTIFIER             = "6789";

    public static final String  LAST_NAME              = "Last name 1";

    public static final long    NUMBER                 = 10;

    private ProfileConstants() {
        super();
    }

}
