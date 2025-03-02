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
import com.bernardomg.association.library.book.domain.exception.MissingDonorException;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultGameBookService;
import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.association.library.gamesystem.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemConstants;
import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService - create")
class TestGameBookServiceCreate {

    @Mock
    private AuthorRepository       authorRepository;

    @Mock
    private GameBookRepository     bookRepository;

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @Mock
    private GameSystemRepository   gameSystemRepository;

    @Mock
    private PersonRepository       personRepository;

    @Mock
    private PublisherRepository    publisherRepository;

    @InjectMocks
    private DefaultGameBookService service;

    public TestGameBookServiceCreate() {
        super();
    }

    @Test
    @DisplayName("When persisting a book with a duplicated author, the duplication is removed")
    void testCreate_DuplicatedAuthor() {
        final GameBook book;

        // GIVEN
        book = GameBooks.duplicatedAuthor();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(bookRepository.findNextNumber()).willReturn(BookConstants.NUMBER);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.full());
    }

    @Test
    @DisplayName("When persisting a book with a duplicated donor, the duplication is removed")
    void testCreate_DuplicatedDonor() {
        final GameBook book;

        // GIVEN
        book = GameBooks.duplicatedDonor();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(bookRepository.findNextNumber()).willReturn(BookConstants.NUMBER);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.full());
    }

    @Test
    @DisplayName("When persisting a book with a duplicated publisher, the duplication is removed")
    void testCreate_DuplicatedPublisher() {
        final GameBook book;

        // GIVEN
        book = GameBooks.duplicatedPublisher();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(bookRepository.findNextNumber()).willReturn(BookConstants.NUMBER);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.full());
    }

    @Test
    @DisplayName("With a book with an empty ISBN, the unique check is not applied")
    void testCreate_EmptyIsbn_IgnoreUnique() {
        final GameBook book;

        // GIVEN
        book = GameBooks.emptyIsbn();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository, Mockito.never()).existsByIsbn(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("With a book with an empty title, an exception is thrown")
    void testCreate_EmptyTitle() {
        final ThrowingCallable execution;
        final GameBook         book;

        // GIVEN
        book = GameBooks.emptyTitle();

        // WHEN
        execution = () -> service.create(book);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, new FieldFailure("empty", "title", GameBooks.emptyTitle()
            .title()));
    }

    @Test
    @DisplayName("With a book with an existing ISBN, an exception is thrown")
    void testCreate_ExistingIsbn() {
        final ThrowingCallable execution;
        final GameBook         book;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        given(bookRepository.existsByIsbn(BookConstants.ISBN_10)).willReturn(true);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "isbn", BookConstants.ISBN_10));
    }

    @Test
    @DisplayName("With a book with an invalid ISBN, an exception is thrown")
    void testCreate_InvalidIsbn() {
        final ThrowingCallable execution;
        final GameBook         book;

        // GIVEN
        book = GameBooks.invalidIsbn();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        given(bookRepository.existsByIsbn(BookConstants.INVALID_ISBN)).willReturn(false);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("invalid", "isbn", BookConstants.INVALID_ISBN));
    }

    @Test
    @DisplayName("With an ISBN-14, the book is persisted")
    void testCreate_Isbn13_PersistedData() {
        final GameBook book;

        // GIVEN
        book = GameBooks.isbn13();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(bookRepository.findNextNumber()).willReturn(BookConstants.NUMBER);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.isbn13());
    }

    @Test
    @DisplayName("When persisting a book for a not existing author, an exception is thrown")
    void testCreate_NoAuthor() {
        final GameBook         book;
        final ThrowingCallable execution;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing book type, an exception is thrown")
    void testCreate_NoBookType() {
        final GameBook         book;
        final ThrowingCallable execution;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookTypeException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing donor, an exception is thrown")
    void testCreate_NoDonor() {
        final GameBook         book;
        final ThrowingCallable execution;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingDonorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing game system, an exception is thrown")
    void testCreate_NoGameSystem() {
        final GameBook         book;
        final ThrowingCallable execution;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingGameSystemException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing publisher, an exception is thrown")
    void testCreate_NoPublisher() {
        final GameBook         book;
        final ThrowingCallable execution;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingPublisherException.class);
    }

    @Test
    @DisplayName("With a valid book, which has no relationships, the book is persisted")
    void testCreate_NoRelationship_PersistedData() {
        final GameBook book;

        // GIVEN
        book = GameBooks.minimal();

        given(bookRepository.findNextNumber()).willReturn(BookConstants.NUMBER);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.minimal());
    }

    @Test
    @DisplayName("With a book with padded title, the book is persisted")
    void testCreate_Padded_PersistedData() {
        final GameBook book;

        // GIVEN
        book = GameBooks.padded();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(bookRepository.findNextNumber()).willReturn(BookConstants.NUMBER);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.full());
    }

    @Test
    @DisplayName("With a valid book, the book is persisted")
    void testCreate_PersistedData() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(bookRepository.findNextNumber()).willReturn(BookConstants.NUMBER);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.full());
    }

    @Test
    @DisplayName("With a valid book, the created book is returned")
    void testCreate_ReturnedData() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(bookRepository.findNextNumber()).willReturn(BookConstants.NUMBER);

        given(bookRepository.save(GameBooks.full())).willReturn(GameBooks.full());

        // WHEN
        created = service.create(book);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(GameBooks.full());
    }

}
