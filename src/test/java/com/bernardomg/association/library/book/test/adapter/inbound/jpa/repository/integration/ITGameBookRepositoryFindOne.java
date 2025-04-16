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

package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDateGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDonorsGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.person.test.configuration.data.annotation.AlternativePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("GameBookRepository - find one")
class ITGameBookRepositoryFindOne {

    @Autowired
    private GameBookRepository repository;

    @Test
    @DisplayName("When there is a game book and it has a donation without date, it is returned")
    @NoMembershipPerson
    @DonationNoDateGameBook
    void testFindOne_DonationNoDate() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(GameBooks.donationNoDate());
    }

    @Test
    @DisplayName("When there is a game book and it has a donation without donors, it is returned")
    @DonationNoDonorsGameBook
    void testFindOne_DonationNoDonors() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(GameBooks.donationNoDonors());
    }

    @Test
    @DisplayName("When there is a full game book, it is returned")
    @NoMembershipPerson
    @FullGameBook
    void testFindOne_Full() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(GameBooks.full());
    }

    @Test
    @DisplayName("When there is a lent game book, it is returned")
    @NoMembershipPerson
    @FullGameBook
    @LentBookLending
    void testFindOne_FullLent() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(GameBooks.lent());
    }

    @Test
    @DisplayName("When there is a lent game book and it has history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullGameBook
    @LentBookLendingHistory
    void testFindOne_Lent_History() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(GameBooks.lentHistory());
    }

    @Test
    @DisplayName("When there is a minimal game book, it is returned")
    @MinimalGameBook
    void testFindOne_Minimal() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(GameBooks.minimal());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned game book, it is returned")
    @NoMembershipPerson
    @FullGameBook
    @ReturnedBookLending
    void testFindOne_Returned() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(GameBooks.returned());
    }

    @Test
    @DisplayName("When there is a returned game book with history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullGameBook
    @ReturnedBookLendingHistory
    void testFindOne_Returned_History() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(GameBooks.returnedHistory());
    }

}
