
package com.bernardomg.association.membership.test.calendar.util.model;

import java.time.YearMonth;

import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.calendar.model.ImmutableFeeMonth;

public final class FeeMonths {

    public static final int CURRENT_MONTH = YearMonth.now()
        .getMonthValue();

    public static final FeeMonth notPaid() {
        return ImmutableFeeMonth.builder()
            .feeId(1l)
            .month(CURRENT_MONTH)
            .paid(false)
            .build();
    }

    public static final FeeMonth paid() {
        return ImmutableFeeMonth.builder()
            .feeId(1l)
            .month(CURRENT_MONTH)
            .paid(true)
            .build();
    }

    public static final FeeMonth paidPrevious() {
        return ImmutableFeeMonth.builder()
            .feeId(1l)
            .month(CURRENT_MONTH - 1)
            .paid(true)
            .build();
    }

    private FeeMonths() {
        super();
    }

}
