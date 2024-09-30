
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeePerson;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendars;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;

public final class Fees {

    public static final Fee alternative() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName("Person 2 Last name 2")
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.ALTERNATIVE_INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee newlyCreated() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.DATE, false, person, transaction);
    }

    public static final Fee noLastName() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee notPaid() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.DATE, false, person, transaction);
    }

    public static final Fee notPaidAt(final long index, final Month month) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName("Person " + index + " Last name " + index)
            .withNumber(index * 10)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), false, person, transaction);
    }

    public static final Fee notPaidCurrentMonth() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.CURRENT_MONTH, false, person, transaction);
    }

    public static final Fee notPaidNextYear() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.NEXT_YEAR_MONTH, false, person, transaction);
    }

    public static final Fee notPaidPreviousMonth() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.PREVIOUS_MONTH, false, person, transaction);
    }

    public static final Fee notPaidTwoMonthsBack() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.TWO_MONTHS_BACK, false, person, transaction);
    }

    public static final Fee paid() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee paidAt(final int month) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAt(final int month, final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAt(final long index, final Month month) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName("Person " + index + " Last name " + index)
            .withNumber(index * 10)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index * 10)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAtAlternative(final int month) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.ALTERNATIVE_FULL_NAME)
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month + 29)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAtNoLastName(final int month) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAtPreviousYear(final int month) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        // TODO: don't use member calendar
        return new Fee(YearMonth.of(MemberCalendars.YEAR_PREVIOUS.getValue(), month), true, person, transaction);
    }

    public static final Fee paidAtPreviousYear(final int month, final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(MemberCalendars.YEAR_PREVIOUS.getValue(), month), true, person, transaction);
    }

    public static final Fee paidCurrentMonth() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.CURRENT_MONTH, true, person, transaction);
    }

    public static final Fee paidCurrentMonth(final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.CURRENT_MONTH, true, person, transaction);
    }

    public static final Fee paidFirstNextYear(final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.FIRST_NEXT_YEAR_DATE, true, person, transaction);
    }

    public static final Fee paidLastInYear(final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.LAST_YEAR_DATE, true, person, transaction);
    }

    public static final Fee paidNextDate() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.NEXT_DATE, true, person, transaction);
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.NEXT_DATE, true, person, transaction);
    }

    public static final Fee paidNextYear(final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(TransactionConstants.DATE)
            .build();
        return new Fee(FeeConstants.NEXT_YEAR_MONTH, true, person, transaction);
    }

    public static final Fee paidPreviousMonth() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, transaction);
    }

    public static final Fee paidPreviousMonth(final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, transaction);
    }

    public static final Fee paidPreviousMonthNew() {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX_SMALL)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, transaction);
    }

    public static final Fee paidTwoMonthsBack(final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.TWO_MONTHS_BACK, true, person, transaction);
    }

    public static final Fee paidWithIndex(final long index) {
        final FeePerson      person;
        final FeeTransaction transaction;

        person = FeePerson.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee toCreate() {
        final FeePerson person;

        person = FeePerson.builder()
            .withNumber(PersonConstants.NUMBER)
            .build();
        return new Fee(FeeConstants.CURRENT_MONTH, false, person, null);
    }

    private Fees() {
        super();
    }

}
