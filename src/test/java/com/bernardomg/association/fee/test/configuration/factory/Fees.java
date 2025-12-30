
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.Month;
import java.time.YearMonth;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;

public final class Fees {

    public static final Fee addPayment() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, null);
        return new Fee(FeeConstants.DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee alternative() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        profile = new Fee.Member(ProfileConstants.ALTERNATIVE_NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.ALTERNATIVE_INDEX);
        return new Fee(FeeConstants.DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee alternativeProfile() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        profile = new Fee.Member(ProfileConstants.ALTERNATIVE_NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee alternativeTransaction() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.ALTERNATIVE_INDEX);
        return new Fee(FeeConstants.DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee noLastName() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, "");
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee notPaid() {
        final Fee.Member  profile;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.DATE, profile);
    }

    public static final Fee notPaidCurrentMonth() {
        final Fee.Member  profile;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.CURRENT_MONTH, profile);
    }

    public static final Fee notPaidForMonth(final long index, final long number, final Month month) {
        final Fee.Member  profile;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(number, name);
        return Fee.unpaid(YearMonth.of(FeeConstants.YEAR_VALUE, month), profile);
    }

    public static final Fee notPaidForMonth(final long index, final Month month) {
        final Fee.Member  profile;
        final ProfileName name;

        name = new ProfileName("Profile " + index, "Last name " + index);
        // TODO: check this number, it should use a constant
        profile = new Fee.Member(index * 10, name);
        return Fee.unpaid(YearMonth.of(FeeConstants.YEAR_VALUE, month), profile);
    }

    public static final Fee notPaidNextYear() {
        final Fee.Member  profile;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.NEXT_YEAR_MONTH, profile);
    }

    public static final Fee notPaidPreviousMonth() {
        final Fee.Member  profile;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.PREVIOUS_MONTH, profile);
    }

    public static final Fee notPaidTwoMonthsBack() {
        final Fee.Member  profile;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.TWO_MONTHS_BACK, profile);
    }

    public static final Fee paid() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee paidAtDate(final Instant paymentDate) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(paymentDate, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee paidCurrentMonth() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.CURRENT_MONTH, true, profile, Optional.of(transaction));
    }

    public static final Fee paidCurrentMonth(final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.CURRENT_MONTH, true, profile, Optional.of(transaction));
    }

    public static final Fee paidFirstNextYear(final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.FIRST_NEXT_YEAR_DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee paidForMonth(final int month) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, profile, Optional.of(transaction));
    }

    public static final Fee paidForMonth(final int month, final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, profile, Optional.of(transaction));
    }

    public static final Fee paidForMonth(final long index, final Month month) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName("Profile " + index, "Last name " + index);
        profile = new Fee.Member(index * 10, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index * 10);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, profile, Optional.of(transaction));
    }

    public static final Fee paidForMonthAlternative(final int month) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        profile = new Fee.Member(ProfileConstants.ALTERNATIVE_NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month + 29);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, profile, Optional.of(transaction));
    }

    public static final Fee paidForMonthNoLastName(final int month) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, "");
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month);
        return new Fee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true, profile, Optional.of(transaction));
    }

    public static final Fee paidForMonthPreviousYear(final int month) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, (long) month);
        // TODO: don't use member calendar
        return new Fee(YearMonth.of(FeeConstants.PREVIOUS_YEAR_TO_DEFAULT.getValue(), month), true, profile,
            Optional.of(transaction));
    }

    public static final Fee paidForMonthPreviousYear(final int month, final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(YearMonth.of(FeeConstants.PREVIOUS_YEAR_TO_DEFAULT.getValue(), month), true, profile,
            Optional.of(transaction));
    }

    public static final Fee paidInFuture() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE_FUTURE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee paidLastInYear(final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.LAST_YEAR_DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee paidNextDate() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.NEXT_DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.NEXT_DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee paidNextYear(final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        // TODO: this is the third distinct date, stick to one
        transaction = new Fee.Transaction(TransactionConstants.DATE, index);
        return new Fee(FeeConstants.NEXT_YEAR_MONTH, true, profile, Optional.of(transaction));
    }

    public static final Fee paidPreviousMonth() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX);
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, profile, Optional.of(transaction));
    }

    public static final Fee paidPreviousMonth(final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, profile, Optional.of(transaction));
    }

    public static final Fee paidPreviousMonthNew() {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, TransactionConstants.INDEX_SMALL);
        return new Fee(FeeConstants.PREVIOUS_MONTH, true, profile, Optional.of(transaction));
    }

    public static final Fee paidTwoMonthsBack(final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.TWO_MONTHS_BACK, true, profile, Optional.of(transaction));
    }

    public static final Fee paidWithIndex(final long index) {
        final Fee.Member      profile;
        final Fee.Transaction transaction;
        final ProfileName     name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        transaction = new Fee.Transaction(FeeConstants.PAYMENT_DATE, index);
        return new Fee(FeeConstants.DATE, true, profile, Optional.of(transaction));
    }

    public static final Fee toCreate() {
        final Fee.Member  profile;
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        profile = new Fee.Member(ProfileConstants.NUMBER, name);
        return Fee.unpaid(FeeConstants.CURRENT_MONTH, profile);
    }

    private Fees() {
        super();
    }

}
