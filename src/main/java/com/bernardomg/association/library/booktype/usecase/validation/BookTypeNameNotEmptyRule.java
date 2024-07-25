
package com.bernardomg.association.library.booktype.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class BookTypeNameNotEmptyRule implements FieldRule<BookType> {

    public BookTypeNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final BookType bookType) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(bookType.getName())) {
            log.error("Empty name");
            fieldFailure = FieldFailure.of("name", "empty", bookType.getName());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
