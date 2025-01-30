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

package com.bernardomg.association.library.book.test.usecase.service.unit;

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

import com.bernardomg.association.library.author.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorConstants;
import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultFictionBookService;
import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService - update")
class TestFictionBookServiceUpdate {

    @Mock
    private AuthorRepository          authorRepository;

    @Mock
    private FictionBookRepository     bookRepository;

    @Mock
    private PersonRepository          personRepository;

    @Mock
    private PublisherRepository       publisherRepository;

    @InjectMocks
    private DefaultFictionBookService service;

    public TestFictionBookServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("When persisting a book with a duplicated author, the duplication is removed")
    void testUpdate_DuplicatedAuthor() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.duplicatedAuthor();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(BookConstants.NUMBER, book);

        // THEN
        verify(bookRepository).save(FictionBooks.full());
    }

    @Test
    @DisplayName("When persisting a book with a duplicated donor, the duplication is removed")
    void testUpdate_DuplicatedDonor() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.duplicatedDonor();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(BookConstants.NUMBER, book);

        // THEN
        verify(bookRepository).save(FictionBooks.full());
    }

    @Test
    @DisplayName("When persisting a book with a duplicated publisher, the duplication is removed")
    void testUpdate_DuplicatedPublisher() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.duplicatedPublisher();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(BookConstants.NUMBER, book);

        // THEN
        verify(bookRepository).save(FictionBooks.full());
    }

    @Test
    @DisplayName("With a book with an empty ISBN, the unique check is not applied")
    void testUpdate_EmptyIsbn_IgnoreUnique() {
        final FictionBook book = FictionBooks.emptyIsbn();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(BookConstants.NUMBER, book);

        // THEN
        verify(bookRepository, Mockito.never()).existsByIsbnForAnother(ArgumentMatchers.anyLong(),
            ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("With a book with an empty title, an exception is thrown")
    void testUpdate_EmptyTitle() {
        final ThrowingCallable execution;
        final FictionBook      book;

        // GIVEN
        book = FictionBooks.emptyTitle();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("title", "empty", FictionBooks.emptyTitle()
            .title()));
    }

    @Test
    @DisplayName("With a book with an existing ISBN, an exception is thrown")
    void testUpdate_ExistingIsbn() {
        final ThrowingCallable execution;
        final FictionBook      book;

        // GIVEN
        book = FictionBooks.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        given(bookRepository.existsByIsbnForAnother(BookConstants.NUMBER, BookConstants.ISBN_10)).willReturn(true);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            FieldFailure.of("isbn", "existing", BookConstants.ISBN_10));
    }

    @Test
    @DisplayName("With a book with an invalid ISBN, an exception is thrown")
    void testUpdate_InvalidIsbn() {
        final ThrowingCallable execution;
        final FictionBook      book;

        // GIVEN
        book = FictionBooks.invalidIsbn();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        given(bookRepository.existsByIsbnForAnother(BookConstants.NUMBER, BookConstants.INVALID_ISBN))
            .willReturn(false);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            FieldFailure.of("isbn", "invalid", BookConstants.INVALID_ISBN));
    }

    @Test
    @DisplayName("When persisting a book with an invalid language code, an exception is thrown")
    void testUpdate_InvalidLanguage() {
        final FictionBook      book;
        final ThrowingCallable execution;

        // GIVEN
        book = FictionBooks.invalidLanguage();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("language", "invalid", "abc"));
    }

    @Test
    @DisplayName("When persisting a book for a not existing author, an exception is thrown")
    void testUpdate_NoAuthor() {
        final FictionBook      book;
        final ThrowingCallable execution;

        // GIVEN
        book = FictionBooks.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing donor, an exception is thrown")
    void testUpdate_NoDonor() {
        final FictionBook      book;
        final ThrowingCallable execution;

        // GIVEN
        book = FictionBooks.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing publisher, an exception is thrown")
    void testUpdate_NoPublisher() {
        final FictionBook      book;
        final ThrowingCallable execution;

        // GIVEN
        book = FictionBooks.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingPublisherException.class);
    }

    @Test
    @DisplayName("With a valid book, which has no relationships, the book is persisted")
    void testUpdate_NoRelationship_PersistedData() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.minimal();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(BookConstants.NUMBER, book);

        // THEN
        verify(bookRepository).save(FictionBooks.minimal());
    }

    @Test
    @DisplayName("When persisting a book which doesn't exist, an exception is thrown")
    void testUpdate_NotExisting() {
        final FictionBook      book;
        final ThrowingCallable execution;

        // GIVEN
        book = FictionBooks.full();

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
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(BookConstants.NUMBER, book);

        // THEN
        verify(bookRepository).save(FictionBooks.full());
    }

    @Test
    @DisplayName("With a valid book, the created book is returned")
    void testUpdate_ReturnedData() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        given(bookRepository.exists(BookConstants.NUMBER)).willReturn(true);
        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        given(bookRepository.save(FictionBooks.full())).willReturn(FictionBooks.full());

        // WHEN
        created = service.update(BookConstants.NUMBER, book);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(FictionBooks.full());
    }

}
