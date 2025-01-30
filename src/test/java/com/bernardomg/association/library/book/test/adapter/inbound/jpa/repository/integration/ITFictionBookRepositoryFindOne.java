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

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDateFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDonorsFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalFictionBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.person.test.configuration.data.annotation.AlternativePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookRepository - find one")
class ITFictionBookRepositoryFindOne {

    @Autowired
    private FictionBookRepository repository;

    @Test
    @DisplayName("When there is a fiction book and it has a donation without date, it is returned")
    @NoMembershipPerson
    @DonationNoDateFictionBook
    void testFindOne_DonationNoDate() {
        final Optional<FictionBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(FictionBooks.donationNoDate());
    }

    @Test
    @DisplayName("When there is a fiction book and it has a donation without donors, it is returned")
    @DonationNoDonorsFictionBook
    void testFindOne_DonationNoDonors() {
        final Optional<FictionBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(FictionBooks.donationNoDonors());
    }

    @Test
    @DisplayName("When there is a full fiction book, it is returned")
    @NoMembershipPerson
    @FullFictionBook
    void testFindOne_Full() {
        final Optional<FictionBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(FictionBooks.full());
    }

    @Test
    @DisplayName("When there is a lent fiction book, it is returned")
    @NoMembershipPerson
    @FullFictionBook
    @LentBookLending
    void testFindOne_FullLent() {
        final Optional<FictionBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(FictionBooks.lent());
    }

    @Test
    @DisplayName("When there is a lent fiction book and it has history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullFictionBook
    @LentBookLendingHistory
    void testFindOne_Lent_History() {
        final Optional<FictionBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(FictionBooks.lentHistory());
    }

    @Test
    @DisplayName("When there is a minimal fiction book, it is returned")
    @MinimalFictionBook
    void testFindOne_Minimal() {
        final Optional<FictionBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(FictionBooks.minimal());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<FictionBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned fiction book, it is returned")
    @NoMembershipPerson
    @FullFictionBook
    @ReturnedBookLending
    void testFindOne_Returned() {
        final Optional<FictionBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(FictionBooks.returned());
    }

    @Test
    @DisplayName("When there is a returned fiction book with history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullFictionBook
    @ReturnedBookLendingHistory
    void testFindOne_Returned_History() {
        final Optional<FictionBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .contains(FictionBooks.returnedHistory());
    }

}
