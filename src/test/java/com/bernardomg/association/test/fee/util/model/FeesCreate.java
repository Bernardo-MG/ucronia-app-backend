
package com.bernardomg.association.test.fee.util.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import com.bernardomg.association.fee.model.request.FeesPayment;
import com.bernardomg.association.fee.model.request.FeesPaymentRequest;

public final class FeesCreate {

    public static final FeesPayment duplicatedDates() {
        return FeesPaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1), new GregorianCalendar(2020, 1, 1)))
            .paymentDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeesPayment invalidId() {
        return FeesPaymentRequest.builder()
            .memberId(-1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeesPayment missingDescription() {
        return FeesPaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .amount(1F)
            .build();
    }

    public static final FeesPayment missingFeeDates() {
        return FeesPaymentRequest.builder()
            .memberId(1L)
            .paymentDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeesPayment missingMemberId() {
        return FeesPaymentRequest.builder()
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeesPayment missingPaymentDate() {
        return FeesPaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeesPayment multipleDates() {
        return FeesPaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1), new GregorianCalendar(2020, 2, 1)))
            .paymentDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeesPayment nullFeeDate() {
        final Collection<Calendar> feeDates;

        feeDates = new ArrayList<>();
        feeDates.add(null);

        return FeesPaymentRequest.builder()
            .memberId(1L)
            .feeDates(feeDates)
            .paymentDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

    public static final FeesPayment valid() {
        return FeesPaymentRequest.builder()
            .memberId(1L)
            .feeDates(List.of(new GregorianCalendar(2020, 1, 1)))
            .paymentDate(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .description("Fee paid")
            .amount(1F)
            .build();
    }

}
