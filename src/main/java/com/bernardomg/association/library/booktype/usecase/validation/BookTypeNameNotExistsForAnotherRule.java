
package com.bernardomg.association.library.booktype.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the book type name doesn't exist for another book type.
 */
public final class BookTypeNameNotExistsForAnotherRule implements FieldRule<BookType> {

    /**
     * Logger for the class.
     */
    private static final Logger      log = LoggerFactory.getLogger(BookTypeNameNotExistsForAnotherRule.class);

    private final BookTypeRepository bookTypeRepository;

    public BookTypeNameNotExistsForAnotherRule(final BookTypeRepository bookTypeRepo) {
        super();

        bookTypeRepository = Objects.requireNonNull(bookTypeRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final BookType bookType) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (bookTypeRepository.existsByNameForAnother(bookType.name(), bookType.number())) {
            log.error("Existing book type name {} for a book type distinct from {}", bookType.name(),
                bookType.number());
            fieldFailure = new FieldFailure("existing", "name", bookType.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
