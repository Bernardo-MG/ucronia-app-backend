
package com.bernardomg.association.library.book.usecase.validation;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class FictionBookLanguageCodeValidRule implements FieldRule<FictionBook> {

    private final Set<String> languages = Set.of(Locale.getISOLanguages());

    public FictionBookLanguageCodeValidRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final FictionBook book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (!languages.contains(book.language())) {
            log.error("Invalid book language code {}", book.language());
            fieldFailure = FieldFailure.of("language", "invalid", book.language());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
