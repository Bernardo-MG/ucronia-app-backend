
package com.bernardomg.association.library.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class BookTypeNameNotExistsRule implements FieldRule<BookType> {

    private final BookTypeRepository bookTypeRepository;

    public BookTypeNameNotExistsRule(final BookTypeRepository bookTypeRepo) {
        super();

        bookTypeRepository = Objects.requireNonNull(bookTypeRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final BookType bookType) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (bookTypeRepository.exists(bookType.getName())) {
            log.error("Existing name {}", bookType.getName());
            fieldFailure = FieldFailure.of("name", "existing", bookType.getName());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
