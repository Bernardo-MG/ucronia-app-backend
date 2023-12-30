
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.membership.fee.model.request.FeeUpdate;
import com.bernardomg.association.membership.fee.model.request.FeeUpdateRequest;

public final class FeesUpdate {

    public static final FeeUpdate missingDate() {
        return FeeUpdateRequest.builder()
            .memberId(1L)
            .paid(true)
            .build();
    }

    public static final FeeUpdate missingMemberId() {
        return FeeUpdateRequest.builder()
            .date(YearMonth.of(Fees.YEAR, Month.FEBRUARY))
            .paid(true)
            .build();
    }

    public static final FeeUpdate missingPaid() {
        return FeeUpdateRequest.builder()
            .memberId(1L)
            .date(YearMonth.of(Fees.YEAR, Month.FEBRUARY))
            .build();
    }

    public static final FeeUpdate notPaid() {
        return FeeUpdateRequest.builder()
            .memberId(1L)
            .date(YearMonth.of(Fees.YEAR, Month.FEBRUARY))
            .paid(false)
            .build();
    }

    public static final FeeUpdate paid() {
        return FeeUpdateRequest.builder()
            .memberId(1L)
            .date(YearMonth.of(Fees.YEAR, Month.FEBRUARY))
            .paid(true)
            .build();
    }

}
