
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;

public final class FeeEntities {

    public static FeeEntity currentMonth(final Boolean paid) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.CURRENT_MONTH)
            .paid(paid)
            .build();
    }

    public static FeeEntity currentMonthAlternative(final Boolean paid) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(2L)
            .date(Fees.CURRENT_MONTH)
            .paid(paid)
            .build();
    }

    public static FeeEntity nextMonth(final Boolean paid) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.NEXT_MONTH)
            .paid(paid)
            .build();
    }

    public static FeeEntity nextYear(final Boolean paid) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.NEXT_YEAR_MONTH)
            .paid(paid)
            .build();
    }

    public static FeeEntity notPaidAt(final Month month) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(Fees.YEAR, month))
            .paid(false)
            .build();
    }

    public static FeeEntity notPaidAt(final YearMonth yearMonth) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(yearMonth)
            .paid(false)
            .build();
    }

    public static FeeEntity paid() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.DATE)
            .paid(true)
            .build();
    }

    public static FeeEntity paidAt(final Month month) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(Fees.YEAR, month))
            .paid(true)
            .build();
    }

    public static FeeEntity paidNextDate() {
        return FeeEntity.builder()
            .id(2L)
            .memberId(1L)
            .date(Fees.NEXT_DATE)
            .paid(true)
            .build();
    }

    public static FeeEntity previousMonth(final Boolean paid) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.PREVIOUS_MONTH)
            .paid(paid)
            .build();
    }

    public static FeeEntity previousYear(final Boolean paid) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.PREVIOUS_YEAR_MONTH)
            .paid(paid)
            .build();
    }

    public static FeeEntity twoMonthsBack(final Boolean paid) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.TWO_MONTHS_BACK)
            .paid(paid)
            .build();
    }

    public static FeeEntity twoYearsBack(final Boolean paid) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.TWO_YEARS_BACK)
            .paid(paid)
            .build();
    }

    private FeeEntities() {
        super();
    }

}
