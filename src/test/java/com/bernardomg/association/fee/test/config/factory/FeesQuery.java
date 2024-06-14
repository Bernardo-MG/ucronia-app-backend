
package com.bernardomg.association.fee.test.config.factory;

import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.FeeQuery;

public final class FeesQuery {

    public static final FeeQuery empty() {
        return FeeQuery.builder()
            .build();
    }

    public static final FeeQuery endDate(final YearMonth date) {
        return FeeQuery.builder()
            .withEndDate(date)
            .build();
    }

    public static final FeeQuery inDate(final YearMonth date) {
        return FeeQuery.builder()
            .withDate(date)
            .build();
    }

    public static final FeeQuery inRange(final YearMonth start,final YearMonth end) {
        return FeeQuery.builder()
                .withStartDate(start)
            .withEndDate(end)
            .build();
    }

    public static final FeeQuery startDate(final YearMonth date) {
        return FeeQuery.builder()
            .withStartDate(date)
            .build();
    }

}
