
package com.bernardomg.association.library.book.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the donor has a name.
 */
public final class GameBookTitleNotEmptyRule implements FieldRule<GameBook> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(GameBookTitleNotEmptyRule.class);

    public GameBookTitleNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final GameBook book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(book.title()
            .title())) {
            log.error("Empty book title");
            fieldFailure = new FieldFailure("empty", "title", book.title());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
