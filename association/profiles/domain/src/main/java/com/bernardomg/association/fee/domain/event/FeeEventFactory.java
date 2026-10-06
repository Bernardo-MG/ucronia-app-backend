
package com.bernardomg.association.fee.domain.event;

import java.time.Instant;

public final class FeeEventFactory {

    private static final String SOURCE = "com.bernardomg.ucronia";

    public static FeeDeletedEvent feeDeleted(final Instant date, final Long profileNumber) {
        return new FeeDeletedEvent(SOURCE, date, profileNumber);
    }

    public static FeePaidEvent feePaid(final Instant date, final Long profileNumber) {
        return new FeePaidEvent(SOURCE, date, profileNumber);
    }

    private FeeEventFactory() {
        super();
    }

}
