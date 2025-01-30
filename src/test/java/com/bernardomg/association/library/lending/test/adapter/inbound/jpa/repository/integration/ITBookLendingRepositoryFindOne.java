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

import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.person.test.configuration.data.annotation.AlternativePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookLendingRepository - find one")
class ITBookLendingRepositoryFindOne {

    @Autowired
    private BookLendingRepository repository;

    @Test
    @DisplayName("When the book is lent, it is returned")
    @NoMembershipPerson
    @FullGameBook
    @LentBookLending
    void testFindOne_Lent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .contains(BookLendings.lent());
    }

    @Test
    @DisplayName("When the book is lent and has history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullGameBook
    @LentBookLendingHistory
    void testFindOne_Lent_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .contains(BookLendings.lentLast());
    }

    @Test
    @DisplayName("With no person, nothing is returned")
    @NoMembershipPerson
    void testFindOne_NoBook() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When the book has no history, nothing is returned")
    @NoMembershipPerson
    @FullGameBook
    void testFindOne_NoHistory() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("With no person, nothing is returned")
    @MinimalGameBook
    void testFindOne_NoPerson() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When the book is returned, it is returned")
    @NoMembershipPerson
    @FullGameBook
    @ReturnedBookLending
    void testFindOne_Returned() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .contains(BookLendings.returned());
    }

    @Test
    @DisplayName("When the book is returned and has history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullGameBook
    @ReturnedBookLendingHistory
    void testFindOne_Returned_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .contains(BookLendings.returnedLast());
    }

}
