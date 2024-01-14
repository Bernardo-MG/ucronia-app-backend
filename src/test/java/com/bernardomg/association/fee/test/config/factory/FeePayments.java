
package com.bernardomg.association.fee.test.config.factory;

import java.util.List;

import com.bernardomg.association.fee.model.FeePayment;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class FeePayments {

    public static final FeePayment alternative() {
        return FeePayment.builder()
            .memberNumber(MemberConstants.NUMBER)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .feeDates(List.of(FeeConstants.NEXT_DATE))
            .build();
    }

    public static final FeePayment duplicated() {
        return FeePayment.builder()
            .memberNumber(MemberConstants.NUMBER)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .feeDates(List.of(FeeConstants.DATE, FeeConstants.DATE))
            .build();
    }

    public static final FeePayment single() {
        return FeePayment.builder()
            .memberNumber(MemberConstants.NUMBER)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .feeDates(List.of(FeeConstants.DATE))
            .build();
    }

    public static final FeePayment two() {
        return FeePayment.builder()
            .memberNumber(MemberConstants.NUMBER)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .feeDates(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE))
            .build();
    }

    public static final FeePayment twoYears() {
        return FeePayment.builder()
            .memberNumber(MemberConstants.NUMBER)
            .paymentDate(FeeConstants.PAYMENT_DATE)
            .feeDates(List.of(FeeConstants.LAST_YEAR_DATE, FeeConstants.FIRST_NEXT_YEAR_DATE))
            .build();
    }

    private FeePayments() {
        super();
    }

}
