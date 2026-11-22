
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

import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.lending.domain.exception.MissingBookLendingException;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.library.lending.usecase.service.DefaultBookLendingService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultBookLendingService - returnBook")
class TestBookLendingServiceReturnBook {

    @Mock
    private BookLendingRepository     bookLendingRepository;

    @Mock
    private BookRepository            bookRepository;

    @InjectMocks
    private DefaultBookLendingService service;

    @Test
    @DisplayName("When returning a book which was already returned, then an exception is thrown")
    void testReturnBook_AlreadyReturned_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(bookLendingRepository.findOne(BookConstants.NUMBER, BookConstants.BORROWER))
            .willReturn(Optional.of(BookLendings.returned()));
        given(bookLendingRepository.findReturned(BookConstants.NUMBER))
            .willReturn(Optional.of(BookLendings.returned()));
        given(bookLendingRepository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE))
            .willReturn(Optional.of(BookLendings.returned()));

        // WHEN
        action = () -> service.returnBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.RETURNED_DATE);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "returnDate", BookConstants.RETURNED_DATE));
    }

    @Test
    @DisplayName("When returning a book for a not existing lending, then an exception is thrown")
    void testReturnBook_NoLending_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(bookLendingRepository.findOne(BookConstants.NUMBER, BookConstants.BORROWER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.returnBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.RETURNED_DATE);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When returning a book for a not existing lending, then an exception is thrown")
            .isInstanceOf(MissingBookLendingException.class);
    }

    @Test
    @DisplayName("When returning a book on the last return date, then it is persisted")
    void testReturnBook_OnLastReturn() {

        // GIVEN
        given(bookLendingRepository.findOne(BookConstants.NUMBER, BookConstants.BORROWER))
            .willReturn(Optional.of(BookLendings.lent()));

        // WHEN
        service.returnBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.RETURNED_DATE);

        // THEN
        verify(bookLendingRepository).save(BookLendings.returned());
    }

    @Test
    @DisplayName("When returning a book, then it is persisted")
    void testReturnBook_PersistedData() {

        // GIVEN
        given(bookLendingRepository.findOne(BookConstants.NUMBER, BookConstants.BORROWER))
            .willReturn(Optional.of(BookLendings.lent()));

        // WHEN
        service.returnBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.RETURNED_DATE);

        // THEN
        verify(bookLendingRepository).save(BookLendings.returned());
    }

    @Test
    @DisplayName("When returning a book before it was lent, then an exception is thrown")
    void testReturnBook_ReturnBeforeLent_Exception() {
        final ThrowingCallable action;
        final Instant          date;

        // GIVEN
        date = LocalDate.ofInstant(BookConstants.LENT_DATE, ZoneOffset.UTC)
            .minusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        given(bookLendingRepository.findOne(BookConstants.NUMBER, BookConstants.BORROWER))
            .willReturn(Optional.of(BookLendings.lent()));

        // WHEN
        action = () -> service.returnBook(BookConstants.NUMBER, BookConstants.BORROWER, date);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("invalid", "returnDate", date));
    }

    @Test
    @DisplayName("When returning a book before the last return, then an exception is thrown")
    void testReturnBook_ReturnedBeforeLastReturn_Exception() {
        final ThrowingCallable action;
        final Instant          date;

        // GIVEN
        date = LocalDate.ofInstant(BookConstants.RETURNED_DATE, ZoneOffset.UTC)
            .minusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        given(bookLendingRepository.findOne(BookConstants.NUMBER, BookConstants.BORROWER))
            .willReturn(Optional.of(BookLendings.returned()));
        given(bookLendingRepository.findReturned(BookConstants.NUMBER))
            .willReturn(Optional.of(BookLendings.returned()));
        given(bookLendingRepository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE))
            .willReturn(Optional.empty());

        // WHEN
        action = () -> service.returnBook(BookConstants.NUMBER, BookConstants.BORROWER, date);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("invalid", "returnDate", date));
    }

    @Test
    @DisplayName("When returning a book, then it is returned")
    void testReturnBook_ReturnedData() {
        final BookLending lending;

        // GIVEN
        given(bookLendingRepository.findOne(BookConstants.NUMBER, BookConstants.BORROWER))
            .willReturn(Optional.of(BookLendings.lent()));
        given(bookLendingRepository.save(BookLendings.returned())).willReturn(BookLendings.returned());

        // WHEN
        lending = service.returnBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.RETURNED_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEqualTo(BookLendings.returned());
    }

    @Test
    @DisplayName("When returning a book in the future, then an exception is thrown")
    void testReturnBook_ReturnInFuture_Exception() {
        final ThrowingCallable action;
        final Instant          date;

        // GIVEN
        date = LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC)
            .plusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        given(bookLendingRepository.findOne(BookConstants.NUMBER, BookConstants.BORROWER))
            .willReturn(Optional.of(BookLendings.lent()));

        // WHEN
        action = () -> service.returnBook(BookConstants.NUMBER, BookConstants.BORROWER, date);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("invalid", "returnDate", date));
    }

    @Test
    @DisplayName("When returning a book when it was lent, then it is persisted")
    void testReturnBook_ReturnWhenLent() {
        // GIVEN
        given(bookLendingRepository.findOne(BookConstants.NUMBER, BookConstants.BORROWER))
            .willReturn(Optional.of(BookLendings.lent()));

        // WHEN
        service.returnBook(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        verify(bookLendingRepository).save(BookLendings.returnedWhenLent());
    }

}
