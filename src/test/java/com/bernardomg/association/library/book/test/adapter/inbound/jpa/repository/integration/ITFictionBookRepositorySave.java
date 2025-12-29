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

import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.FictionBookSpringRepository;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalFictionBook;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBookEntities;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FictionBookRepository - save")
class ITFictionBookRepositorySave {

    @Autowired
    private FictionBookRepository       repository;

    @Autowired
    private FictionBookSpringRepository springRepository;

    @Test
    @DisplayName("When there is an existing game book, and relationships are added, it is persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @MinimalFictionBook
    void testSave_Existing_AddRelationships_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .contains(FictionBookEntities.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are added, it is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @MinimalFictionBook
    void testSave_Existing_AddRelationships_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(FictionBooks.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships also already exist, it is persisted")
    @ValidProfile
    @FullFictionBook
    void testSave_Existing_ExistingRelationships_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .contains(FictionBookEntities.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships also already exist, it is returned")
    @ValidProfile
    @FullFictionBook
    void testSave_Existing_ExistingRelationships_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(FictionBooks.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are removed, it is persisted")
    @ValidProfile
    @FullFictionBook
    void testSave_Existing_RemoveRelationships_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .contains(FictionBookEntities.minimal());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are removed, it is returned")
    @ValidProfile
    @FullFictionBook
    void testSave_Existing_RemoveRelationships_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(FictionBooks.minimal());
    }

    @Test
    @DisplayName("When there are relationships in a game book, but they don't exist, these relationships are not is persisted")
    void testSave_Full_MissingData_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .containsExactly(FictionBookEntities.noRelationships());
    }

    @Test
    @DisplayName("When there are relationships in a game book, but they don't exist, these relationships are not returned")
    void testSave_Full_MissingData_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(FictionBooks.noRelationships());
    }

    @Test
    @DisplayName("When there are relationships the fiction book is persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    void testSave_Full_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .containsExactly(FictionBookEntities.full());
    }

    @Test
    @DisplayName("When there are relationships the persisted fiction book is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    void testSave_Full_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(FictionBooks.full());
    }

    @Test
    @DisplayName("When the game book has a ISBN-13 it is persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    void testSave_Isbn13_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.isbn13();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .containsExactly(FictionBookEntities.isbn13());
    }

    @Test
    @DisplayName("When the game book has a ISBN-13 it is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    void testSave_Isbn13_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.isbn13();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(FictionBooks.isbn13());
    }

    @Test
    @DisplayName("When there is an existing minimal fiction book it is persisted")
    @MinimalFictionBook
    void testSave_Minimal_Existing_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .contains(FictionBookEntities.minimal());
    }

    @Test
    @DisplayName("When there is an existing minimal game book it is returned")
    @MinimalFictionBook
    void testSave_Minimal_Existing_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(FictionBooks.minimal());
    }

    @Test
    @DisplayName("When the minimal game book is saved it is persisted")
    void testSave_Minimal_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(FictionBookEntities.minimal());
    }

    @Test
    @DisplayName("When the minimal game book is saved it is returned")
    void testSave_Minimal_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(FictionBooks.minimal());
    }

}
