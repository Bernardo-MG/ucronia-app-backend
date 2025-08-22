
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
 * Checks the fee's transaction was not changed or removed.
 */
public final class FeeTransactionNotChangedRule implements FieldRule<Fee> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(FeeTransactionNotChangedRule.class);

    private final FeeRepository feeRepository;

    public FeeTransactionNotChangedRule(final FeeRepository feeRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Fee fee) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Fee                    existing;

        existing = feeRepository.findOne(fee.member()
            .number(), fee.month())
            .get();
        if (wasRemoved(fee, existing)) {
            log.error("Removed payment from fee");
            fieldFailure = new FieldFailure("removed", "transaction", null);
            failure = Optional.of(fieldFailure);
        } else if (hasTransaction(fee, existing) && wasChanged(fee, existing)) {
            log.error("Changed fee transaction");
            fieldFailure = new FieldFailure("modified", "transaction", fee.transaction()
                .get()
                .index());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

    private final boolean hasTransaction(final Fee fee, final Fee existing) {
        return (fee.transaction()
            .isPresent())
                && (existing.transaction()
                    .isPresent());
    }

    private final boolean wasChanged(final Fee fee, final Fee existing) {
        return fee.transaction()
            .get()
            .index() != existing.transaction()
                .get()
                .index();
    }

    private final boolean wasRemoved(final Fee fee, final Fee existing) {
        return (fee.transaction()
            .isEmpty())
                && (existing.transaction()
                    .isPresent());
    }

}
