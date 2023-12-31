
package com.bernardomg.association.membership.test.fee.util.model;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;

public final class FeeEntities {

    public static FeeEntity atDate() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.DATE)
            .build();
    }

    public static FeeEntity currentMonth() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.CURRENT_MONTH)
            .build();
    }

    public static FeeEntity currentMonthAlternative() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(2L)
            .date(Fees.CURRENT_MONTH)
            .build();
    }

    public static FeeEntity nextDate() {
        return FeeEntity.builder()
            .id(2L)
            .memberId(1L)
            .date(Fees.NEXT_DATE)
            .build();
    }

    public static FeeEntity nextMonth() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.NEXT_MONTH)
            .build();
    }

    public static FeeEntity nextYear() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.NEXT_YEAR_MONTH)
            .build();
    }

    public static FeeEntity previousMonth() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.PREVIOUS_MONTH)
            .build();
    }

    public static FeeEntity previousYear() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.PREVIOUS_YEAR_MONTH)
            .build();
    }

    public static FeeEntity twoMonthsBack() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.TWO_MONTHS_BACK)
            .build();
    }

    public static FeeEntity twoYearsBack() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(Fees.TWO_YEARS_BACK)
            .build();
    }

    private FeeEntities() {
        super();
    }

}
