
package com.bernardomg.association.library.lending.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.Books;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.library.lending.usecase.service.DefaultBookLendingService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultBookLendingService - lendBook")
class TestBookLendingServiceLendBook {

    @Mock
    private BookLendingRepository     bookLendingRepository;

    @Mock
    private BookRepository            bookRepository;

    @InjectMocks
    private DefaultBookLendingService service;

    @Test
    @DisplayName("When lending a book which is already lent, then an exception is thrown")
    void testLendBook_AlreadyLent_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(bookLendingRepository.findLent(BookConstants.NUMBER)).willReturn(Optional.of(BookLendings.lent()));

        // WHEN
        action = () -> service.lendBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "lendingDate", BookConstants.LENT_DATE));
    }

    @Test
    @DisplayName("When lending a book which is already lent for another profile, then an exception is thrown")
    void testLendBook_AlreadyLentForAnother_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(bookLendingRepository.findLent(BookConstants.NUMBER))
            .willReturn(Optional.of(BookLendings.lentAlternativeProfile()));

        // WHEN
        action = () -> service.lendBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "lendingDate", BookConstants.LENT_DATE));
    }

    @Test
    @DisplayName("When lending a book before the last return date, then an exception is thrown")
    void testLendBook_BeforeLastReturn_Exception() {
        final ThrowingCallable action;
        final Instant          date;

        // GIVEN
        date = LocalDate.ofInstant(BookConstants.RETURNED_DATE, ZoneOffset.UTC)
            .minusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(bookLendingRepository.findReturned(BookConstants.NUMBER))
            .willReturn(Optional.of(BookLendings.returned()));

        // WHEN
        action = () -> service.lendBook(BookConstants.NUMBER, BookConstants.BORROWER, date);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("invalid", "lendingDate", date));
    }

    @Test
    @DisplayName("When lending a book in the future, then an exception is thrown")
    void testLendBook_InFuture_Exception() {
        final ThrowingCallable action;
        final Instant          date;

        // GIVEN
        date = LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC)
            .plusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));

        // WHEN
        action = () -> service.lendBook(BookConstants.NUMBER, BookConstants.BORROWER, date);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("invalid", "lendingDate", date));
    }

    @Test
    @DisplayName("When lending a book for a not existing book, then an exception is thrown")
    void testLendBook_NoBook_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.lendBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When lending a book for a not existing book, then an exception is thrown")
            .isInstanceOf(MissingBookException.class);
    }

    @Test
    @DisplayName("When lending a book on the last return date, then it is persisted")
    void testLendBook_OnLastReturn() {

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(bookLendingRepository.findReturned(BookConstants.NUMBER))
            .willReturn(Optional.of(BookLendings.returned()));

        // WHEN
        service.lendBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.RETURNED_DATE);

        // THEN
        verify(bookLendingRepository).save(BookLendings.lentAtReturn());
    }

    @Test
    @DisplayName("When lending a book, then it is persisted with the current date")
    void testLendBook_PersistedData() {

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));

        // WHEN
        service.lendBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        verify(bookLendingRepository).save(BookLendings.lent());
    }

    @Test
    @DisplayName("When lending a book, then it is returned with the current date")
    void testLendBook_ReturnedData() {
        final BookLending lending;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(bookLendingRepository.save(BookLendings.lent())).willReturn(BookLendings.lent());

        // WHEN
        lending = service.lendBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEqualTo(BookLendings.lent());
    }

    @Test
    @DisplayName("When lending a book today, then it is persisted with the current date")
    void testLendBook_Today() {

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));

        // WHEN
        service.lendBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE_TODAY);

        // THEN
        verify(bookLendingRepository).save(BookLendings.lentToday());
    }

}
