
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
import com.bernardomg.association.library.book.domain.repository.DonorRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.DonorConstants;
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
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameBookService - create")
class TestGameBookServiceCreate {

    @Mock
    private AuthorRepository       authorRepository;

    @Mock
    private GameBookRepository     bookRepository;

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @Mock
    private DonorRepository        donorRepository;

    @Mock
    private GameSystemRepository   gameSystemRepository;

    @Mock
    private PublisherRepository    publisherRepository;

    @InjectMocks
    private DefaultGameBookService service;

    @Test
    @DisplayName("When persisting a book with a duplicated author, then the duplication is removed")
    void testCreate_DuplicatedAuthor() {
        final GameBook book;

        // GIVEN
        book = GameBooks.duplicatedAuthor();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.toCreate());
    }

    @Test
    @DisplayName("When persisting a book with a duplicated donor, then the duplication is removed")
    void testCreate_DuplicatedDonor() {
        final GameBook book;

        // GIVEN
        book = GameBooks.duplicatedDonor();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.toCreate());
    }

    @Test
    @DisplayName("When persisting a book with a duplicated publisher, then the duplication is removed")
    void testCreate_DuplicatedPublisher() {
        final GameBook book;

        // GIVEN
        book = GameBooks.duplicatedPublisher();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.toCreate());
    }

    @Test
    @DisplayName("When there is a book with an empty ISBN, then the unique check is not applied")
    void testCreate_EmptyIsbn_IgnoreUnique() {
        final GameBook book;

        // GIVEN
        book = GameBooks.emptyIsbn();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
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
        final GameBook         book;

        // GIVEN
        book = GameBooks.emptyTitle();

        // WHEN
        action = () -> service.create(book);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "title", GameBooks.emptyTitle()
            .title()));
    }

    @Test
    @DisplayName("When there is a book with an existing ISBN, then an exception is thrown")
    void testCreate_ExistingIsbn() {
        final ThrowingCallable action;
        final GameBook         book;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
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
        final GameBook         book;

        // GIVEN
        book = GameBooks.invalidIsbn();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
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
        final GameBook book;

        // GIVEN
        book = GameBooks.isbn13();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.toCreateIsbn13());
    }

    @Test
    @DisplayName("When persisting a book for a not existing author, then an exception is thrown")
    void testCreate_NoAuthor() {
        final GameBook         book;
        final ThrowingCallable action;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When persisting a book for a not existing author, then an exception is thrown")
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing book type, then an exception is thrown")
    void testCreate_NoBookType() {
        final GameBook         book;
        final ThrowingCallable action;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When persisting a book for a not existing book type, then an exception is thrown")
            .isInstanceOf(MissingBookTypeException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing donor, then an exception is thrown")
    void testCreate_NoDonor() {
        final GameBook         book;
        final ThrowingCallable action;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When persisting a book for a not existing donor, then an exception is thrown")
            .isInstanceOf(MissingDonorException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing game system, then an exception is thrown")
    void testCreate_NoGameSystem() {
        final GameBook         book;
        final ThrowingCallable action;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.create(book);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When persisting a book for a not existing game system, then an exception is thrown")
            .isInstanceOf(MissingGameSystemException.class);
    }

    @Test
    @DisplayName("When persisting a book for a not existing publisher, then an exception is thrown")
    void testCreate_NoPublisher() {
        final GameBook         book;
        final ThrowingCallable action;

        // GIVEN
        book = GameBooks.full();

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
        final GameBook book;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.toCretaeMinimal());
    }

    @Test
    @DisplayName("When there is a book with padded title, then the book is persisted")
    void testCreate_Padded_PersistedData() {
        final GameBook book;

        // GIVEN
        book = GameBooks.padded();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.toCreate());
    }

    @Test
    @DisplayName("When there is a valid book, then the book is persisted")
    void testCreate_PersistedData() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.create(book);

        // THEN
        verify(bookRepository).save(GameBooks.toCreate());
    }

    @Test
    @DisplayName("When there is a valid book, then the created book is returned")
    void testCreate_ReturnedData() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(publisherRepository.exists(PublisherConstants.NUMBER)).willReturn(true);
        given(gameSystemRepository.exists(GameSystemConstants.NUMBER)).willReturn(true);
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);
        given(donorRepository.exists(DonorConstants.NUMBER)).willReturn(true);

        given(bookRepository.save(GameBooks.toCreate())).willReturn(GameBooks.full());

        // WHEN
        created = service.create(book);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid book, then the created book is returned")
            .isEqualTo(GameBooks.full());
    }

}
