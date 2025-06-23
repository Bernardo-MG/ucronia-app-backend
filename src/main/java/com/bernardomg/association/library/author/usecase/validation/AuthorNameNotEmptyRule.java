
package com.bernardomg.association.library.author.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the donor has a name.
 */
public final class AuthorNameNotEmptyRule implements FieldRule<Author> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(AuthorNameNotEmptyRule.class);

    public AuthorNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Author author) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(author.name())) {
            log.error("Empty author name");
            fieldFailure = new FieldFailure("empty", "name", author.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
