
package com.bernardomg.association.membership.test.fee.config.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.funds.test.transaction.config.factory.TransactionConstants;
import com.bernardomg.association.membership.test.member.config.factory.MemberConstants;
import com.bernardomg.association.model.fee.Fee;
import com.bernardomg.association.model.fee.FeeMember;
import com.bernardomg.association.model.fee.FeeTransaction;

public final class Fees {

    public static final Fee alternative() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName("Member 2 Surname 2")
            .number(MemberConstants.ALTERNATIVE_NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .index(TransactionConstants.ALTERNATIVE_INDEX)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(FeeConstants.DATE)
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee noSurname() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName("Member 1")
            .number(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .index(TransactionConstants.INDEX)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(FeeConstants.DATE)
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee notPaid() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .date(FeeConstants.DATE)
            .paid(false)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee notPaidAt(final long index, final Month month) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName("Member " + index + " Surname " + index)
            .number(index * 10)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .date(YearMonth.of(FeeConstants.YEAR, month))
            .paid(false)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee paid() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .index(TransactionConstants.INDEX)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(FeeConstants.DATE)
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee paidAt(final int month) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .index((long) month)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(YearMonth.of(FeeConstants.YEAR, month))
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee paidAt(final long index, final Month month) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName("Member " + index + " Surname " + index)
            .number(index * 10)
            .build();
        transaction = FeeTransaction.builder()
            .index(index * 10)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(YearMonth.of(FeeConstants.YEAR, month))
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee paidFirstNextYear(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .index(index)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(FeeConstants.FIRST_NEXT_YEAR_DATE)
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee paidLastInYear(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .index(index)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(FeeConstants.LAST_YEAR_DATE)
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee paidNextDate() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .index(TransactionConstants.INDEX)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(FeeConstants.NEXT_DATE)
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .index(index)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(FeeConstants.NEXT_DATE)
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    public static final Fee paidWithIndex(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(MemberConstants.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .index(index)
            .date(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .date(FeeConstants.DATE)
            .paid(true)
            .member(member)
            .transaction(transaction)
            .build();
    }

    private Fees() {
        super();
    }

}
