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

import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDateBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDonorsBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.Books;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.person.test.configuration.data.annotation.AlternativePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookRepository - find one")
class ITBookRepositoryFindOne {

    @Autowired
    private BookRepository repository;

    @Test
    @DisplayName("With a book with a donation without date, it is returned")
    @NoMembershipPerson
    @DonationNoDateBook
    void testFindOne_DonationNoDate() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(Books.donationNoDate());
    }

    @Test
    @DisplayName("With a book with a donation without donors, it is returned")
    @DonationNoDonorsBook
    void testFindOne_DonationNoDonors() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(Books.donationNoDonors());
    }

    @Test
    @DisplayName("With a full book, it is returned")
    @NoMembershipPerson
    @FullBook
    void testFindOne_Full() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(Books.full());
    }

    @Test
    @DisplayName("With a lent book, it is returned")
    @NoMembershipPerson
    @FullBook
    @LentBookLending
    void testFindOne_FullLent() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(Books.lent());
    }

    @Test
    @DisplayName("With a lent book with history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullBook
    @LentBookLendingHistory
    void testFindOne_Lent_History() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(Books.lentHistory());
    }

    @Test
    @DisplayName("With a minimal book, it is returned")
    @MinimalBook
    void testFindOne_Minimal() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(Books.minimal());
    }

    @Test
    @DisplayName("With no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .isEmpty();
    }

    @Test
    @DisplayName("With a returned book, it is returned")
    @NoMembershipPerson
    @FullBook
    @ReturnedBookLending
    void testFindOne_Returned() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(Books.returned());
    }

    @Test
    @DisplayName("With a returned book with history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullBook
    @ReturnedBookLendingHistory
    void testFindOne_Returned_History() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(Books.returnedHistory());
    }

}
