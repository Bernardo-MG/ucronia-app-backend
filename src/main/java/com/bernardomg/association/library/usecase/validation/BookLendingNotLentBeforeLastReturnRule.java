
package com.bernardomg.association.library.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.library.domain.repository.BookLendingRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BookLendingNotLentBeforeLastReturnRule implements FieldRule<BookLending> {

    private final BookLendingRepository bookLendingRepository;

    public BookLendingNotLentBeforeLastReturnRule(final BookLendingRepository bookLendingRepo) {
        super();

        bookLendingRepository = Objects.requireNonNull(bookLendingRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final BookLending lending) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Optional<BookLending>  returned;

        returned = bookLendingRepository.findReturned(lending.getNumber());
        if ((returned.isPresent()) && (lending.getLendingDate()
            .isBefore(returned.get()
                .getReturnDate()))) {
            log.error("Lending book {} to {} on {}, which is before last return {}", lending.getNumber(),
                lending.getPerson(), lending.getLendingDate(), returned.get()
                    .getReturnDate());
            fieldFailure = FieldFailure.of("lendingDate", "invalid", lending.getLendingDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
