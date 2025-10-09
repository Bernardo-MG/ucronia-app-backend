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

package com.bernardomg.association.library.author.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorEntities;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("AuthorRepository - save")
class ITAuthorRepositorySave {

    @Autowired
    private AuthorRepository       repository;

    @Autowired
    private AuthorSpringRepository springRepository;

    @Test
    @DisplayName("When the author exists, it is updated")
    @ValidAuthor
    void testSave_Existing_Persisted() {
        final Author author;

        // GIVEN
        author = Authors.valid();

        // WHEN
        repository.save(author);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("authors")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(AuthorEntities.valid());
    }

    @Test
    @DisplayName("When the author exists, it is returned")
    @ValidAuthor
    void testSave_Existing_Returned() {
        final Author author;
        final Author created;

        // GIVEN
        author = Authors.valid();

        // WHEN
        created = repository.save(author);

        // THEN
        Assertions.assertThat(created)
            .as("author")
            .isEqualTo(Authors.valid());
    }

    @Test
    @DisplayName("When saving, an author is persisted")
    void testSave_Persisted() {
        final Author author;

        // GIVEN
        author = Authors.valid();

        // WHEN
        repository.save(author);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("authors")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(AuthorEntities.valid());
    }

    @Test
    @DisplayName("When saving, the persisted author is returned")
    void testSave_Returned() {
        final Author author;
        final Author created;

        // GIVEN
        author = Authors.valid();

        // WHEN
        created = repository.save(author);

        // THEN
        Assertions.assertThat(created)
            .as("author")
            .isEqualTo(Authors.valid());
    }

}
