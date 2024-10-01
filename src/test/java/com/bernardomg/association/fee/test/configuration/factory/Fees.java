
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
        person = new PublicPerson(PersonConstants.ALTERNATIVE_NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, TransactionConstants.ALTERNATIVE_INDEX);
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee newlyCreated() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(null, null);
        return new Fee(FeeConstants.DATE, false, person, transaction);
    }

    public static final Fee noLastName() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, "");
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee notPaid() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(null, null);
        return new Fee(FeeConstants.DATE, false, person, transaction);
    }

    public static final Fee notPaidAt(final long index, final Month month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName("Person " + index, "Last name " + index);
        person = new PublicPerson(index * 10, name);
        transaction = new FeeTransaction(null, null);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), false, person, transaction);
    }

    public static final Fee notPaidCurrentMonth() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(null, null);
        return new Fee(FeeConstants.CURRENT_MONTH, false, person, transaction);
    }

    public static final Fee notPaidNextYear() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(null, null);
        return new Fee(FeeConstants.NEXT_YEAR_MONTH, false, person, transaction);
    }

    public static final Fee notPaidPreviousMonth() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(null, null);
        return new Fee(FeeConstants.PREVIOUS_MONTH, false, person, transaction);
    }

    public static final Fee notPaidTwoMonthsBack() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(null, null);
        return new Fee(FeeConstants.TWO_MONTHS_BACK, false, person, transaction);
    }

    public static final Fee paid() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee paidAt(final int month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, (long) month);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAt(final int month, final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAt(final long index, final Month month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName("Person " + index, "Last name " + index);
        person = new PublicPerson(index * 10, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, index * 10);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAtAlternative(final int month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        person = new PublicPerson(PersonConstants.ALTERNATIVE_NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, (long) month + 29);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAtNoLastName(final int month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, "");
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, (long) month);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, transaction);
    }

    public static final Fee paidAtPreviousYear(final int month) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, (long) month);
        // TODO: don't use member calendar
        return new Fee(YearMonth.of(MemberCalendars.YEAR_PREVIOUS.getValue(), month), true, person, transaction);
    }

    public static final Fee paidAtPreviousYear(final int month, final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(YearMonth.of(MemberCalendars.YEAR_PREVIOUS.getValue(), month), true, person, transaction);
    }

    public static final Fee paidCurrentMonth() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.TRANSACTION_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.CURRENT_MONTH, true, person, transaction);
    }

    public static final Fee paidCurrentMonth(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.TRANSACTION_DATE, index);
        return new Fee(FeeConstants.CURRENT_MONTH, true, person, transaction);
    }

    public static final Fee paidFirstNextYear(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.FIRST_NEXT_YEAR_DATE, true, person, transaction);
    }

    public static final Fee paidLastInYear(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.LAST_YEAR_DATE, true, person, transaction);
    }

    public static final Fee paidNextDate() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.NEXT_DATE, true, person, transaction);
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.NEXT_DATE, true, person, transaction);
    }

    public static final Fee paidNextYear(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        // TODO: this is the third distinct date, stick to one
        transaction = new FeeTransaction(TransactionConstants.DATE, index);
        return new Fee(FeeConstants.NEXT_YEAR_MONTH, true, person, transaction);
    }

    public static final Fee paidPreviousMonth() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.TRANSACTION_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, transaction);
    }

    public static final Fee paidPreviousMonth(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.TRANSACTION_DATE, index);
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, transaction);
    }

    public static final Fee paidPreviousMonthNew() {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.TRANSACTION_DATE, TransactionConstants.INDEX_SMALL);
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, transaction);
    }

    public static final Fee paidTwoMonthsBack(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.TRANSACTION_DATE, index);
        return new Fee(FeeConstants.TWO_MONTHS_BACK, true, person, transaction);
    }

    public static final Fee paidWithIndex(final long index) {
        final PublicPerson   person;
        final FeeTransaction transaction;
        final PersonName     name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        transaction = new FeeTransaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.DATE, true, person, transaction);
    }

    public static final Fee toCreate() {
        final PublicPerson person;
        final PersonName   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new PublicPerson(PersonConstants.NUMBER, name);
        return new Fee(FeeConstants.CURRENT_MONTH, false, person, null);
    }

    private Fees() {
        super();
    }

}
