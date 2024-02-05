
package com.bernardomg.association.fee.test.config.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.member.test.config.factory.MemberCalendars;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.association.transaction.test.config.factory.TransactionConstants;

public final class Fees {

    public static final Fee alternative() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName("Member 2 Surname 2")
            .withNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.ALTERNATIVE_INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.DATE)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee noSurname() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.DATE)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee notPaid() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .withDate(FeeConstants.DATE)
            .withPaid(false)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee notPaidAt(final long index, final Month month) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName("Member " + index + " Surname " + index)
            .withNumber(index * 10)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .withDate(YearMonth.of(FeeConstants.YEAR, month))
            .withPaid(false)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee notPaidCurrentMonth() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .withDate(FeeConstants.CURRENT_MONTH)
            .withPaid(false)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee notPaidNextYear() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .withDate(FeeConstants.NEXT_YEAR_MONTH)
            .withPaid(false)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee notPaidPreviousMonth() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .withDate(FeeConstants.PREVIOUS_MONTH)
            .withPaid(false)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee notPaidTwoMonthsBack() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .withDate(FeeConstants.TWO_MONTHS_BACK)
            .withPaid(false)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paid() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.DATE)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee toCreate() {
        final FeeMember      member;

        member = FeeMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.CURRENT_MONTH)
            .withPaid(false)
            .withMember(member)
            .build();
    }

    public static final Fee paidAt(final int month) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(YearMonth.of(FeeConstants.YEAR, month))
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidAt(final int month, final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(YearMonth.of(FeeConstants.YEAR, month))
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidAt(final long index, final Month month) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName("Member " + index + " Surname " + index)
            .withNumber(index * 10)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index * 10)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(YearMonth.of(FeeConstants.YEAR, month))
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidAtAlternative(final int month) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.ALTERNATIVE_FULL_NAME)
            .withNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month + 29)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(YearMonth.of(FeeConstants.YEAR, month))
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidAtNoSurname(final int month) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(YearMonth.of(FeeConstants.YEAR, month))
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidAtPreviousYear(final int month) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(YearMonth.of(MemberCalendars.YEAR_PREVIOUS, month))
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidAtPreviousYear(final int month, final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(YearMonth.of(MemberCalendars.YEAR_PREVIOUS, month))
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidCurrentMonth() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.CURRENT_MONTH)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidCurrentMonth(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.CURRENT_MONTH)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidFirstNextYear(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.FIRST_NEXT_YEAR_DATE)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidLastInYear(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.LAST_YEAR_DATE)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidNextDate() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.NEXT_DATE)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.NEXT_DATE)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidNextYear(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(TransactionConstants.DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.NEXT_YEAR_MONTH)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidPreviousMonth(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.PREVIOUS_MONTH)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidPreviousMonth() {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.PREVIOUS_MONTH)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidTwoMonthsBack(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.TWO_MONTHS_BACK)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    public static final Fee paidWithIndex(final long index) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(MemberConstants.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return Fee.builder()
            .withDate(FeeConstants.DATE)
            .withPaid(true)
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    private Fees() {
        super();
    }

}
