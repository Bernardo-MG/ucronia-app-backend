
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.fee.domain.dto.FeePayments;

public final class FeesPayments {

    public static final FeePayments duplicated() {
        return new FeePayments(ContactConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.DATE));
    }

    public static final FeePayments empty() {
        return new FeePayments(ContactConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of());
    }

    public static final FeePayments multiple() {
        return new FeePayments(ContactConstants.NUMBER, FeeConstants.PAYMENT_DATE,
            List.of(FeeConstants.DATE, FeeConstants.DATE.plusMonths(1)));
    }

    public static final FeePayments paidFuture() {
        return new FeePayments(ContactConstants.NUMBER, FeeConstants.PAYMENT_DATE_FUTURE, List.of(FeeConstants.DATE));
    }

    public static final FeePayments single() {
        return new FeePayments(ContactConstants.NUMBER, FeeConstants.PAYMENT_DATE, List.of(FeeConstants.DATE));
    }

    private FeesPayments() {
        super();
    }

}
