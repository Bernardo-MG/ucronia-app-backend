
package com.bernardomg.association.contact.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

public final class ContactConstants {

    public static final String  ALTERNATIVE_EMAIL      = "email2@somewhere.com";

    public static final String  ALTERNATIVE_FIRST_NAME = "Contact 2";

    public static final String  ALTERNATIVE_FULL_NAME  = "Contact 2 Last name 2";

    public static final String  ALTERNATIVE_IDENTIFIER = "67890";

    public static final String  ALTERNATIVE_LAST_NAME  = "Last name 2";

    public static final long    ALTERNATIVE_NUMBER     = 20;

    public static final Instant BIRTH_DATE             = LocalDate.of(1990, Month.JANUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final String  COMMENTS               = "Comments";

    public static final String  EMAIL                  = "email@somewhere.com";

    public static final String  FIRST_NAME             = "Contact 1";

    public static final String  FULL_NAME              = "Contact 1 Last name 1";

    public static final String  IDENTIFIER             = "6789";

    public static final String  LAST_NAME              = "Last name 1";

    public static final long    NUMBER                 = 10;

    public static final String  PHONE                  = "123456789";

    public static final String  TYPE_MEMBER            = "member";

    private ContactConstants() {
        super();
    }

}
