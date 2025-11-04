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

package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.test.configuration.data.annotation.AlternativeContact;
import com.bernardomg.association.contact.test.configuration.data.annotation.NoMembershipContact;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FictionBookRepository - find all")
class ITFictionBookRepositoryFindAll {

    @Autowired
    private FictionBookRepository repository;

    @Test
    @DisplayName("When there is a fiction book, it is returned")
    @NoMembershipContact
    @FullFictionBook
    void testFindAll() {
        final Iterable<FictionBook> books;
        final Sorting               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(FictionBooks.full());
    }

    @Test
    @DisplayName("When there is a lent fiction book, it is returned")
    @NoMembershipContact
    @FullFictionBook
    @LentBookLending
    void testFindAll_Lent() {
        final Iterable<FictionBook> books;
        final Sorting               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(FictionBooks.lent());
    }

    @Test
    @DisplayName("When there is a lent fiction book and it has history, it is returned")
    @NoMembershipContact
    @AlternativeContact
    @FullFictionBook
    @LentBookLendingHistory
    void testFindAll_Lent_WithHistory() {
        final Iterable<FictionBook> books;
        final Sorting               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(FictionBooks.lentHistory());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindAll_NoData() {
        final Iterable<FictionBook> books;
        final Sorting               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned fiction book, it is returned")
    @NoMembershipContact
    @FullFictionBook
    @ReturnedBookLending
    void testFindAll_Returned() {
        final Iterable<FictionBook> books;
        final Sorting               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(FictionBooks.returned());
    }

    @Test
    @DisplayName("When there is a returned fiction book and it has history, it is returned")
    @NoMembershipContact
    @AlternativeContact
    @FullFictionBook
    @ReturnedBookLendingHistory
    void testFindAll_Returned_WithHistory() {
        final Iterable<FictionBook> books;
        final Sorting               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(FictionBooks.returnedHistory());
    }

}
