
package com.bernardomg.association.membership.test.fee.util.model;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.membership.fee.model.MemberFee;

public final class MemberFees {

    public static final MemberFee noSurname() {
        return MemberFee.builder()
            .memberId(1L)
            .memberName("Member 1")
            .date(Fees.DATE)
            .paid(true)
            .build();
    }

    public static final MemberFee notPaidAt(final long index, final Month month) {
        return MemberFee.builder()
            .memberId(index)
            .memberName("Member " + index + " Surname " + index)
            .date(YearMonth.of(2020, month))
            .paid(false)
            .build();
    }

    public static final MemberFee paid() {
        return MemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(Fees.DATE)
            .paid(true)
            .build();
    }

    public static final MemberFee paidAt(final long index, final Month month) {
        return MemberFee.builder()
            .memberId(index)
            .memberName("Member " + index + " Surname " + index)
            .date(YearMonth.of(2020, month))
            .paid(true)
            .build();
    }

    public static final MemberFee paidAt(final Month month) {
        return MemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, month))
            .paid(true)
            .build();
    }

    private MemberFees() {
        super();
    }

}
