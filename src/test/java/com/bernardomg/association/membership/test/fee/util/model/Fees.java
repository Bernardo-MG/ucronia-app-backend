
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.membership.fee.model.Fee;

public final class Fees {

    public static final Fee noSurname() {
        return Fee.builder()
            .memberNumber(1L)
            .memberName("Member 1")
            .date(FeeConstants.DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(1L)
            .build();
    }

    public static final Fee notPaid() {
        return Fee.builder()
            .memberNumber(1L)
            .memberName("Member 1 Surname 1")
            .date(FeeConstants.DATE)
            .paid(false)
            .build();
    }

    public static final Fee notPaidAt(final long index, final Month month) {
        return Fee.builder()
            .memberNumber(index)
            .memberName("Member " + index + " Surname " + index)
            .date(YearMonth.of(FeeConstants.YEAR, month))
            .paid(false)
            .build();
    }

    public static final Fee paid() {
        return Fee.builder()
            .memberNumber(1L)
            .memberName("Member 1 Surname 1")
            .date(FeeConstants.DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(1L)
            .build();
    }

    public static final Fee paidAt(final int month) {
        return Fee.builder()
            .memberNumber(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(FeeConstants.YEAR, month))
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex((long) month)
            .build();
    }

    public static final Fee paidAt(final long index, final Month month) {
        return Fee.builder()
            .memberNumber(index)
            .memberName("Member " + index + " Surname " + index)
            .date(YearMonth.of(FeeConstants.YEAR, month))
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(index)
            .build();
    }

    public static final Fee paidNextDate() {
        return Fee.builder()
            .memberNumber(1L)
            .memberName("Member 1 Surname 1")
            .date(FeeConstants.NEXT_DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(1L)
            .build();
    }

    private Fees() {
        super();
    }

}
