
package com.bernardomg.association.fee.usecase.validation;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.fee.domain.dto.FeePayments;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the fees payment date is not in the future.
 */
public final class FeePaymentsNotPaidInFutureRule implements FieldRule<FeePayments> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(FeePaymentsNotPaidInFutureRule.class);

    public FeePaymentsNotPaidInFutureRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final FeePayments payments) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Instant                now;

        now = Instant.now();
        if (payments.paymentDate()
            .isAfter(now)) {
            log.error("Attempting to pay fee at future date {}", payments.paymentDate());
            fieldFailure = new FieldFailure("invalid", "paymentDate", payments.paymentDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
