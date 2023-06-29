
package com.bernardomg.association.test.fee.util.model;

import java.util.GregorianCalendar;

import com.bernardomg.association.fee.model.request.FeeCreate;
import com.bernardomg.association.fee.model.request.ValidatedFeeCreate;

public final class FeesCreate {

    public static final FeeCreate invalidId() {
        return ValidatedFeeCreate.builder()
            .memberId(-1L)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build();
    }

    public static final FeeCreate missingDate() {
        return ValidatedFeeCreate.builder()
            .memberId(1L)
            .paid(true)
            .build();
    }

    public static final FeeCreate missingMemberId() {
        return ValidatedFeeCreate.builder()
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build();
    }

    public static final FeeCreate missingPaid() {
        return ValidatedFeeCreate.builder()
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 1))
            .build();
    }

    public static final FeeCreate paid() {
        return ValidatedFeeCreate.builder()
            .memberId(1L)
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build();
    }

}
