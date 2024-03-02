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
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.test.config.factory.AuthorConstants;
import com.bernardomg.association.library.test.config.factory.BookTypeConstants;
import com.bernardomg.association.library.test.config.factory.Books;
import com.bernardomg.association.library.test.config.factory.GameSystemConstants;
import com.bernardomg.association.library.usecase.service.DefaultBookService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService - create")
class TestBookServiceCreate {

    @Mock
    private AuthorRepository     authorRepository;

    @Mock
    private BookRepository       bookRepository;

    @Mock
    private BookTypeRepository   bookTypeRepository;

    @Mock
    private GameSystemRepository gameSystemRepository;

    @InjectMocks
    private DefaultBookService   service;

    public TestBookServiceCreate() {
        super();
    }

    @Test
    @DisplayName("When persisting a book for a not existing author, an exception is thrown")
    void testCreateBook_NoAuthor_Exception() {
        final Book             book;
        final ThrowingCallable execution;

        // GIVEN
        book = Books.full();

        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(false);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing book type, an exception is thrown")
    void testCreateBook_NoBookType_Exception() {
        final Book             book;
        final ThrowingCallable execution;

        // GIVEN
        book = Books.full();

        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NAME)).willReturn(false);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookTypeException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing game system, an exception is thrown")
    void testCreateBook_NoGameSystem_Exception() {
        final Book             book;
        final ThrowingCallable execution;

        // GIVEN
        book = Books.full();

        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(false);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingGameSystemException.class);
    }

    @Test
    @DisplayName("With a valid book, the book is persisted")
    void testCreateBook_PersistedData() {
        final Book book;

        // GIVEN
        book = Books.full();

        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NAME)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(Books.full());
    }

    @Test
    @DisplayName("With a valid book, the created book is returned")
    void testCreateBook_ReturnedData() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.full();

        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NAME)).willReturn(true);

        given(bookRepository.save(Books.full())).willReturn(Books.full());

        // WHEN
        created = service.create(book);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Books.full());
    }

}
