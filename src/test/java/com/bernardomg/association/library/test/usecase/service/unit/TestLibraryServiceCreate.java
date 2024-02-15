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

package com.bernardomg.association.library.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.test.config.factory.Authors;
import com.bernardomg.association.library.test.config.factory.BookTypes;
import com.bernardomg.association.library.test.config.factory.Books;
import com.bernardomg.association.library.test.config.factory.GameSystems;
import com.bernardomg.association.library.usecase.service.DefaultLibraryService;

@ExtendWith(MockitoExtension.class)
@DisplayName("LibraryService - create")
class TestLibraryServiceCreate {

    @Mock
    private AuthorRepository      authorRepository;

    @Mock
    private BookRepository        bookRepository;

    @Mock
    private BookTypeRepository    bookTypeRepository;

    @Mock
    private GameSystemRepository  gameSystemRepository;

    @InjectMocks
    private DefaultLibraryService service;

    public TestLibraryServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a valid author, the author is persisted")
    void testCreateAuthor_PersistedData() {
        final Author author;

        // GIVEN
        author = Authors.valid();

        // WHEN
        service.createAuthor(author);

        // THEN
        verify(authorRepository).save(Authors.valid());
    }

    @Test
    @DisplayName("With a valid author, the created author is returned")
    void testCreateAuthor_ReturnedData() {
        final Author author;
        final Author created;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.save(Authors.valid())).willReturn(Authors.valid());

        // WHEN
        created = service.createAuthor(author);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Authors.valid());
    }

    @Test
    @DisplayName("With a valid book, the book is persisted")
    void testCreateBook_PersistedData() {
        final Book book;

        // GIVEN
        book = Books.valid();

        // WHEN
        service.createBook(book);

        // THEN
        verify(bookRepository).save(Books.valid());
    }

    @Test
    @DisplayName("With a valid book, the created book is returned")
    void testCreateBook_ReturnedData() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.valid();

        given(bookRepository.save(Books.valid())).willReturn(Books.valid());

        // WHEN
        created = service.createBook(book);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Books.valid());
    }

    @Test
    @DisplayName("With a valid book type, the book is persisted")
    void testCreateBookType_PersistedData() {
        final BookType book;

        // GIVEN
        book = BookTypes.valid();

        // WHEN
        service.createBookType(book);

        // THEN
        verify(bookTypeRepository).save(BookTypes.valid());
    }

    @Test
    @DisplayName("With a valid book type, the created book is returned")
    void testCreateBookType_ReturnedData() {
        final BookType book;
        final BookType created;

        // GIVEN
        book = BookTypes.valid();

        given(bookTypeRepository.save(BookTypes.valid())).willReturn(BookTypes.valid());

        // WHEN
        created = service.createBookType(book);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(BookTypes.valid());
    }

    @Test
    @DisplayName("With a valid game system, the game system is persisted")
    void testCreateSystem_PersistedData() {
        final GameSystem book;

        // GIVEN
        book = GameSystems.valid();

        // WHEN
        service.createGameSystem(book);

        // THEN
        verify(gameSystemRepository).save(GameSystems.valid());
    }

    @Test
    @DisplayName("With a valid game system, the created game system is returned")
    void testCreateSystem_ReturnedData() {
        final GameSystem book;
        final GameSystem created;

        // GIVEN
        book = GameSystems.valid();

        given(gameSystemRepository.save(GameSystems.valid())).willReturn(GameSystems.valid());

        // WHEN
        created = service.createGameSystem(book);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(GameSystems.valid());
    }

}
