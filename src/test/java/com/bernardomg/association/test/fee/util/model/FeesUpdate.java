
package com.bernardomg.association.test.fee.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.fee.model.request.ValidatedFeeUpdate;

public final class FeesUpdate {

    public static final FeeUpdate missingDate() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .paid(true)
            .build();
    }

    public static final FeeUpdate missingMemberId() {
        return ValidatedFeeUpdate.builder()
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(true)
            .build();
    }

    public static final FeeUpdate missingPaid() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .build();
    }

    public static final FeeUpdate paid() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(true)
            .build();
    }

    public static final FeeUpdate unpaid() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(false)
            .build();
    }

}
