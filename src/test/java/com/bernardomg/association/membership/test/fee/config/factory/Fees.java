
package com.bernardomg.association.membership.test.fee.config.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.funds.test.transaction.config.factory.TransactionConstants;
import com.bernardomg.association.membership.fee.model.Fee;
import com.bernardomg.association.membership.test.member.config.factory.MemberConstants;

public final class Fees {

    public static final Fee alternative() {
        return Fee.builder()
            .memberNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .fullName("Member 2 Surname 2")
            .date(FeeConstants.DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(TransactionConstants.ALTERNATIVE_INDEX)
            .build();
    }

    public static final Fee noSurname() {
        return Fee.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName("Member 1")
            .date(FeeConstants.DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(TransactionConstants.INDEX)
            .build();
    }

    public static final Fee notPaid() {
        return Fee.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName("Member 1 Surname 1")
            .date(FeeConstants.DATE)
            .paid(false)
            .build();
    }

    public static final Fee notPaidAt(final long index, final Month month) {
        return Fee.builder()
            .memberNumber(index * 10)
            .fullName("Member " + index + " Surname " + index)
            .date(YearMonth.of(FeeConstants.YEAR, month))
            .paid(false)
            .build();
    }

    public static final Fee paid() {
        return Fee.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName("Member 1 Surname 1")
            .date(FeeConstants.DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(TransactionConstants.INDEX)
            .build();
    }

    public static final Fee paidAt(final int month) {
        return Fee.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName("Member 1 Surname 1")
            .date(YearMonth.of(FeeConstants.YEAR, month))
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex((long) month)
            .build();
    }

    public static final Fee paidAt(final long index, final Month month) {
        return Fee.builder()
            .memberNumber(index * 10)
            .fullName("Member " + index + " Surname " + index)
            .date(YearMonth.of(FeeConstants.YEAR, month))
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(index * 10)
            .build();
    }

    public static final Fee paidFirstNextYear(final long index) {
        return Fee.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName("Member 1 Surname 1")
            .date(FeeConstants.FIRST_NEXT_YEAR_DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(index)
            .build();
    }

    public static final Fee paidLastInYear(final long index) {
        return Fee.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName("Member 1 Surname 1")
            .date(FeeConstants.LAST_YEAR_DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(index)
            .build();
    }

    public static final Fee paidNextDate() {
        return Fee.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName("Member 1 Surname 1")
            .date(FeeConstants.NEXT_DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(TransactionConstants.INDEX)
            .build();
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        return Fee.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName("Member 1 Surname 1")
            .date(FeeConstants.NEXT_DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(index)
            .build();
    }

    public static final Fee paidWithIndex(final long index) {
        return Fee.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName("Member 1 Surname 1")
            .date(FeeConstants.DATE)
            .paid(true)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .transactionIndex(index)
            .build();
    }

    private Fees() {
        super();
    }

}