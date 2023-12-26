
package com.bernardomg.association.membership.test.calendar.util.model;

import java.time.YearMonth;

import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.test.fee.util.model.Fees;

public final class FeeMonths {

    public static final int CURRENT_MONTH = YearMonth.now()
        .getMonthValue();

    public static final FeeMonth notPaid() {
        return FeeMonth.builder()
            .date(Fees.DATE)
            .memberId(1l)
            .month(CURRENT_MONTH)
            .paid(false)
            .build();
    }

    public static final FeeMonth paid() {
        return FeeMonth.builder()
            .date(Fees.DATE)
            .memberId(1l)
            .month(CURRENT_MONTH)
            .paid(true)
            .build();
    }

    public static final FeeMonth paid(final int month) {
        return FeeMonth.builder()
            .date(YearMonth.of(2019, month))
            .memberId(1l)
            .month(month)
            .paid(true)
            .build();
    }

    public static final FeeMonth paidPrevious() {
        return FeeMonth.builder()
            .date(Fees.DATE)
            .memberId(1l)
            .month(CURRENT_MONTH - 1)
            .paid(true)
            .build();
    }

    private FeeMonths() {
        super();
    }

}
