
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.YearMonth;
import java.time.ZoneOffset;

import com.bernardomg.association.fee.domain.model.FeeQuery;

public final class FeesQuery {

    public static final FeeQuery empty() {
        return new FeeQuery(null, null, null);
    }

    public static final FeeQuery from(final YearMonth month) {
        return new FeeQuery(null, month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), null);
    }

    public static final FeeQuery inMonth(final YearMonth month) {
        return new FeeQuery(month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), null, null);
    }

    public static final FeeQuery inRange(final YearMonth start, final YearMonth end) {
        return new FeeQuery(null, start.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            end.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final FeeQuery to(final YearMonth month) {
        return new FeeQuery(null, null, month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
    }

}
