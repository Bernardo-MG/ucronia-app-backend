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

import com.bernardomg.association.contact.test.configuration.data.annotation.NoMembershipContact;
import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.GameBookSpringRepository;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.GameBookEntities;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.booktype.test.configuration.data.annotation.ValidBookType;
import com.bernardomg.association.library.gamesystem.test.configuration.data.annotation.ValidGameSystem;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("GameBookRepository - save")
class ITGameBookRepositorySave {

    @Autowired
    private GameBookRepository       repository;

    @Autowired
    private GameBookSpringRepository springRepository;

    @Test
    @DisplayName("When there is an existing game book, and relationships are added, it is persisted")
    @NoMembershipContact
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    @MinimalGameBook
    void testSave_Existing_AddRelationships_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .contains(GameBookEntities.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are added, it is returned")
    @NoMembershipContact
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    @MinimalGameBook
    void testSave_Existing_AddRelationships_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(GameBooks.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships also already exist, it is persisted")
    @NoMembershipContact
    @FullGameBook
    void testSave_Existing_ExistingRelationships_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .contains(GameBookEntities.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships also already exist, it is returned")
    @NoMembershipContact
    @FullGameBook
    void testSave_Existing_ExistingRelationships_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(GameBooks.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are removed, it is persisted")
    @NoMembershipContact
    @FullGameBook
    void testSave_Existing_RemoveRelationships_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .contains(GameBookEntities.minimal());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are removed, it is returned")
    @NoMembershipContact
    @FullGameBook
    void testSave_Existing_RemoveRelationships_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(GameBooks.minimal());
    }

    @Test
    @DisplayName("When there are relationships in a game book, but they don't exist, these relationships are not is persisted")
    void testSave_Full_MissingData_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .containsExactly(GameBookEntities.noRelationships());
    }

    @Test
    @DisplayName("When there are relationships in a game book, but they don't exist, these relationships are not returned")
    void testSave_Full_MissingData_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(GameBooks.noRelationships());
    }

    @Test
    @DisplayName("When there are relationships the game book is persisted")
    @NoMembershipContact
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_Full_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .containsExactly(GameBookEntities.full());
    }

    @Test
    @DisplayName("When there are relationships the persisted game book is returned")
    @NoMembershipContact
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_Full_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(GameBooks.full());
    }

    @Test
    @DisplayName("When the game book has a ISBN-13 it is persisted")
    @NoMembershipContact
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_Isbn13_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.isbn13();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "authors.id", "bookType.id", "donors.id",
                "gameSystem.id", "publishers.id")
            .containsExactly(GameBookEntities.isbn13());
    }

    @Test
    @DisplayName("When the game book has a ISBN-13 it is returned")
    @NoMembershipContact
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_Isbn13_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.isbn13();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(GameBooks.isbn13());
    }

    @Test
    @DisplayName("When there is an existing minimal game book it is persisted")
    @MinimalGameBook
    void testSave_Minimal_Existing_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .contains(GameBookEntities.minimal());
    }

    @Test
    @DisplayName("When there is an existing minimal game book it is returned")
    @MinimalGameBook
    void testSave_Minimal_Existing_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(GameBooks.minimal());
    }

    @Test
    @DisplayName("When the minimal game book is saved it is persisted")
    void testSave_Minimal_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(GameBookEntities.minimal());
    }

    @Test
    @DisplayName("When the minimal game book is saved it is returned")
    void testSave_Minimal_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(GameBooks.minimal());
    }

}
