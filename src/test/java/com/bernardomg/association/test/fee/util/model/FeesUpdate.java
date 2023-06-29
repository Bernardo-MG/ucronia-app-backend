
package com.bernardomg.association.test.fee.util.model;

import java.util.GregorianCalendar;

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
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build();
    }

    public static final FeeUpdate missingPaid() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 1))
            .build();
    }

    public static final FeeUpdate paid() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build();
    }

    public static final FeeUpdate unpaid() {
        return ValidatedFeeUpdate.builder()
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(false)
            .build();
    }

}
