
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
 * Checks the donor has a name.
 */
public final class BookTypeNameNotExistsRule implements FieldRule<BookType> {

    /**
     * Logger for the class.
     */
    private static final Logger      log = LoggerFactory.getLogger(BookTypeNameNotExistsRule.class);

    private final BookTypeRepository bookTypeRepository;

    public BookTypeNameNotExistsRule(final BookTypeRepository bookTypeRepo) {
        super();

        bookTypeRepository = Objects.requireNonNull(bookTypeRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final BookType bookType) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (bookTypeRepository.existsByName(bookType.name())) {
            log.error("Existing book type name {}", bookType.name());
            fieldFailure = new FieldFailure("existing", "name", bookType.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
