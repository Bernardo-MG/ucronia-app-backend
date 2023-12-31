
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.membership.fee.model.MemberFee;

public final class MemberFees {

    public static final MemberFee noSurname() {
        return MemberFee.builder()
            .memberNumber(1L)
            .memberName("Member 1")
            .date(Fees.DATE)
            .paid(true)
            .paymentDate(Fees.PAYMENT_DATE)
            .build();
    }

    public static final MemberFee notPaid() {
        return MemberFee.builder()
            .memberNumber(1L)
            .memberName("Member 1 Surname 1")
            .date(Fees.DATE)
            .paid(false)
            .build();
    }

    public static final MemberFee notPaidAt(final long index, final Month month) {
        return MemberFee.builder()
            .memberNumber(index)
            .memberName("Member " + index + " Surname " + index)
            .date(YearMonth.of(Fees.YEAR, month))
            .paid(false)
            .build();
    }

    public static final MemberFee paid() {
        return MemberFee.builder()
            .memberNumber(1L)
            .memberName("Member 1 Surname 1")
            .date(Fees.DATE)
            .paid(true)
            .paymentDate(Fees.PAYMENT_DATE)
            .build();
    }

    public static final MemberFee paidAt(final long index, final Month month) {
        return MemberFee.builder()
            .memberNumber(index)
            .memberName("Member " + index + " Surname " + index)
            .date(YearMonth.of(Fees.YEAR, month))
            .paid(true)
            .paymentDate(Fees.PAYMENT_DATE)
            .build();
    }

    public static final MemberFee paidAt(final Month month) {
        return MemberFee.builder()
            .memberNumber(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(Fees.YEAR, month))
            .paid(true)
            .paymentDate(Fees.PAYMENT_DATE)
            .build();
    }

    public static final MemberFee paidNextDate() {
        return MemberFee.builder()
            .memberNumber(1L)
            .memberName("Member 1 Surname 1")
            .date(Fees.NEXT_DATE)
            .paid(true)
            .paymentDate(Fees.PAYMENT_DATE)
            .build();
    }

    private MemberFees() {
        super();
    }

}
