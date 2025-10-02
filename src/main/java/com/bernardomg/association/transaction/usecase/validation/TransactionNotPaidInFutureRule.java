
package com.bernardomg.association.transaction.usecase.validation;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the transaction date is not in the future.
 */
public final class TransactionNotPaidInFutureRule implements FieldRule<Transaction> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(TransactionNotPaidInFutureRule.class);

    public TransactionNotPaidInFutureRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Transaction transaction) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Instant                today;

        today = Instant.now();
        if (transaction.date()
            .isAfter(today)) {
            log.error("Attempting to create a transaction at future date {}", transaction.date());
            fieldFailure = new FieldFailure("invalid", "date", transaction.date());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
