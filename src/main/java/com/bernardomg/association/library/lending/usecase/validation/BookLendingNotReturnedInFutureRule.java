
package com.bernardomg.association.library.lending.usecase.validation;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

public final class BookLendingNotReturnedInFutureRule implements FieldRule<BookLending> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(BookLendingNotReturnedInFutureRule.class);

    public BookLendingNotReturnedInFutureRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final BookLending lending) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Instant                now;

        now = Instant.now();
        if (now.isBefore(lending.returnDate())) {
            log.error("Returned book {} to {} on {}, which is after current date {}", lending.book()
                .number(),
                lending.borrower()
                    .number(),
                lending.returnDate(), now);
            fieldFailure = new FieldFailure("invalid", "returnDate", lending.returnDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
