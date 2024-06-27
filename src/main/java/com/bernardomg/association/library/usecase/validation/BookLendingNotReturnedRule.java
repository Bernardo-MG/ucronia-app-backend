
package com.bernardomg.association.library.usecase.validation;

import java.util.Optional;

import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BookLendingNotReturnedRule implements FieldRule<BookLending> {

    public BookLendingNotReturnedRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final BookLending lending) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (lending.getReturnDate() != null) {
            log.error("Returning book {} from {} which was already returned on {}", lending.getNumber(),
                lending.getMember(), lending.getReturnDate());
            fieldFailure = FieldFailure.of("returnDate", "invalid", lending.getReturnDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
