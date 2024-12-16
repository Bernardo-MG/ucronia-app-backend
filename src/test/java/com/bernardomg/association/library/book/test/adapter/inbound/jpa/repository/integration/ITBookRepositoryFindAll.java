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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullBook;
import com.bernardomg.association.library.book.test.configuration.factory.Books;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.person.test.configuration.data.annotation.AlternativePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookRepository - find all")
class ITBookRepositoryFindAll {

    @Autowired
    private BookRepository repository;

    @Test
    @DisplayName("When there are books, they are returned")
    @NoMembershipPerson
    @FullBook
    void testFindAll() {
        final Iterable<Book> books;
        final Pagination     pagination;
        final Sorting        sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        books = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(Books.full());
    }

    @Test
    @DisplayName("When there is a lent book, it is returned")
    @NoMembershipPerson
    @FullBook
    @LentBookLending
    void testFindAll_Lent() {
        final Iterable<Book> books;
        final Pagination     pagination;
        final Sorting        sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        books = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(Books.lent());
    }

    @Test
    @DisplayName("When there is a lent book with history, it is returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullBook
    @LentBookLendingHistory
    void testFindAll_Lent_WithHistory() {
        final Iterable<Book> books;
        final Pagination     pagination;
        final Sorting        sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        books = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(Books.lentHistory());
    }

    @Test
    @DisplayName("When there are no books, nothing is returned")
    void testFindAll_NoData() {
        final Iterable<Book> books;
        final Pagination     pagination;
        final Sorting        sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        books = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned book, they are returned")
    @NoMembershipPerson
    @FullBook
    @ReturnedBookLending
    void testFindAll_Returned() {
        final Iterable<Book> books;
        final Pagination     pagination;
        final Sorting        sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        books = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(Books.returned());
    }

    @Test
    @DisplayName("When there is a returned book, they are returned")
    @NoMembershipPerson
    @AlternativePerson
    @FullBook
    @ReturnedBookLendingHistory
    void testFindAll_Returned_WithHistory() {
        final Iterable<Book> books;
        final Pagination     pagination;
        final Sorting        sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        books = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(Books.returnedHistory());
    }

}
