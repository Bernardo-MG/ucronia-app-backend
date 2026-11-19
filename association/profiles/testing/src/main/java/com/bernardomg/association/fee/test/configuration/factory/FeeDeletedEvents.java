
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.YearMonth;

import com.bernardomg.association.fee.domain.event.FeeDeletedEvent;

public final class FeeDeletedEvents {

    public static final FeeDeletedEvent notPaid() {
        return new FeeDeletedEvent("com.bernardomg.ucronia", YearMonth.of(2026, 10), 1L, false);
    }

    public static final FeeDeletedEvent paid() {
        return new FeeDeletedEvent("com.bernardomg.ucronia", YearMonth.of(2026, 10), 1L, true);
    }

    private FeeDeletedEvents() {
        super();
    }

}
