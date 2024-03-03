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

import com.bernardomg.association.library.adapter.inbound.jpa.repository.BookSpringRepository;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.test.config.data.annotation.ValidAuthor;
import com.bernardomg.association.library.test.config.data.annotation.ValidBookType;
import com.bernardomg.association.library.test.config.data.annotation.ValidGameSystem;
import com.bernardomg.association.library.test.config.factory.BookEntities;
import com.bernardomg.association.library.test.config.factory.Books;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookRepository - save")
class ITBookRepositorySave {

    @Autowired
    private BookRepository       repository;

    @Autowired
    private BookSpringRepository springRepository;

    @Test
    @DisplayName("When saving, the persisted author is returned")
    @ValidAuthor
    @ValidBookType
    @ValidGameSystem
    void testSave_Full_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("author")
            .isEqualTo(Books.full());
    }

    @Test
    @DisplayName("When saving, an author is persisted")
    void testSave_Persisted() {
        final Book book;

        // GIVEN
        book = Books.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("authors")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .contains(BookEntities.valid());
    }

    @Test
    @DisplayName("When saving, the persisted author is returned")
    void testSave_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("author")
            .isEqualTo(Books.minimal());
    }

}