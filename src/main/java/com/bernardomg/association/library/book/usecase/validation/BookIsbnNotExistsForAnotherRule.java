
package com.bernardomg.association.library.book.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the ISBN is not already registered for another book.
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

        if ((!StringUtils.isBlank(book.isbn()))
                && (bookRepository.existsByIsbnForAnother(book.number(), book.isbn()))) {
            log.error("Existing book ISBN {} for a book distinct of {}", book.isbn(), book.number());
            fieldFailure = new FieldFailure("existing", "isbn", book.isbn());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
