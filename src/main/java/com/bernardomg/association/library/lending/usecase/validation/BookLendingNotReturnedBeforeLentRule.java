
package com.bernardomg.association.library.lending.usecase.validation;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

public final class BookLendingNotReturnedBeforeLentRule implements FieldRule<BookLending> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(BookLendingNotReturnedBeforeLentRule.class);

    public BookLendingNotReturnedBeforeLentRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final BookLending lending) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (lending.returnDate()
            .isBefore(lending.lendingDate())) {
            log.error("Returning book {} from {} on {}, which is before the lent date {}", lending.book()
                .number(),
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
