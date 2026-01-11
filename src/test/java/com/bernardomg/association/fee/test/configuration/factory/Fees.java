
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;

public final class Fees {

    public static final Fee addPayment() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, null);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee alternative() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.ALTERNATIVE_INDEX);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.ALTERNATIVE_NUMBER, name, feeType, transaction);
    }

    public static final Fee alternativeProfile() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.ALTERNATIVE_NUMBER, name, feeType, transaction);
    }

    public static final Fee alternativeTransaction() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.ALTERNATIVE_INDEX);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee noLastName() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, "");
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee notPaid() {
        final Fee.FeeType feeType;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        return Fee.unpaid(FeeConstants.DATE, ProfileConstants.NUMBER, name, feeType);
    }

    public static final Fee notPaidCurrentMonth() {
        final Fee.FeeType feeType;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        return Fee.unpaid(FeeConstants.CURRENT_MONTH, ProfileConstants.NUMBER, name, feeType);
    }

    public static final Fee notPaidForMonth(final long index, final long number, final Month month) {
        final Fee.FeeType feeType;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        return Fee.unpaid(YearMonth.of(FeeConstants.YEAR_VALUE, month), number, name, feeType);
    }

    public static final Fee notPaidForMonth(final long index, final Month month) {
        final Fee.FeeType feeType;
        final ProfileName name;

        name = new ProfileName("Profile " + index, "Last name " + index);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        // TODO: check this number, it should use a constant
        return Fee.unpaid(YearMonth.of(FeeConstants.YEAR_VALUE, month), index * 10, name, feeType);
    }

    public static final Fee notPaidNextYear() {
        final Fee.FeeType feeType;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        return Fee.unpaid(FeeConstants.NEXT_YEAR_MONTH, ProfileConstants.NUMBER, name, feeType);
    }

    public static final Fee notPaidPreviousMonth() {
        final Fee.FeeType feeType;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        return Fee.unpaid(FeeConstants.PREVIOUS_MONTH, ProfileConstants.NUMBER, name, feeType);
    }

    public static final Fee notPaidTwoMonthsBack() {
        final Fee.FeeType feeType;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        return Fee.unpaid(FeeConstants.TWO_MONTHS_BACK, ProfileConstants.NUMBER, name, feeType);
    }

    public static final Fee paid() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidAtDate(final Instant paymentDate) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(paymentDate, TransactionConstants.INDEX);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidCurrentMonth() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return Fee.paid(FeeConstants.CURRENT_MONTH, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidCurrentMonth(final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return Fee.paid(FeeConstants.CURRENT_MONTH, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidFirstNextYear(final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return Fee.paid(FeeConstants.FIRST_NEXT_YEAR_DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidForMonth(final int month) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), ProfileConstants.NUMBER, name, feeType,
            transaction);
    }

    public static final Fee paidForMonth(final int month, final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), ProfileConstants.NUMBER, name, feeType,
            transaction);
    }

    public static final Fee paidForMonth(final long index, final Month month) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName("Profile " + index, "Last name " + index);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index * 10);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), index * 10, name, feeType, transaction);
    }

    public static final Fee paidForMonthAlternative(final int month) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month + 29);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), ProfileConstants.ALTERNATIVE_NUMBER, name,
            feeType, transaction);
    }

    public static final Fee paidForMonthNoLastName(final int month) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, "");
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), ProfileConstants.NUMBER, name, feeType,
            transaction);
    }

    public static final Fee paidForMonthPreviousYear(final int month) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month);
        // TODO: don't use member calendar
        return Fee.paid(YearMonth.of(FeeConstants.PREVIOUS_YEAR_TO_DEFAULT.getValue(), month), ProfileConstants.NUMBER,
            name, feeType, transaction);
    }

    public static final Fee paidForMonthPreviousYear(final int month, final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return Fee.paid(YearMonth.of(FeeConstants.PREVIOUS_YEAR_TO_DEFAULT.getValue(), month), ProfileConstants.NUMBER,
            name, feeType, transaction);
    }

    public static final Fee paidInFuture() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE_FUTURE, TransactionConstants.INDEX);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidLastInYear(final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return Fee.paid(FeeConstants.LAST_YEAR_DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidNextDate() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return Fee.paid(FeeConstants.NEXT_DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return Fee.paid(FeeConstants.NEXT_DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidNextYear(final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        // TODO: this is the third distinct date, stick to one
        transaction = new Fee.Transaction(TransactionConstants.DATE, index);
        return Fee.paid(FeeConstants.NEXT_YEAR_MONTH, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidNoTransaction() {
        final Fee.FeeType feeType;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.NUMBER, name, feeType);
    }

    public static final Fee paidPreviousMonth() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return Fee.paid(FeeConstants.PREVIOUS_MONTH, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidPreviousMonth(final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return Fee.paid(FeeConstants.PREVIOUS_MONTH, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidPreviousMonthNew() {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX_SMALL);
        return Fee.paid(FeeConstants.PREVIOUS_MONTH, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidTwoMonthsBack(final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return Fee.paid(FeeConstants.TWO_MONTHS_BACK, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidWithIndex(final long index) {
        final Fee.FeeType     feeType;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return Fee.paid(FeeConstants.DATE, ProfileConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee toCreate() {
        final Fee.FeeType feeType;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        feeType = new Fee.FeeType(FeeConstants.FEE_TYPE_NUMBER);
        return Fee.unpaid(FeeConstants.CURRENT_MONTH, ProfileConstants.NUMBER, name, feeType);
    }

    private Fees() {
        super();
    }

}
