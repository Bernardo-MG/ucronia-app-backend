
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendars;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.domain.model.PublicPerson;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;

public final class Fees {

    public static final Fee alternative() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.ALTERNATIVE_INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee newlyCreated() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.DATE, false, person, transaction);
    }

    public static final Fee noLastName() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, "");
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee notPaid() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.DATE, false, person, transaction);
    }

    public static final Fee notPaidAt(final long index, final Month month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName("Person " + index, "Last name " + index);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(index * 10)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), false, person, transaction);
    }

    public static final Fee notPaidCurrentMonth() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.CURRENT_MONTH, false, person, transaction);
    }

    public static final Fee notPaidNextYear() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.NEXT_YEAR_MONTH, false, person, transaction);
    }

    public static final Fee notPaidPreviousMonth() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.PREVIOUS_MONTH, false, person, transaction);
    }

    public static final Fee notPaidTwoMonthsBack() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .build();
        return new Fee(FeeConstants.TWO_MONTHS_BACK, false, person, transaction);
    }

    public static final Fee paid() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee paidAt(final int month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAt(final int month, final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAt(final long index, final Month month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName("Person " + index, "Last name " + index);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(index * 10)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index * 10)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAtAlternative(final int month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month + 29)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAtNoLastName(final int month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, "");
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex((long) month)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAtPreviousYear(final int month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
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
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(YearMonth.of(MemberCalendars.YEAR_PREVIOUS.getValue(), month), true, person, transaction);
    }

    public static final Fee paidCurrentMonth() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.CURRENT_MONTH, true, person, transaction);
    }

    public static final Fee paidCurrentMonth(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.CURRENT_MONTH, true, person, transaction);
    }

    public static final Fee paidFirstNextYear(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.FIRST_NEXT_YEAR_DATE, true, person, transaction);
    }

    public static final Fee paidLastInYear(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.LAST_YEAR_DATE, true, person, transaction);
    }

    public static final Fee paidNextDate() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.NEXT_DATE, true, person, transaction);
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.NEXT_DATE, true, person, transaction);
    }

    public static final Fee paidNextYear(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(TransactionConstants.DATE)
            .build();
        return new Fee(FeeConstants.NEXT_YEAR_MONTH, true, person, transaction);
    }

    public static final Fee paidPreviousMonth() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, transaction);
    }

    public static final Fee paidPreviousMonth(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, transaction);
    }

    public static final Fee paidPreviousMonthNew() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(TransactionConstants.INDEX_SMALL)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, transaction);
    }

    public static final Fee paidTwoMonthsBack(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.TRANSACTION_DATE)
            .build();
        return new Fee(FeeConstants.TWO_MONTHS_BACK, true, person, transaction);
    }

    public static final Fee paidWithIndex(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(index)
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee toCreate() {
        final PublicPerson person;
        final PersonName   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = PublicPerson.builder()
            .withName(name)
            .withNumber(PersonConstants.NUMBER)
            .build();
        return new Fee(FeeConstants.CURRENT_MONTH, false, person, null);
    }

    private Fees() {
        super();
    }

}
