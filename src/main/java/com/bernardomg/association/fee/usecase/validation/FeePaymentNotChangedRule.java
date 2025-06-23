
package com.bernardomg.association.fee.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the fee's payment was not changed or removed.
 */
public final class FeePaymentNotChangedRule implements FieldRule<Fee> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(FeePaymentNotChangedRule.class);

    private final FeeRepository feeRepository;

    public FeePaymentNotChangedRule(final FeeRepository feeRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Fee fee) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Fee                    existing;

        existing = feeRepository.findOne(fee.person()
            .number(), fee.month())
            .get();
        if (wasRemoved(fee, existing)) {
            log.error("Removed payment from fee");
            fieldFailure = new FieldFailure("removed", "payment", null);
            failure = Optional.of(fieldFailure);
        } else if (hasPayment(fee, existing) && wasChanged(fee, existing)) {
            log.error("Changed fee payment");
            fieldFailure = new FieldFailure("modified", "payment", fee.payment()
                .get()
                .index());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

    private final boolean hasPayment(final Fee fee, final Fee existing) {
        return (fee.payment()
            .isPresent())
                && (existing.payment()
                    .isPresent());
    }

    private final boolean wasChanged(final Fee fee, final Fee existing) {
        return fee.payment()
            .get()
            .index() != existing.payment()
                .get()
                .index();
    }

    private final boolean wasRemoved(final Fee fee, final Fee existing) {
        return (fee.payment()
            .isEmpty())
                && (existing.payment()
                    .isPresent());
    }

}
