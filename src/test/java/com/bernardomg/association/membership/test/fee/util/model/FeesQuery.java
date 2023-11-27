
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.YearMonth;

import com.bernardomg.association.membership.fee.model.request.FeeQuery;
import com.bernardomg.association.membership.fee.model.request.FeeQueryRequest;

public final class FeesQuery {

    public static final FeeQuery empty() {
        return FeeQueryRequest.builder()
            .build();
    }

    public static final FeeQuery endDate(final YearMonth date) {
        return FeeQueryRequest.builder()
            .endDate(date)
            .build();
    }

    public static final FeeQuery inDate(final YearMonth date) {
        return FeeQueryRequest.builder()
            .date(date)
            .build();
    }

    public static final FeeQuery startDate(final YearMonth date) {
        return FeeQueryRequest.builder()
            .startDate(date)
            .build();
    }

}
