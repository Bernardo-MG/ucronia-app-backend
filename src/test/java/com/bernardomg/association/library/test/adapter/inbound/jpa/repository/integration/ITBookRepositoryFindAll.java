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

package com.bernardomg.association.library.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.test.config.data.annotation.ValidBook;
import com.bernardomg.association.library.test.config.factory.Books;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookRepository - find all")
class ITBookRepositoryFindAll {

    @Autowired
    private BookRepository repository;

    @Test
    @DisplayName("When there are books, they are returned")
    @ValidBook
    void testFindAll() {
        final Iterable<Book> books;
        final Pageable       pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        books = repository.findAll(pageable);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .containsExactly(Books.valid());
    }

    @Test
    @DisplayName("When there are no books, nothing is returned")
    void testFindAll_NoData() {
        final Iterable<Book> books;
        final Pageable       pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        books = repository.findAll(pageable);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .isEmpty();
    }

}
