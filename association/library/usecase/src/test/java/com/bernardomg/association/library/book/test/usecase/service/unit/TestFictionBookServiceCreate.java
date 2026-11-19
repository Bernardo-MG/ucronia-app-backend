
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
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.DonorRepository;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.DonorConstants;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultFictionBookService;
import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFictionBookService - create")
class TestFictionBookServiceCreate {

    @Mock
    private AuthorRepository          authorRepository;

    @Mock
    private FictionBookRepository     bookRepository;

    @Mock
    private DonorRepository           donorRepository;

    @Mock
    private PublisherRepository       publisherRepository;

    @InjectMocks
    private DefaultFictionBookService service;

    @Test
    @DisplayName("When persisting a book with a duplicated author, then the duplication is removed")
    void testCreate_DuplicatedAuthor() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.duplicatedAuthor();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(FictionBooks.toCreate());
    }

    @Test
    @DisplayName("When persisting a book with a duplicated donor, then the duplication is removed")
    void testCreate_DuplicatedDonor() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.duplicatedDonor();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(FictionBooks.toCreate());
    }

    @Test
    @DisplayName("When persisting a book with a duplicated publisher, then the duplication is removed")
    void testCreate_DuplicatedPublisher() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.duplicatedPublisher();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(FictionBooks.toCreate());
    }

    @Test
    @DisplayName("When there is a book with an empty ISBN, then the unique check is not applied")
    void testCreate_EmptyIsbn_IgnoreUnique() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.emptyIsbn();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository, Mockito.never()).existsByIsbn(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("When there is a book with an empty title, then an exception is thrown")
    void testCreate_EmptyTitle() {
        final ThrowingCallable action;
        final FictionBook      book;

        // GIVEN
        book = FictionBooks.emptyTitle();

        // WHEN
        action = () -> service.create(book);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "title", FictionBooks.emptyTitle()
            .title()));
    }

    @Test
    @DisplayName("When there is a book with an existing ISBN, then an exception is thrown")
    void testCreate_ExistingIsbn() {
        final ThrowingCallable action;
        final FictionBook      book;

        // GIVEN
        book = FictionBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        given(bookRepository.existsByIsbn(BookConstants.ISBN_10)).willReturn(true);

        // WHEN
        action = () -> service.create(book);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("existing", "isbn", BookConstants.ISBN_10));
    }

    @Test
    @DisplayName("When there is a book with an invalid ISBN, then an exception is thrown")
    void testCreate_InvalidIsbn() {
        final ThrowingCallable action;
        final FictionBook      book;

        // GIVEN
        book = FictionBooks.invalidIsbn();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        given(bookRepository.existsByIsbn(BookConstants.INVALID_ISBN)).willReturn(false);

        // WHEN
        action = () -> service.create(book);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("invalid", "isbn", BookConstants.INVALID_ISBN));
    }

    @Test
    @DisplayName("When there is an ISBN-14, then the book is persisted")
    void testCreate_Isbn13_PersistedData() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.isbn13();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(FictionBooks.toCreateIsbn13());
    }

    @Test
    @DisplayName("When persisting a book for a not existing author, then an exception is thrown")
    void testCreate_NoAuthor() {
        final FictionBook      book;
        final ThrowingCallable action;

        // GIVEN
        book = FictionBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When persisting a book for a not existing author, then an exception is thrown")
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing donor, then an exception is thrown")
    void testCreate_NoDonor() {
        final FictionBook      book;
        final ThrowingCallable action;

        // GIVEN
        book = FictionBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When persisting a book for a not existing donor, then an exception is thrown")
            .isInstanceOf(MissingDonorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing publisher, then an exception is thrown")
    void testCreate_NoPublisher() {
        final FictionBook      book;
        final ThrowingCallable action;

        // GIVEN
        book = FictionBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When persisting a book for a not existing publisher, then an exception is thrown")
            .isInstanceOf(MissingPublisherException.class);
    }

    @Test
    @DisplayName("When there is a valid book, which has no relationships, then the book is persisted")
    void testCreate_NoRelationship_PersistedData() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(FictionBooks.toCreateMinimal());
    }

    @Test
    @DisplayName("When there is a book with padded title, then the book is persisted")
    void testCreate_Padded_PersistedData() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.padded();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(FictionBooks.toCreate());
    }

    @Test
    @DisplayName("When there is a valid book, then the book is persisted")
    void testCreate_PersistedData() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(FictionBooks.toCreate());
    }

    @Test
    @DisplayName("When there is a valid book, then the created book is returned")
    void testCreate_ReturnedData() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        given(bookRepository.save(FictionBooks.toCreate())).willReturn(FictionBooks.full());

        // WHEN
        created = service.create(book);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid book, then the created book is returned")
            .isEqualTo(FictionBooks.full());
    }

}
