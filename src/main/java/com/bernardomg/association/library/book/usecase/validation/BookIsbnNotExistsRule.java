
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
 * Checks the ISBN is not already registered.
 */
@Slf4j
public final class BookIsbnNotExistsRule implements FieldRule<Book> {

    private final BookRepository bookRepository;

    public BookIsbnNotExistsRule(final BookRepository bookRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Book book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if ((!StringUtils.isBlank(book.getIsbn())) && (bookRepository.existsByIsbn(book.getIsbn()))) {
            log.error("Existing ISBN {}", book.getIsbn());
            fieldFailure = FieldFailure.of("isbn", "existing", book.getIsbn());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
