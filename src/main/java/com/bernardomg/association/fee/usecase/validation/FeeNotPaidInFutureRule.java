
package com.bernardomg.association.fee.usecase.validation;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.Fee.Transaction;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the fees's months are not duplicated.
 */
public final class FeeNotPaidInFutureRule implements FieldRule<Fee> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(FeeNotPaidInFutureRule.class);

    public FeeNotPaidInFutureRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Fee fee) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Optional<Instant>      date;
        final Optional<Boolean>      exists;
        final Instant                today;

        today = Instant.now();
        date = fee.transaction()
            .map(Transaction::date);
        exists = date.map(d -> d.isAfter(today));
        if (exists.isPresent() && exists.get()) {
            log.error("Attempting to pay fee at future date {}", date.get());
            fieldFailure = new FieldFailure("invalid", "paymentDate", date.get());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
