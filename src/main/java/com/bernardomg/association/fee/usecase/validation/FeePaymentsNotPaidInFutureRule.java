
package com.bernardomg.association.fee.usecase.validation;

import java.time.Instant;
import java.util.Optional;

import com.bernardomg.association.fee.domain.dto.FeePayments;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the fees are not paid in the future.
 */
public final class FeePaymentsNotPaidInFutureRule implements FieldRule<FeePayments> {

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
            fieldFailure = new FieldFailure("invalid", "paymentDate", payments.paymentDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
