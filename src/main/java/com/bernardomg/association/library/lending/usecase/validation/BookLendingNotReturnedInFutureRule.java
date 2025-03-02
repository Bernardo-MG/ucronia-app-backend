
package com.bernardomg.association.library.lending.usecase.validation;

import java.time.LocalDate;
import java.util.Optional;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BookLendingNotReturnedInFutureRule implements FieldRule<BookLending> {

    public BookLendingNotReturnedInFutureRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final BookLending lending) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final LocalDate              now;

        now = LocalDate.now();
        if (now.isBefore(lending.returnDate())) {
            log.error("Returned book {} to {} on {}, which is after current date {}", lending.number(),
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
