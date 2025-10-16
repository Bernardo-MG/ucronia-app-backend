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

package com.bernardomg.association.library.lending.domain.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.person.domain.model.PersonName;

public record BookLending(LentBook book, Borrower borrower, Instant lendingDate, Instant returnDate) {

    public BookLending(final LentBook book, final Borrower borrower, final Instant lendingDate) {
        this(book, borrower, lendingDate, null);
    }

    public BookLending returned(final Instant date) {
        return new BookLending(book, borrower, lendingDate, date);
    }

    public record LentBook(long number, Title title) {
        // TODO: not all the info is being returned to the frontend?

    }

    public record Borrower(long number, PersonName name) {

    }

    public Long getDays() {
        final Long days;

        // TODO: don't generate, set on creation
        if (returnDate == null) {
            days = ChronoUnit.DAYS.between(lendingDate, Instant.now()) + 1;
        } else {
            days = ChronoUnit.DAYS.between(lendingDate, returnDate) + 1;
        }

        return days;
    }

}
