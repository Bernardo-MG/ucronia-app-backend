
package com.bernardomg.association.guest.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

public final class GuestConstants {

    public static final Instant DATE = LocalDate.of(2025, Month.JANUARY, 1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    private GuestConstants() {
        super();
    }

}
