
package com.bernardomg.association.library.booktype.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the donor has a name.
 */
public final class BookTypeNameNotEmptyRule implements FieldRule<BookType> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(BookTypeNameNotEmptyRule.class);

    public BookTypeNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final BookType bookType) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(bookType.name())) {
            log.error("Empty book type name");
            fieldFailure = new FieldFailure("empty", "name", bookType.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
