
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.FeeQuery;

public final class FeesQuery {

    public static final FeeQuery empty() {
        return new FeeQuery(null, null, null);
    }

    public static final FeeQuery endDate(final YearMonth date) {
        return new FeeQuery(null, null, date);
    }

    public static final FeeQuery inDate(final YearMonth date) {
        return new FeeQuery(date, null, null);
    }

    public static final FeeQuery inRange(final YearMonth start, final YearMonth end) {
        return new FeeQuery(null, start, end);
    }

    public static final FeeQuery startDate(final YearMonth date) {
        return new FeeQuery(null, date, null);
    }

}
