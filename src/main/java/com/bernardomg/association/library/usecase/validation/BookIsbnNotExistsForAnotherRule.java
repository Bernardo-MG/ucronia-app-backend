
package com.bernardomg.association.library.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class BookIsbnNotExistsForAnotherRule implements FieldRule<Book> {

    private final BookRepository bookRepository;

    public BookIsbnNotExistsForAnotherRule(final BookRepository bookRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Book book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if ((!StringUtils.isBlank(book.getIsbn()))
                && (bookRepository.existsByIsbnForAnother(book.getNumber(), book.getIsbn()))) {
            log.error("Existing ISBN {} for a book distinct of {}", book.getIsbn(), book.getNumber());
            fieldFailure = FieldFailure.of("isbn", "existing", book.getIsbn());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
