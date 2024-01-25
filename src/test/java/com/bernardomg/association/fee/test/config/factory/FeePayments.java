
package com.bernardomg.association.fee.test.config.factory;

import java.util.List;

import com.bernardomg.association.fee.domain.model.FeePayment;
import com.bernardomg.association.fee.domain.model.FeePaymentMember;
import com.bernardomg.association.fee.domain.model.FeePaymentTransaction;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class FeePayments {

    public static final FeePayment alternative() {
        final FeePaymentMember      member;
        final FeePaymentTransaction transaction;

        member = FeePaymentMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeePaymentTransaction.builder()
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return FeePayment.builder()
            .withMember(member)
            .withTransaction(transaction)
            .withFeeDates(List.of(FeeConstants.NEXT_DATE))
            .build();
    }

    public static final FeePayment duplicated() {
        final FeePaymentMember      member;
        final FeePaymentTransaction transaction;

        member = FeePaymentMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeePaymentTransaction.builder()
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return FeePayment.builder()
            .withMember(member)
            .withTransaction(transaction)
            .withFeeDates(List.of(FeeConstants.DATE, FeeConstants.DATE))
            .build();
    }

    public static final FeePayment single() {
        final FeePaymentMember      member;
        final FeePaymentTransaction transaction;

        member = FeePaymentMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeePaymentTransaction.builder()
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return FeePayment.builder()
            .withMember(member)
            .withTransaction(transaction)
            .withFeeDates(List.of(FeeConstants.DATE))
            .build();
    }

    public static final FeePayment two() {
        final FeePaymentMember      member;
        final FeePaymentTransaction transaction;

        member = FeePaymentMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeePaymentTransaction.builder()
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return FeePayment.builder()
            .withMember(member)
            .withTransaction(transaction)
            .withFeeDates(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE))
            .build();
    }

    public static final FeePayment twoYears() {
        final FeePaymentMember      member;
        final FeePaymentTransaction transaction;

        member = FeePaymentMember.builder()
            .withNumber(MemberConstants.NUMBER)
            .build();
        transaction = FeePaymentTransaction.builder()
            .withDate(FeeConstants.PAYMENT_DATE)
            .build();
        return FeePayment.builder()
            .withMember(member)
            .withTransaction(transaction)
            .withFeeDates(List.of(FeeConstants.LAST_YEAR_DATE, FeeConstants.FIRST_NEXT_YEAR_DATE))
            .build();
    }

    private FeePayments() {
        super();
    }

}
