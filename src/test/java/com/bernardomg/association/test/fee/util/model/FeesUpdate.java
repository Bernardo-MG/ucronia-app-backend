
package com.bernardomg.association.test.fee.util.model;

import java.util.GregorianCalendar;

import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.fee.model.request.ValidatedFeeUpdate;

public final class FeesUpdate {

    public static final FeeUpdate paid() {
        return ValidatedFeeUpdate.builder()
            .id(1L)
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 2))
            .paid(true)
            .build();
    }

    public static final FeeUpdate unpaid() {
        return ValidatedFeeUpdate.builder()
            .id(1L)
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 2))
            .paid(false)
            .build();
    }

}
