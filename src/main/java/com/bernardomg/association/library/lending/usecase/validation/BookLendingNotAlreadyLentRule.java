
package com.bernardomg.association.library.lending.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BookLendingNotAlreadyLentRule implements FieldRule<BookLending> {

    private final BookLendingRepository bookLendingRepository;

    public BookLendingNotAlreadyLentRule(final BookLendingRepository bookLendingRepo) {
        super();

        bookLendingRepository = Objects.requireNonNull(bookLendingRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final BookLending lending) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Optional<BookLending>  read;

        read = bookLendingRepository.findLent(lending.getNumber());
        if (read.isPresent()) {
            log.error("Lending book {} to {} on {}, which was already lent on {}", lending.getNumber(),
                lending.getPerson(), lending.getLendingDate(), read.get()
                    .getLendingDate());
            fieldFailure = FieldFailure.of("lendingDate", "existing", lending.getLendingDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
