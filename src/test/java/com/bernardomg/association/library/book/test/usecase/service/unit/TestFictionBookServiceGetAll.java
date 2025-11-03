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

package com.bernardomg.association.library.book.test.usecase.service.unit;

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

import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultFictionBookService;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService - get all")
class TestFictionBookServiceGetAll {

    @Mock
    private AuthorRepository          authorRepository;

    @Mock
    private FictionBookRepository     bookRepository;

    @Mock
    private ContactRepository         contactRepository;

    @Mock
    private PublisherRepository       publisherRepository;

    @InjectMocks
    private DefaultFictionBookService service;

    public TestFictionBookServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When there are books, they are returned")
    void testGetAll() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<FictionBook> books;
        final Page<FictionBook> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(FictionBooks.full()), 0, 0, 0, 0, 0, false, false, sorting);
        given(bookRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        books = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .containsExactly(FictionBooks.full());
    }

    @Test
    @DisplayName("When there are no books, nothing is returned")
    void testGetAll_NoData() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<FictionBook> books;
        final Page<FictionBook> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(bookRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        books = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .isEmpty();
    }

}
