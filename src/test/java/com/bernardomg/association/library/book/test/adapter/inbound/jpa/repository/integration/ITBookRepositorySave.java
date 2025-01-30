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

package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.BookSpringRepository;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.Books;
import com.bernardomg.association.library.book.test.configuration.factory.GameBookEntities;
import com.bernardomg.association.library.booktype.test.configuration.data.annotation.ValidBookType;
import com.bernardomg.association.library.gamesystem.test.configuration.data.annotation.ValidGameSystem;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookRepository - save")
class ITBookRepositorySave {

    @Autowired
    private BookRepository       repository;

    @Autowired
    private BookSpringRepository springRepository;

    @Test
    @DisplayName("When there is an existing game book, and relationships are added, it is persisted")
    @NoMembershipPerson
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    @MinimalGameBook
    void testSave_GameBook_Existing_AddRelationships_Persisted() {
        final Book book;

        // GIVEN
        book = Books.full();

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
    @NoMembershipPerson
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    @MinimalGameBook
    void testSave_GameBook_Existing_AddRelationships_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(Books.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships also already exist, it is persisted")
    @NoMembershipPerson
    @FullGameBook
    void testSave_GameBook_Existing_ExistingRelationships_Persisted() {
        final Book book;

        // GIVEN
        book = Books.full();

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
    @NoMembershipPerson
    @FullGameBook
    void testSave_GameBook_Existing_ExistingRelationships_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(Books.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are removed, it is persisted")
    @NoMembershipPerson
    @FullGameBook
    void testSave_GameBook_Existing_RemoveRelationships_Persisted() {
        final Book book;

        // GIVEN
        book = Books.minimal();

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
    @NoMembershipPerson
    @FullGameBook
    void testSave_GameBook_Existing_RemoveRelationships_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(Books.minimal());
    }

    @Test
    @DisplayName("When there are relationships in a game book, but they don't exist, these relationships are not is persisted")
    void testSave_GameBook_Full_MissingData_Persisted() {
        final Book book;

        // GIVEN
        book = Books.full();

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
    void testSave_GameBook_Full_MissingData_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(Books.noRelationships());
    }

    @Test
    @DisplayName("When there are relationships the game book is persisted")
    @NoMembershipPerson
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_GameBook_Full_Persisted() {
        final Book book;

        // GIVEN
        book = Books.full();

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
    @NoMembershipPerson
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_GameBook_Full_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(Books.full());
    }

    @Test
    @DisplayName("When the game book has a ISBN-13 it is persisted")
    @NoMembershipPerson
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_GameBook_Isbn13_Persisted() {
        final Book book;

        // GIVEN
        book = Books.isbn13();

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
    @NoMembershipPerson
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_GameBook_Isbn13_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.isbn13();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(Books.isbn13());
    }

    @Test
    @DisplayName("When there is an existing minimal game book it is persisted")
    @MinimalGameBook
    void testSave_GameBook_Minimal_Existing_Persisted() {
        final Book book;

        // GIVEN
        book = Books.minimal();

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
    void testSave_GameBook_Minimal_Existing_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(Books.minimal());
    }

    @Test
    @DisplayName("When the minimal game book is saved it is persisted")
    void testSave_GameBook_Minimal_Persisted() {
        final Book book;

        // GIVEN
        book = Books.minimal();

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
    void testSave_GameBook_Minimal_Returned() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .isEqualTo(Books.minimal());
    }

}
