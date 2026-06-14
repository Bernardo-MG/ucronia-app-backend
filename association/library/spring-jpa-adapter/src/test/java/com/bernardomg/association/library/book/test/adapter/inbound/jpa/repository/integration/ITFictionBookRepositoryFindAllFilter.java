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
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.domain.model.BookFilter;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookFilters;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FictionBookRepository - find all - filter")
class ITFictionBookRepositoryFindAllFilter {

    @Autowired
    private FictionBookRepository repository;

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindAll_NoData() {
        final Page<FictionBook> books;
        final Pagination        pagination;
        final Sorting           sorting;
        final BookFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();
        filter = BookFilters.title();

        // WHEN
        books = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by partial title, it is returned")
    @ValidProfile
    @FullFictionBook
    void testFindAll_PartialTitle() {
        final Page<FictionBook> books;
        final Pagination        pagination;
        final Sorting           sorting;
        final BookFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();
        filter = BookFilters.partialTitle();

        // WHEN
        books = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .containsExactly(FictionBooks.full());
    }

    @Test
    @DisplayName("When filtering by subtitle, it is returned")
    @ValidProfile
    @FullFictionBook
    void testFindAll_Subtitle() {
        final Page<FictionBook> books;
        final Pagination        pagination;
        final Sorting           sorting;
        final BookFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();
        filter = BookFilters.subtitle();

        // WHEN
        books = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .containsExactly(FictionBooks.full());
    }

    @Test
    @DisplayName("When filtering by supertitle, it is returned")
    @ValidProfile
    @FullFictionBook
    void testFindAll_Supertitle() {
        final Page<FictionBook> books;
        final Pagination        pagination;
        final Sorting           sorting;
        final BookFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();
        filter = BookFilters.supertitle();

        // WHEN
        books = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .containsExactly(FictionBooks.full());
    }

    @Test
    @DisplayName("When filtering by title, it is returned")
    @ValidProfile
    @FullFictionBook
    void testFindAll_Title() {
        final Page<FictionBook> books;
        final Pagination        pagination;
        final Sorting           sorting;
        final BookFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();
        filter = BookFilters.title();

        // WHEN
        books = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .containsExactly(FictionBooks.full());
    }

    @Test
    @DisplayName("When filtering by a wrong title, nothing is returned")
    @ValidProfile
    @FullFictionBook
    void testFindAll_WrongTitle() {
        final Page<FictionBook> books;
        final Pagination        pagination;
        final Sorting           sorting;
        final BookFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();
        filter = BookFilters.partialTitle();

        // WHEN
        books = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .containsExactly(FictionBooks.full());
    }

}
