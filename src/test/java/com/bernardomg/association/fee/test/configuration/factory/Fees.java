
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.Month;
import java.time.YearMonth;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;

public final class Fees {

    public static final Fee addPayment() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, null);
        return new Fee(FeeConstants.DATE, true, person, Optional.of(transaction));
    }

    public static final Fee alternative() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        person = new Fee.Member(PersonConstants.ALTERNATIVE_NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.ALTERNATIVE_INDEX);
        return new Fee(FeeConstants.DATE, true, person, Optional.of(transaction));
    }

    public static final Fee alternativePerson() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        person = new Fee.Member(PersonConstants.ALTERNATIVE_NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, person, Optional.of(transaction));
    }

    public static final Fee alternativeTransaction() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.ALTERNATIVE_INDEX);
        return new Fee(FeeConstants.DATE, true, person, Optional.of(transaction));
    }

    public static final Fee noLastName() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, "");
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, person, Optional.of(transaction));
    }

    public static final Fee notPaid() {
        final Fee.Member person;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.DATE, person);
    }

    public static final Fee notPaidCurrentMonth() {
        final Fee.Member person;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.CURRENT_MONTH, person);
    }

    public static final Fee notPaidForMonth(final long index, final Month month) {
        final Fee.Member person;
        final PersonName name;

        name = new PersonName("Person " + index, "Last name " + index);
        person = new Fee.Member(index * 10, name);
        return Fee.unpaid(YearMonth.of(FeeConstants.YEAR_VALUE, month), person);
    }

    public static final Fee notPaidNextYear() {
        final Fee.Member person;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.NEXT_YEAR_MONTH, person);
    }

    public static final Fee notPaidPreviousMonth() {
        final Fee.Member person;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.PREVIOUS_MONTH, person);
    }

    public static final Fee notPaidTwoMonthsBack() {
        final Fee.Member person;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.TWO_MONTHS_BACK, person);
    }

    public static final Fee paid() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, person, Optional.of(transaction));
    }

    public static final Fee paidAtDate(final Instant paymentDate) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(paymentDate, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, person, Optional.of(transaction));
    }

    public static final Fee paidCurrentMonth() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.CURRENT_MONTH, true, person, Optional.of(transaction));
    }

    public static final Fee paidCurrentMonth(final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.CURRENT_MONTH, true, person, Optional.of(transaction));
    }

    public static final Fee paidFirstNextYear(final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.FIRST_NEXT_YEAR_DATE, true, person, Optional.of(transaction));
    }

    public static final Fee paidForMonth(final int month) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, Optional.of(transaction));
    }

    public static final Fee paidForMonth(final int month, final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, Optional.of(transaction));
    }

    public static final Fee paidForMonth(final long index, final Month month) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName("Person " + index, "Last name " + index);
        person = new Fee.Member(index * 10, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index * 10);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, Optional.of(transaction));
    }

    public static final Fee paidForMonthAlternative(final int month) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        person = new Fee.Member(PersonConstants.ALTERNATIVE_NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month + 29);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, Optional.of(transaction));
    }

    public static final Fee paidForMonthNoLastName(final int month) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, "");
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, person, Optional.of(transaction));
    }

    public static final Fee paidForMonthPreviousYear(final int month) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month);
        // TODO: don't use member calendar
        return new Fee(YearMonth.of(FeeConstants.PREVIOUS_YEAR_TO_DEFAULT.getValue(), month), true, person,
            Optional.of(transaction));
    }

    public static final Fee paidForMonthPreviousYear(final int month, final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(YearMonth.of(FeeConstants.PREVIOUS_YEAR_TO_DEFAULT.getValue(), month), true, person,
            Optional.of(transaction));
    }

    public static final Fee paidLastInYear(final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.LAST_YEAR_DATE, true, person, Optional.of(transaction));
    }

    public static final Fee paidNextDate() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.NEXT_DATE, true, person, Optional.of(transaction));
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.NEXT_DATE, true, person, Optional.of(transaction));
    }

    public static final Fee paidNextYear(final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        // TODO: this is the third distinct date, stick to one
        transaction = new Fee.Transaction(TransactionConstants.DATE, index);
        return new Fee(FeeConstants.NEXT_YEAR_MONTH, true, person, Optional.of(transaction));
    }

    public static final Fee paidPreviousMonth() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, Optional.of(transaction));
    }

    public static final Fee paidPreviousMonth(final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, Optional.of(transaction));
    }

    public static final Fee paidPreviousMonthNew() {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX_SMALL);
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, person, Optional.of(transaction));
    }

    public static final Fee paidTwoMonthsBack(final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.TWO_MONTHS_BACK, true, person, Optional.of(transaction));
    }

    public static final Fee paidWithIndex(final long index) {
        final Fee.Member      person;
        final Fee.Transaction transaction;
        final PersonName      name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.DATE, true, person, Optional.of(transaction));
    }

    public static final Fee toCreate() {
        final Fee.Member person;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new Fee.Member(PersonConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.CURRENT_MONTH, person);
    }

    private Fees() {
        super();
    }

}
