
package com.bernardomg.association.library.lending.usecase.validation;

import java.util.Optional;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BookLendingNotReturnedBeforeLentRule implements FieldRule<BookLending> {

    public BookLendingNotReturnedBeforeLentRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final BookLending lending) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (lending.returnDate()
            .isBefore(lending.lendingDate())) {
            log.error("Returning book {} from {} on {}, which is before the lent date {}", lending.number(),
                lending.borrower()
                    .number(),
                lending.returnDate(), lending.lendingDate());
            fieldFailure = new FieldFailure("invalid", "returnDate", lending.returnDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
