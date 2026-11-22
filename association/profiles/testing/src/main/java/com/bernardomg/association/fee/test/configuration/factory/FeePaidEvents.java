
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.YearMonth;

import com.bernardomg.association.fee.domain.event.FeePaidEvent;

public final class FeePaidEvents {

    public static final FeePaidEvent valid() {
        return new FeePaidEvent("com.bernardomg.ucronia", YearMonth.of(2026, 10), 1L, 2L, Instant.EPOCH);
    }

    private FeePaidEvents() {
        super();
    }

}
