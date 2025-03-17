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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.book.test.configuration.data.annotation.FullBook;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookLendingRepository - find all")
class ITBookLendingRepositoryFindAll {

    @Autowired
    private BookLendingRepository repository;

    @Test
    @DisplayName("When there are lent books, they are returned")
    @NoMembershipPerson
    @FullBook
    @LentBookLending
    void testFindAll_Lent() {
        final Pagination            pagination;
        final Sorting               sorting;
        final Iterable<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .as("lendings")
            .containsExactly(BookLendings.lent());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindAll_NoData() {
        final Pagination            pagination;
        final Sorting               sorting;
        final Iterable<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .as("lendings")
            .isEmpty();
    }

    @Test
    @DisplayName("When there are returned books, nothing is returned")
    @NoMembershipPerson
    @FullBook
    @ReturnedBookLending
    void testFindAll_Returned() {
        final Pagination            pagination;
        final Sorting               sorting;
        final Iterable<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .as("lendings")
            .isEmpty();
    }

}
