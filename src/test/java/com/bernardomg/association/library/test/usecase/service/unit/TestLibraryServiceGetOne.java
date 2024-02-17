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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.exception.MissingBookException;
import com.bernardomg.association.library.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.test.config.factory.AuthorConstants;
import com.bernardomg.association.library.test.config.factory.Authors;
import com.bernardomg.association.library.test.config.factory.BookConstants;
import com.bernardomg.association.library.test.config.factory.BookTypes;
import com.bernardomg.association.library.test.config.factory.Books;
import com.bernardomg.association.library.test.config.factory.GameSystems;
import com.bernardomg.association.library.usecase.service.DefaultLibraryService;

@ExtendWith(MockitoExtension.class)
@DisplayName("LibraryService - get one")
class TestLibraryServiceGetOne {

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

    public TestLibraryServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("When there is an author, it is returned")
    void testGetOneAuthor() {
        final Optional<Author> author;

        // GIVEN
        given(authorRepository.findOne(AuthorConstants.NAME)).willReturn(Optional.of(Authors.valid()));

        // WHEN
        author = service.getOneAuthor(AuthorConstants.NAME);

        // THEN
        Assertions.assertThat(author)
            .contains(Authors.valid());
    }

    @Test
    @DisplayName("When there are no authors, an exception is thrown")
    void testGetOneAuthor_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(authorRepository.findOne(AuthorConstants.NAME)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOneAuthor(AuthorConstants.NAME);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("When there is a book, it is returned")
    void testGetOneBook() {
        final Optional<Book> book;

        // GIVEN
        given(bookRepository.findOne(BookConstants.ISBN)).willReturn(Optional.of(Books.valid()));

        // WHEN
        book = service.getOneBook(BookConstants.ISBN);

        // THEN
        Assertions.assertThat(book)
            .contains(Books.valid());
    }

    @Test
    @DisplayName("When there are no books, an exception is thrown")
    void testGetOneBook_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(bookRepository.findOne(BookConstants.ISBN)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOneBook(BookConstants.ISBN);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookException.class);
    }

    @Test
    @DisplayName("When there is a book type, it is returned")
    void testGetOneBookType() {
        final Optional<BookType> bookType;

        // GIVEN
        given(bookTypeRepository.findOne(AuthorConstants.NAME)).willReturn(Optional.of(BookTypes.valid()));

        // WHEN
        bookType = service.getOneBookType(AuthorConstants.NAME);

        // THEN
        Assertions.assertThat(bookType)
            .contains(BookTypes.valid());
    }

    @Test
    @DisplayName("When there are no book types, an exception is thrown")
    void testGetOneBookType_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(bookTypeRepository.findOne(AuthorConstants.NAME)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOneBookType(AuthorConstants.NAME);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookTypeException.class);
    }

    @Test
    @DisplayName("When there is a game system, it is returned")
    void testGetOneGameSystem() {
        final Optional<GameSystem> gameSystem;

        // GIVEN
        given(gameSystemRepository.findOne(AuthorConstants.NAME)).willReturn(Optional.of(GameSystems.valid()));

        // WHEN
        gameSystem = service.getOneGameSystem(AuthorConstants.NAME);

        // THEN
        Assertions.assertThat(gameSystem)
            .contains(GameSystems.valid());
    }

    @Test
    @DisplayName("When there are no game systems, an exception is thrown")
    void testGetOneGameSystem_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(gameSystemRepository.findOne(AuthorConstants.NAME)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOneGameSystem(AuthorConstants.NAME);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingGameSystemException.class);
    }
}
