
package com.bernardomg.association.membership.test.fee.config.factory;

import java.time.YearMonth;

import com.bernardomg.association.fee.model.FeeQuery;

public final class FeesQuery {

    public static final FeeQuery empty() {
        return FeeQuery.builder()
            .build();
    }

    public static final FeeQuery endDate(final YearMonth date) {
        return FeeQuery.builder()
            .endDate(date)
            .build();
    }

    public static final FeeQuery inDate(final YearMonth date) {
        return FeeQuery.builder()
            .date(date)
            .build();
    }

    public static final FeeQuery startDate(final YearMonth date) {
        return FeeQuery.builder()
            .startDate(date)
            .build();
    }

}
