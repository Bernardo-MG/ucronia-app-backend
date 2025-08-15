
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.Instant;
import java.time.YearMonth;
import java.util.Collection;

/**
 * TODO: rename feeMonths to months
 */
public final record FeePayments(FeePaymentPerson person, FeePaymentPayment payment, Collection<YearMonth> feeMonths) {

    public static final record FeePaymentPayment(Instant date) {

    }

    public static final record FeePaymentPerson(Long number) {

    }

}
