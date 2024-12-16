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

package com.bernardomg.association.library.booktype.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.booktype.usecase.service.DefaultBookTypeService;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookTypeService - get all")
class TestBookTypeServiceGetAll {

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @InjectMocks
    private DefaultBookTypeService service;

    @Test
    @DisplayName("When there are book types, they are returned")
    void testGetAll() {
        final Pagination         pagination;
        final Sorting            sorting;
        final Iterable<BookType> types;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        given(bookTypeRepository.findAll(pagination, sorting)).willReturn(List.of(BookTypes.valid()));

        // WHEN
        types = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(types)
            .as("book types")
            .containsExactly(BookTypes.valid());
    }

    @Test
    @DisplayName("When there are no book types, nothing is returned")
    void testGetAll_NoData() {
        final Pagination         pagination;
        final Sorting            sorting;
        final Iterable<BookType> types;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        given(bookTypeRepository.findAll(pagination, sorting)).willReturn(List.of());

        // WHEN
        types = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(types)
            .as("book types")
            .isEmpty();
    }

}
