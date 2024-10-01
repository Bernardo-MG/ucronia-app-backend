
package com.bernardomg.association.library.book.usecase.validation;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class BookLanguageCodeValidRule implements FieldRule<Book> {

    private final Set<String> languages = Set.of(Locale.getISOLanguages());

    public BookLanguageCodeValidRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Book book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (!languages.contains(book.language())) {
            log.error("Invalid language code {}", book.language());
            fieldFailure = FieldFailure.of("language", "invalid", book.language());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
