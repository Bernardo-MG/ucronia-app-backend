
package com.bernardomg.association.membership.test.calendar.util.model;

import java.time.YearMonth;

import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.test.fee.util.model.Fees;

public final class FeeMonths {

    public static final YearMonth CURRENT_DATE   = YearMonth.now();

    public static final int       CURRENT_MONTH  = YearMonth.now()
        .getMonthValue();

    public static final YearMonth NEXT_YEAR_DATE = YearMonth.now()
        .plusYears(1);

    public static final FeeMonth notPaid() {
        return FeeMonth.builder()
            .date(Fees.DATE)
            .memberId(1l)
            .month(CURRENT_MONTH)
            .paid(false)
            .build();
    }

    public static final FeeMonth notPaidCurrent() {
        return FeeMonth.builder()
            .date(CURRENT_DATE)
            .memberId(1l)
            .month(CURRENT_MONTH)
            .paid(false)
            .build();
    }

    public static final FeeMonth notPaidNextYear() {
        return FeeMonth.builder()
            .date(NEXT_YEAR_DATE)
            .memberId(1l)
            .month(CURRENT_MONTH)
            .paid(false)
            .build();
    }

    public static final FeeMonth notPaidPreviousMonth() {
        return FeeMonth.builder()
            .date(CURRENT_DATE.minusMonths(1))
            .memberId(1l)
            .month(CURRENT_MONTH - 1)
            .paid(false)
            .build();
    }

    public static final FeeMonth notPaidTwoMonthsBack() {
        return FeeMonth.builder()
            .date(CURRENT_DATE.minusMonths(2))
            .memberId(1l)
            .month(CURRENT_MONTH - 2)
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

    public static final FeeMonth paid(final int year, final int month) {
        return FeeMonth.builder()
            .date(YearMonth.of(year, month))
            .memberId(1l)
            .month(month)
            .paid(true)
            .build();
    }

    public static final FeeMonth paidCurrent() {
        return FeeMonth.builder()
            .date(CURRENT_DATE)
            .memberId(1l)
            .month(CURRENT_MONTH)
            .paid(true)
            .build();
    }

    public static final FeeMonth paidNextYear() {
        return FeeMonth.builder()
            .date(NEXT_YEAR_DATE)
            .memberId(1l)
            .month(CURRENT_MONTH)
            .paid(true)
            .build();
    }

    public static final FeeMonth paidPreviousMonth() {
        return FeeMonth.builder()
            .date(CURRENT_DATE.minusMonths(1))
            .memberId(1l)
            .month(CURRENT_MONTH - 1)
            .paid(true)
            .build();
    }

    public static final FeeMonth paidTwoMonthsBack() {
        return FeeMonth.builder()
            .date(CURRENT_DATE.minusMonths(2))
            .memberId(1l)
            .month(CURRENT_MONTH - 2)
            .paid(true)
            .build();
    }

    private FeeMonths() {
        super();
    }

}
