
package com.bernardomg.association.test.fee.util.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import com.bernardomg.association.fee.model.request.FeePayment;
import com.bernardomg.association.fee.model.request.FeePaymentRequest;

public final class FeesCreate {

    public static final FeePayment duplicatedDates() {
        return FeePaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1), new GregorianCalendar(2020, 1, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeePayment invalidId() {
        return FeePaymentRequest.builder()
            .memberId(-1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeePayment missingDescription() {
        return FeePaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .amount(1F)
            .build();
    }

    public static final FeePayment missingFeeDates() {
        return FeePaymentRequest.builder()
            .memberId(1L)
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeePayment missingMemberId() {
        return FeePaymentRequest.builder()
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeePayment missingPaymentDate() {
        return FeePaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeePayment multipleDates() {
        return FeePaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1), new GregorianCalendar(2020, 2, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeePayment nullFeeDate() {
        final Collection<Calendar> feeDates;

        feeDates = new ArrayList<>();
        feeDates.add(null);

        return FeePaymentRequest.builder()
            .memberId(1L)
            .feeDates(feeDates)
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeePayment valid() {
        return FeePaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(new GregorianCalendar(2020, 0, 1))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

}
