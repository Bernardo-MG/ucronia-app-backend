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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.exception.MissingBookException;
import com.bernardomg.association.library.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.domain.repository.PublisherRepository;
import com.bernardomg.association.library.test.config.factory.AuthorConstants;
import com.bernardomg.association.library.test.config.factory.BookConstants;
import com.bernardomg.association.library.test.config.factory.BookTypeConstants;
import com.bernardomg.association.library.test.config.factory.Books;
import com.bernardomg.association.library.test.config.factory.GameSystemConstants;
import com.bernardomg.association.library.test.config.factory.PublisherConstants;
import com.bernardomg.association.library.usecase.service.DefaultBookService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService - update")
class TestBookServiceUpdate {

    @Mock
    private AuthorRepository     authorRepository;

    @Mock
    private BookRepository       bookRepository;

    @Mock
    private BookTypeRepository   bookTypeRepository;

    @Mock
    private GameSystemRepository gameSystemRepository;

    @Mock
    private PublisherRepository  publisherRepository;

    @InjectMocks
    private DefaultBookService   service;

    public TestBookServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With a book with an empty ISBN, the unique check is not applied")
    void testUpdate_EmptyIsbn_IgnoreUnique() {
        final Book book;

        // GIVEN
        book = Books.emptyIsbn();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NAME)).willReturn(true);

        // WHEN
        service.update(BookConstants.NUMBER, book);

        // THEN
        verify(bookRepository, Mockito.never()).existsByIsbn(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("With a book with an empty title, an exception is thrown")
    void testUpdate_EmptyTitle() {
        final ThrowingCallable execution;
        final Book             book;

        // GIVEN
        book = Books.emptyTitle();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("title", "empty", " "));
    }

    @Test
    @DisplayName("With a book with an existing ISBN, an exception is thrown")
    void testUpdate_ExistingIsbn() {
        final ThrowingCallable execution;
        final Book             book;

        // GIVEN
        book = Books.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NAME)).willReturn(true);

        given(bookRepository.existsByIsbn(BookConstants.NUMBER, BookConstants.ISBN)).willReturn(true);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("isbn", "existing", BookConstants.ISBN));
    }

    @Test
    @DisplayName("When persisting a book for a not existing author, an exception is thrown")
    void testUpdate_NoAuthor() {
        final Book             book;
        final ThrowingCallable execution;

        // GIVEN
        book = Books.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(false);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing book type, an exception is thrown")
    void testUpdate_NoBookType() {
        final Book             book;
        final ThrowingCallable execution;

        // GIVEN
        book = Books.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NAME)).willReturn(false);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookTypeException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing game system, an exception is thrown")
    void testUpdate_NoGameSystem() {
        final Book             book;
        final ThrowingCallable execution;

        // GIVEN
        book = Books.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(false);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingGameSystemException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing publisher, an exception is thrown")
    void testUpdate_NoPublisher() {
        final Book             book;
        final ThrowingCallable execution;

        // GIVEN
        book = Books.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NAME)).willReturn(false);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingPublisherException.class);
    }

    @Test
    @DisplayName("With a valid book, which has no relationships, the book is persisted")
    void testUpdate_NoRelationship_PersistedData() {
        final Book book;

        // GIVEN
        book = Books.minimal();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(BookConstants.NUMBER, book);

        // THEN
        verify(bookRepository).save(Books.minimal());
    }

    @Test
    @DisplayName("When persisting a book which doesn't exist, an exception is thrown")
    void testUpdate_NotExisting() {
        final Book             book;
        final ThrowingCallable execution;

        // GIVEN
        book = Books.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookException.class);
    }

    @Test
    @DisplayName("With a valid book, the book is persisted")
    void testUpdate_PersistedData() {
        final Book book;

        // GIVEN
        book = Books.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NAME)).willReturn(true);

        // WHEN
        service.update(BookConstants.NUMBER, book);

        // THEN
        verify(bookRepository).save(Books.full());
    }

    @Test
    @DisplayName("With a valid book, the created book is returned")
    void testUpdate_ReturnedData() {
        final Book book;
        final Book created;

        // GIVEN
        book = Books.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NAME)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NAME)).willReturn(true);

        given(bookRepository.save(Books.full())).willReturn(Books.full());

        // WHEN
        created = service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Books.full());
    }

}
