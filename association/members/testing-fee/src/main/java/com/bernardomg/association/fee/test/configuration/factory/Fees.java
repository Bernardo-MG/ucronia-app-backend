
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;

public final class Fees {

    public static final Fee addPayment() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(null, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee alternative() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.ALTERNATIVE_FIRST_NAME, MemberConstants.ALTERNATIVE_LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.ALTERNATIVE_INDEX, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.ALTERNATIVE_NUMBER, name, feeType, transaction);
    }

    public static final Fee alternativeFeeType() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_ALTERNATIVE_NUMBER, FeeConstants.FEE_TYPE_NAME,
            FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee alternativeProfile() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.ALTERNATIVE_FIRST_NAME, MemberConstants.ALTERNATIVE_LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.ALTERNATIVE_NUMBER, name, feeType, transaction);
    }

    public static final Fee alternativeTransaction() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.ALTERNATIVE_INDEX, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee noLastName() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, "");
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee notPaid() {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        return Fee.unpaid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType);
    }

    public static final Fee notPaidCurrentMonth() {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        return Fee.unpaid(FeeConstants.CURRENT_MONTH, MemberConstants.NUMBER, name, feeType);
    }

    public static final Fee notPaidForMonth(final long index, final long number, final Month month) {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        return Fee.unpaid(YearMonth.of(FeeConstants.YEAR_VALUE, month), number, name, feeType);
    }

    public static final Fee notPaidForMonth(final long index, final Month month) {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName("Profile " + index, "Last name " + index);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        return Fee.unpaid(YearMonth.of(FeeConstants.YEAR_VALUE, month), index * 10, name, feeType);
    }

    public static final Fee notPaidNextYear() {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        return Fee.unpaid(FeeConstants.NEXT_YEAR_MONTH, MemberConstants.NUMBER, name, feeType);
    }

    public static final Fee notPaidPreviousMonth() {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        return Fee.unpaid(FeeConstants.PREVIOUS_MONTH, MemberConstants.NUMBER, name, feeType);
    }

    public static final Fee notPaidTwoMonthsBack() {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        return Fee.unpaid(FeeConstants.TWO_MONTHS_BACK, MemberConstants.NUMBER, name, feeType);
    }

    public static final Fee paid() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidAtDate(final Instant paymentDate) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX, paymentDate);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidCurrentMonth() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.CURRENT_MONTH, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidCurrentMonth(final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.CURRENT_MONTH, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidFirstNextYear(final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.FIRST_NEXT_YEAR_DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidForMonth(final int month) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction((long) month, FeeConstants.PAYMENT_DATE);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), MemberConstants.NUMBER, name, feeType,
            transaction);
    }

    public static final Fee paidForMonth(final int month, final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index, FeeConstants.PAYMENT_DATE);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), MemberConstants.NUMBER, name, feeType,
            transaction);
    }

    public static final Fee paidForMonth(final long index, final Month month) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName("Profile " + index, "Last name " + index);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index * 10, FeeConstants.PAYMENT_DATE);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), index * 10, name, feeType, transaction);
    }

    public static final Fee paidForMonthAlternative(final int month) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.ALTERNATIVE_FIRST_NAME, MemberConstants.ALTERNATIVE_LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction((long) month + 29, FeeConstants.PAYMENT_DATE);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), MemberConstants.ALTERNATIVE_NUMBER, name, feeType,
            transaction);
    }

    public static final Fee paidForMonthNoLastName(final int month) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, "");
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction((long) month, FeeConstants.PAYMENT_DATE);
        return Fee.paid(YearMonth.of(FeeConstants.YEAR_VALUE, month), MemberConstants.NUMBER, name, feeType,
            transaction);
    }

    public static final Fee paidForMonthPreviousYear(final int month) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction((long) month, FeeConstants.PAYMENT_DATE);
        return Fee.paid(YearMonth.of(FeeConstants.PREVIOUS_YEAR_TO_DEFAULT.getValue(), month), MemberConstants.NUMBER,
            name, feeType, transaction);
    }

    public static final Fee paidForMonthPreviousYear(final int month, final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index, FeeConstants.PAYMENT_DATE);
        return Fee.paid(YearMonth.of(FeeConstants.PREVIOUS_YEAR_TO_DEFAULT.getValue(), month), MemberConstants.NUMBER,
            name, feeType, transaction);
    }

    public static final Fee paidInFuture() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX, FeeConstants.PAYMENT_DATE_FUTURE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidLastInYear(final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.LAST_YEAR_DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidNextDate() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.NEXT_DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidNextDateWithIndex(final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.NEXT_DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidNextYear(final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        // TODO: this is the third distinct date, stick to one
        transaction = new Fee.Transaction(index, TransactionConstants.DATE);
        return Fee.paid(FeeConstants.NEXT_YEAR_MONTH, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidNoTransaction() {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType);
    }

    public static final Fee paidNoTransactionNoAmount() {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, 0F);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType);
    }

    public static final Fee paidNoTransactionCurrentMonthNoAmount() {
        final FeeType              feeType;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, 0F);
        return Fee.paid(FeeConstants.CURRENT_MONTH, MemberConstants.NUMBER, name, feeType);
    }

    public static final Fee paidPreviousMonth() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.PREVIOUS_MONTH, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidPreviousMonth(final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.PREVIOUS_MONTH, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidPreviousMonthNew() {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(TransactionConstants.INDEX_SMALL, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.PREVIOUS_MONTH, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidTwoMonthsBack(final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.TWO_MONTHS_BACK, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee paidWithIndex(final long index) {
        final FeeType              feeType;
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        feeType = new FeeType(FeeConstants.FEE_TYPE_NUMBER, FeeConstants.FEE_TYPE_NAME, FeeConstants.FEE_TYPE_AMOUNT);
        transaction = new Fee.Transaction(index, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, feeType, transaction);
    }

    public static final Fee updatePaid() {
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        transaction = new Fee.Transaction(null, FeeConstants.PAYMENT_DATE);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, null, transaction);
    }

    public static final Fee updatePaidAtDate(final Instant paymentDate) {
        final Fee.Transaction      transaction;
        final FeeMember.MemberName name;

        name = new FeeMember.MemberName(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        transaction = new Fee.Transaction(null, paymentDate);
        return Fee.paid(FeeConstants.DATE, MemberConstants.NUMBER, name, null, transaction);
    }

    private Fees() {
        super();
    }

}
