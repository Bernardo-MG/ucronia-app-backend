
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

    private static boolean isValidISBN10(final String isbn) {
        final String cleanedIsbn;
        final String control;
        int          checksum;

        cleanedIsbn = isbn.replace("-", "");

        checksum = 0;
        for (int i = 0; i < 9; i++) {
            final int digit;

            digit = Integer.parseInt(cleanedIsbn.substring(i, i + 1));
            checksum += (digit * (10 - i));
        }

        control = cleanedIsbn.substring(9);
        if ("X".equals(control)) {
            checksum += 10;
        } else {
            checksum += Integer.parseInt(control);
        }

        return ((checksum % 11) == 0);
    }

    private static boolean isValidISBN13(final String isbn) {
        final String cleanedIsbn;
        int          checksum;
        int          sum;

        cleanedIsbn = isbn.replace("-", "");

        sum = 0;
        for (int i = 0; i < 12; i++) {
            final int digit;

            digit = Integer.parseInt(cleanedIsbn.substring(i, i + 1));
            sum += ((i % 2) == 0) ? digit : (digit * 3);
        }

        checksum = 10 - (sum % 10);
        if (checksum == 10) {
            checksum = 0;
        }

        return checksum == Integer.parseInt(cleanedIsbn.substring(12));
    }

    private final Pattern pattern10 = Pattern.compile(ISBN_10_REGEX);

    private final Pattern pattern13 = Pattern.compile(ISBN_13_REGEX);

    public BookIsbnValidRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Book book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (book.getIsbn()
            .isBlank()) {
            // Empty ISBN
            failure = Optional.empty();
        } else if (pattern10.matcher(book.getIsbn())
            .matches()) {
            if (!isValidISBN10(book.getIsbn())) {
                // Invalid ISBN 10
                log.error("Invalid ISBN {}", book.getIsbn());
                fieldFailure = FieldFailure.of("isbn", "invalid", book.getIsbn());
                failure = Optional.of(fieldFailure);
            } else {
                // Valid ISBN 10
                failure = Optional.empty();
            }
        } else if (!pattern13.matcher(book.getIsbn())
            .matches() || !isValidISBN13(book.getIsbn())) {
            // Invalid ISBN 13
            log.error("Invalid ISBN {}", book.getIsbn());
            fieldFailure = FieldFailure.of("isbn", "invalid", book.getIsbn());
            failure = Optional.of(fieldFailure);
        } else {
            // Valid ISBN
            failure = Optional.empty();
        }

        return failure;
    }

}
