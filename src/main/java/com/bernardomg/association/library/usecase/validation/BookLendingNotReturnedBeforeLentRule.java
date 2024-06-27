
package com.bernardomg.association.library.usecase.validation;

import java.util.Optional;

import com.bernardomg.association.library.domain.model.BookLending;
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

        if (lending.getReturnDate()
            .isBefore(lending.getLendingDate())) {
            log.error("Returning book {} from {} on {}, which is before the lent date {}", lending.getNumber(),
                lending.getMember(), lending.getReturnDate(), lending.getLendingDate());
            fieldFailure = FieldFailure.of("lendingDate", "invalid", lending.getLendingDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
