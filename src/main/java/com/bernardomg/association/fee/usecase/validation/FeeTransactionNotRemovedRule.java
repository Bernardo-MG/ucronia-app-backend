
package com.bernardomg.association.fee.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the fee's transaction was not removed.
 */
@Slf4j
public final class FeeTransactionNotRemovedRule implements FieldRule<Fee> {

    private final FeeRepository feeRepository;

    public FeeTransactionNotRemovedRule(final FeeRepository feeRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Fee fee) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Fee                    existing;

        existing = feeRepository.findOne(fee.person()
            .number(), fee.date())
            .get();
        if ((fee.transaction()
            .isEmpty())
                && (existing.transaction()
                    .isPresent())) {
            log.error("Removed transaction from fee");
            fieldFailure = FieldFailure.of("transaction", "missing", null);
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
