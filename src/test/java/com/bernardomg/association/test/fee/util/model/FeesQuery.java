
package com.bernardomg.association.test.fee.util.model;

import java.time.YearMonth;

import com.bernardomg.association.fee.model.request.FeeQuery;
import com.bernardomg.association.fee.model.request.ValidatedFeeQuery;

public final class FeesQuery {

    public static final FeeQuery empty() {
        return ValidatedFeeQuery.builder()
            .build();
    }

    public static final FeeQuery endDate(final YearMonth date) {
        return ValidatedFeeQuery.builder()
            .endDate(date)
            .build();
    }

    public static final FeeQuery inDate(final YearMonth date) {
        return ValidatedFeeQuery.builder()
            .date(date)
            .build();
    }

    public static final FeeQuery startDate(final YearMonth date) {
        return ValidatedFeeQuery.builder()
            .startDate(date)
            .build();
    }

}
