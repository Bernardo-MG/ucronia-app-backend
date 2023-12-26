
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;

public final class FeeEntities {

    public static FeeEntity notPaidAt(final Month month) {
        return FeeEntity.builder()
            .id(1L)
            .memberId(1L)
            .date(YearMonth.of(2020, month))
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
            .date(YearMonth.of(2020, month))
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

    private FeeEntities() {
        super();
    }

}
