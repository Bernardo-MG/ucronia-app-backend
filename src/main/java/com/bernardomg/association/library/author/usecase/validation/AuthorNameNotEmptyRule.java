
package com.bernardomg.association.library.author.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class AuthorNameNotEmptyRule implements FieldRule<Author> {

    public AuthorNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Author author) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(author.name())) {
            log.error("Empty author name");
            fieldFailure = FieldFailure.of("name", "empty", author.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
