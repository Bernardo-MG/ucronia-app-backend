
package com.bernardomg.association.library.lending.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BookLendingNotAlreadyReturnedRule implements FieldRule<BookLending> {

    private final BookLendingRepository bookLendingRepository;

    public BookLendingNotAlreadyReturnedRule(final BookLendingRepository bookLendingRepo) {
        super();

        bookLendingRepository = Objects.requireNonNull(bookLendingRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final BookLending lending) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;
        final Optional<BookLending>  returned;

        returned = bookLendingRepository.findReturned(lending.getNumber(), lending.getPerson()
            .getNumber(), lending.getLendingDate());
        if (returned.isPresent()) {
            log.error("Returning book {} to {} on {}, which was already returned on {}", lending.getNumber(),
                lending.getPerson(), lending.getReturnDate(), returned.get()
                    .getReturnDate());
            fieldFailure = FieldFailure.of("returnDate", "existing", lending.getReturnDate());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
