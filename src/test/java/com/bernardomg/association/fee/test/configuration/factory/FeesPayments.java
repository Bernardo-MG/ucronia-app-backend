
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.fee.domain.dto.FeePayments;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class FeesPayments {

    public static final FeePayments duplicated() {
        return new FeePayments(FeeConstants.FEE_TYPE, ProfileConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.DATE));
    }

    public static final FeePayments empty() {
        return new FeePayments(FeeConstants.FEE_TYPE, ProfileConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of());
    }

    public static final FeePayments multiple() {
        return new FeePayments(FeeConstants.FEE_TYPE, ProfileConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.DATE.plusMonths(1)));
    }

    public static final FeePayments paidFuture() {
        return new FeePayments(FeeConstants.FEE_TYPE, ProfileConstants.NUMBER, FeeConstants.PAYMENT_DATE_FUTURE,
            List.of(FeeConstants.DATE));
    }

    public static final FeePayments single() {
        return new FeePayments(FeeConstants.FEE_TYPE, ProfileConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE));
    }

    private FeesPayments() {
        super();
    }

}
