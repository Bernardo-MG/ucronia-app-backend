
package com.bernardomg.association.library.book.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the ISBN is not already registered.
 */
public final class FictionBookIsbnNotExistsRule implements FieldRule<FictionBook> {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(FictionBookIsbnNotExistsRule.class);

    private final FictionBookRepository bookRepository;

    public FictionBookIsbnNotExistsRule(final FictionBookRepository bookRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final FictionBook book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if ((!StringUtils.isBlank(book.isbn())) && (bookRepository.existsByIsbn(book.isbn()))) {
            log.error("Existing book ISBN {}", book.isbn());
            fieldFailure = new FieldFailure("existing", "isbn", book.isbn());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
