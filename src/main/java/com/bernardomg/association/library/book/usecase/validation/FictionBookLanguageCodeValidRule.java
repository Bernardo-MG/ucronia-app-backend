
package com.bernardomg.association.library.book.usecase.validation;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the fiction book language has a valid code.
 */
public final class FictionBookLanguageCodeValidRule implements FieldRule<FictionBook> {

    /**
     * Logger for the class.
     */
    private static final Logger log       = LoggerFactory.getLogger(FictionBookLanguageCodeValidRule.class);

    private final Set<String>   languages = Set.of(Locale.getISOLanguages());

    public FictionBookLanguageCodeValidRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final FictionBook book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (!languages.contains(book.language())) {
            log.error("Invalid book language code {}", book.language());
            fieldFailure = new FieldFailure("invalid", "language", book.language());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
