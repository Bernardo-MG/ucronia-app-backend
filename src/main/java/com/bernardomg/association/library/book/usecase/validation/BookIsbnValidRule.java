
package com.bernardomg.association.library.book.usecase.validation;

import java.util.Optional;
import java.util.regex.Pattern;

import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the ISBN is valid.
 */
@Slf4j
public final class BookIsbnValidRule implements FieldRule<Book> {

    private static final String ISBN_10_REGEX = "^(\\d{1,5}-\\d{1,7}-\\d{1,7}-[\\dX])$";

    private static final String ISBN_13_REGEX = "^(\\d{3}-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d)$";

    private final Pattern       pattern10     = Pattern.compile(ISBN_10_REGEX);

    private final Pattern       pattern13     = Pattern.compile(ISBN_13_REGEX);

    public BookIsbnValidRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Book book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (book.getIsbn()
            .isBlank()
                || ((pattern10.matcher(book.getIsbn())
                    .matches())
                        || (pattern13.matcher(book.getIsbn())
                            .matches()))) {
            // Empty ISBN
            failure = Optional.empty();
        } else {
            // Invalid ISBN
            log.error("Invalid ISBN {}", book.getIsbn());
            fieldFailure = FieldFailure.of("isbn", "invalid", book.getIsbn());
            failure = Optional.of(fieldFailure);
        }

        return failure;
    }

}
