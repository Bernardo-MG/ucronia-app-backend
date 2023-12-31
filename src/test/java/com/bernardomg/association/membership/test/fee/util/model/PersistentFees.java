
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;

public final class PersistentFees {

    public static PersistentFee currentMonth(final Boolean paid) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.CURRENT_MONTH)
            .paid(paid)
            .build();
    }

    public static PersistentFee currentMonthAlternative(final Boolean paid) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(2L)
            .date(Fees.CURRENT_MONTH)
            .paid(paid)
            .build();
    }

    public static PersistentFee nextMonth(final Boolean paid) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.NEXT_MONTH)
            .paid(paid)
            .build();
    }

    public static PersistentFee nextYear(final Boolean paid) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.NEXT_YEAR_MONTH)
            .paid(paid)
            .build();
    }

    public static PersistentFee notPaidAt(final Month month) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(Fees.YEAR, month))
            .paid(false)
            .build();
    }

    public static PersistentFee notPaidAt(final YearMonth yearMonth) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(yearMonth)
            .paid(false)
            .build();
    }

    public static PersistentFee paid() {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.DATE)
            .paid(true)
            .build();
    }

    public static PersistentFee paidAt(final Month month) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(Fees.YEAR, month))
            .paid(true)
            .build();
    }

    public static PersistentFee paidNextDate() {
        return PersistentFee.builder()
            .id(2L)
            .memberId(1L)
            .date(Fees.NEXT_DATE)
            .paid(true)
            .build();
    }

    public static PersistentFee previousMonth(final Boolean paid) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.PREVIOUS_MONTH)
            .paid(paid)
            .build();
    }

    public static PersistentFee previousYear(final Boolean paid) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.PREVIOUS_YEAR_MONTH)
            .paid(paid)
            .build();
    }

    public static PersistentFee twoMonthsBack(final Boolean paid) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.TWO_MONTHS_BACK)
            .paid(paid)
            .build();
    }

    public static PersistentFee twoYearsBack(final Boolean paid) {
        return PersistentFee.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.TWO_YEARS_BACK)
            .paid(paid)
            .build();
    }

    private PersistentFees() {
        super();
    }

}
