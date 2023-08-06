
package com.bernardomg.association.test.fee.util.model;

import java.util.GregorianCalendar;
import java.util.List;

import com.bernardomg.association.fee.model.request.FeeCreate;
import com.bernardomg.association.fee.model.request.FeeCreateRequest;

public final class FeesCreate {

    public static final FeeCreate invalidId() {
        return FeeCreateRequest.builder()
            .memberId(-1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeeCreate missingDescription() {
        return FeeCreateRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .amount(1F)
            .build();
    }

    public static final FeeCreate missingFeeDates() {
        return FeeCreateRequest.builder()
            .memberId(1L)
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeeCreate missingMemberId() {
        return FeeCreateRequest.builder()
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeeCreate missingPaymentDate() {
        return FeeCreateRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeeCreate valid() {
        return FeeCreateRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

}
