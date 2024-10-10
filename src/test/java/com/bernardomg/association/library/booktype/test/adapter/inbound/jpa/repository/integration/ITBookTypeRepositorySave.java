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

package com.bernardomg.association.library.booktype.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.data.annotation.ValidBookType;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeEntities;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookTypeRepository - save")
class ITBookTypeRepositorySave {

    @Autowired
    private BookTypeRepository       repository;

    @Autowired
    private BookTypeSpringRepository springRepository;

    @Test
    @DisplayName("When the book type exists, it is updated")
    @ValidBookType
    void testSave_Existing_Persisted() {
        final BookType bookType;

        // GIVEN
        bookType = BookTypes.valid();

        // WHEN
        repository.save(bookType);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("book types")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(BookTypeEntities.valid());
    }

    @Test
    @DisplayName("When the book type exists, it is returned")
    @ValidBookType
    void testSave_Existing_Returned() {
        final BookType bookType;
        final BookType created;

        // GIVEN
        bookType = BookTypes.valid();

        // WHEN
        created = repository.save(bookType);

        // THEN
        Assertions.assertThat(created)
            .as("book type")
            .isEqualTo(BookTypes.valid());
    }

    @Test
    @DisplayName("When saving, an book type is persisted")
    void testSave_Persisted() {
        final BookType bookType;

        // GIVEN
        bookType = BookTypes.valid();

        // WHEN
        repository.save(bookType);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("book types")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(BookTypeEntities.valid());
    }

    @Test
    @DisplayName("When saving, the persisted book type is returned")
    void testSave_Returned() {
        final BookType bookType;
        final BookType created;

        // GIVEN
        bookType = BookTypes.valid();

        // WHEN
        created = repository.save(bookType);

        // THEN
        Assertions.assertThat(created)
            .as("book type")
            .isEqualTo(BookTypes.valid());
    }

}
