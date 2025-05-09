/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.library.lending.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.person.test.configuration.data.annotation.AlternativePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookLendingRepository - find returned for person at date")
class ITBookLendingRepositoryFindReturnedExact {

    @Autowired
    private BookLendingRepository repository;

    @Test
    @DisplayName("When there is a lent fiction book, nothing is returned")
    @NoMembershipPerson
    @FullFictionBook
    @LentBookLending
    void testFindReturned_FictionBook_Lent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a fiction book and it has no history, nothing is returned")
    @NoMembershipPerson
    @FullFictionBook
    void testFindReturned_FictionBook_NoHistory() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a fiction book and it is not lent, nothing is returned")
    @MinimalFictionBook
    void testFindReturned_FictionBook_NotLent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned fiction book, it is returned")
    @NoMembershipPerson
    @FullFictionBook
    @ReturnedBookLending
    void testFindReturned_FictionBook_Returned() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .contains(BookLendings.returned());
    }

    @Test
    @DisplayName("When there is a returned game book and it has history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullFictionBook
    @ReturnedBookLendingHistory
    void testFindReturned_FictionBook_Returned_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .contains(BookLendings.returned());
    }

    @Test
    @DisplayName("When there is a lent game book, nothing is returned")
    @NoMembershipPerson
    @FullGameBook
    @LentBookLending
    void testFindReturned_GameBook_Lent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a game book and it has no history, nothing is returned")
    @NoMembershipPerson
    @FullGameBook
    void testFindReturned_GameBook_NoHistory() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a game book and it is not lent, nothing is returned")
    @MinimalGameBook
    void testFindReturned_GameBook_NotLent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned game book, it is returned")
    @NoMembershipPerson
    @FullGameBook
    @ReturnedBookLending
    void testFindReturned_GameBook_Returned() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .contains(BookLendings.returned());
    }

    @Test
    @DisplayName("When there is a returned game book and it has history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullGameBook
    @ReturnedBookLendingHistory
    void testFindReturned_GameBook_Returned_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .contains(BookLendings.returned());
    }

    @Test
    @DisplayName("With no book, nothing is returned")
    @NoMembershipPerson
    void testFindReturned_NoBook() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

}
