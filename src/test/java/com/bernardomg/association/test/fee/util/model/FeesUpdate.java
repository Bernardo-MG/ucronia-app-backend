
package com.bernardomg.association.test.fee.util.model;

import java.time.LocalDateTime;
import java.time.Month;

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
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .paid(true)
            .build();
    }

    public static final FeeUpdate missingPaid() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .build();
    }

    public static final FeeUpdate paid() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .paid(true)
            .build();
    }

    public static final FeeUpdate unpaid() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .paid(false)
            .build();
    }

}
