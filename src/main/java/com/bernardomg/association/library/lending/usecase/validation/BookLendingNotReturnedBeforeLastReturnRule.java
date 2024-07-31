
package com.bernardomg.association.library.lending.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BookLendingNotReturnedBeforeLastReturnRule implements FieldRule<BookLending> {

    private final BookLendingRepository bookLendingRepository;

    public BookLendingNotReturnedBeforeLastReturnRule(final BookLendingRepository bookLendingRepo) {
        super();

        bookLendingRepository = Objects.requireNonNull(bookLendingRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final BookLending lending) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Optional<BookLending>  returned;

        returned = bookLendingRepository.findReturned(lending.getNumber());
        if ((returned.isPresent()) && (lending.getReturnDate()
            .isBefore(returned.get()
                .getReturnDate()))) {
            log.error("Returning book {} from {} on {}, which is before last return {}", lending.getNumber(),
                lending.getPerson(), lending.getReturnDate(), returned.get()
                    .getReturnDate());
            fieldFailure = FieldFailure.of("returnDate", "invalid", lending.getReturnDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
