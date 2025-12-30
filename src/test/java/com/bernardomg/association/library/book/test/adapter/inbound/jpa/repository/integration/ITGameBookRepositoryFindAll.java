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

import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("GameBookRepository - find all")
class ITGameBookRepositoryFindAll {

    @Autowired
    private GameBookRepository repository;

    @Test
    @DisplayName("When there is a game book, it is returned")
    @ValidProfile
    @FullGameBook
    void testFindAll() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(GameBooks.full());
    }

    @Test
    @DisplayName("When there is a lent game book, it is returned")
    @ValidProfile
    @FullGameBook
    @LentBookLending
    void testFindAll_Lent() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(GameBooks.lent());
    }

    @Test
    @DisplayName("When there is a lent game book and it has history, it is returned")
    @ValidProfile
    @AlternativeProfile
    @FullGameBook
    @LentBookLendingHistory
    void testFindAll_Lent_WithHistory() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(GameBooks.lentHistory());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindAll_NoData() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

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
    @DisplayName("When there is a returned game book, it is returned")
    @ValidProfile
    @FullGameBook
    @ReturnedBookLending
    void testFindAll_Returned() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(GameBooks.returned());
    }

    @Test
    @DisplayName("When there is a returned game book and it has history, it is returned")
    @ValidProfile
    @AlternativeProfile
    @FullGameBook
    @ReturnedBookLendingHistory
    void testFindAll_Returned_WithHistory() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(GameBooks.returnedHistory());
    }

}
