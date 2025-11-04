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

package com.bernardomg.association.library.lending.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.library.lending.usecase.service.DefaultBookLendingService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookLendingService - get all")
class TestBookLendingServiceGetAll {

    @Mock
    private BookLendingRepository     bookLendingRepository;

    @Mock
    private BookRepository            bookRepository;

    @Mock
    private ContactRepository         personRepository;

    @InjectMocks
    private DefaultBookLendingService service;

    @Test
    @DisplayName("When there are lent books, they are returned")
    void testGetAll() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<BookLending> lendings;
        final Page<BookLending> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(BookLendings.lent()), 0, 0, 0, 0, 0, false, false, sorting);
        given(bookLendingRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        lendings = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("lendings")
            .containsExactly(BookLendings.lent());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testGetAll_NoData() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<BookLending> lendings;
        final Page<BookLending> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(bookLendingRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        lendings = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("lendings")
            .isEmpty();
    }

}
