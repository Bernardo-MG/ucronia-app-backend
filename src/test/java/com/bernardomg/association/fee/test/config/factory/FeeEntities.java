
package com.bernardomg.association.fee.test.config.factory;

import com.bernardomg.association.fee.infra.inbound.jpa.model.FeeEntity;

public final class FeeEntities {

    public static FeeEntity atDate() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.DATE)
            .build();
    }

    public static FeeEntity currentMonth() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.CURRENT_MONTH)
            .build();
    }

    public static FeeEntity currentMonthAlternative() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(2L)
            .date(FeeConstants.CURRENT_MONTH)
            .build();
    }

    public static FeeEntity firstNextYear() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.FIRST_NEXT_YEAR_DATE)
            .build();
    }

    public static FeeEntity lastInYear() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.LAST_YEAR_DATE)
            .build();
    }

    public static FeeEntity nextDate() {
        return FeeEntity.builder()
            .id(2L)
            .memberId(1L)
            .date(FeeConstants.NEXT_DATE)
            .build();
    }

    public static FeeEntity nextMonth() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.NEXT_MONTH)
            .build();
    }

    public static FeeEntity nextYear() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.NEXT_YEAR_MONTH)
            .build();
    }

    public static FeeEntity previousMonth() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.PREVIOUS_MONTH)
            .build();
    }

    public static FeeEntity previousYear() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.PREVIOUS_YEAR_MONTH)
            .build();
    }

    public static FeeEntity twoMonthsBack() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.TWO_MONTHS_BACK)
            .build();
    }

    public static FeeEntity twoYearsBack() {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(FeeConstants.TWO_YEARS_BACK)
            .build();
    }

    private FeeEntities() {
        super();
    }

}
