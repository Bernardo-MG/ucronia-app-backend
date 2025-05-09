
package com.bernardomg.association.library.book.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class FictionBookTitleNotEmptyRule implements FieldRule<FictionBook> {

    public FictionBookTitleNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final FictionBook book) {
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
