
package com.bernardomg.association.library.book.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the ISBN is not already registered.
 */
public final class GameBookIsbnNotExistsRule implements FieldRule<GameBook> {

    /**
     * Logger for the class.
     */
    private static final Logger      log = LoggerFactory.getLogger(GameBookIsbnNotExistsRule.class);

    private final GameBookRepository bookRepository;

    public GameBookIsbnNotExistsRule(final GameBookRepository bookRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final GameBook book) {
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
