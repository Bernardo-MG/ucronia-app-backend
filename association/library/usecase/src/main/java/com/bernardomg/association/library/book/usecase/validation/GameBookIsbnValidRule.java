/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.library.book.usecase.validation;

import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the ISBN is valid.
 */
public final class GameBookIsbnValidRule implements FieldRule<GameBook> {

    private static final String ISBN_10_REGEX = "^(\\d{1,5}-\\d{1,7}-\\d{1,7}-[\\dX])$";

    private static final String ISBN_13_REGEX = "^(\\d{3}-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d)$";

    /**
     * Logger for the class.
     */
    private static final Logger log           = LoggerFactory.getLogger(GameBookIsbnValidRule.class);

    private final Pattern       pattern10     = Pattern.compile(ISBN_10_REGEX);

    private final Pattern       pattern13     = Pattern.compile(ISBN_13_REGEX);

    public GameBookIsbnValidRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final GameBook book) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (book.isbn()
            .isBlank()
                || ((pattern10.matcher(book.isbn())
                    .matches())
                        || (pattern13.matcher(book.isbn())
                            .matches()))) {
            // Empty ISBN
            failure = Optional.empty();
        } else {
            // Invalid ISBN
            log.error("Invalid book ISBN {}", book.isbn());
            fieldFailure = new FieldFailure("invalid", "isbn", book.isbn());
            failure = Optional.of(fieldFailure);
        }

        return failure;
    }

}
