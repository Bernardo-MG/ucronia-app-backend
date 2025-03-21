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

package com.bernardomg.association.library.lending.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
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
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookLendingService - lend book")
class TestBookLendingServiceLendBook {

    @Mock
    private BookLendingRepository     bookLendingRepository;

    @Mock
    private BookRepository            bookRepository;

    @Mock
    private PersonRepository          personRepository;

    @InjectMocks
    private DefaultBookLendingService service;

    @Test
    @DisplayName("When lending a book which is already lent, an exception is thrown")
    void testLendBook_AlreadyLent_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));
        given(bookLendingRepository.findLent(BookConstants.NUMBER)).willReturn(Optional.of(BookLendings.lent()));

        // WHEN
        execution = () -> service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "lendingDate", BookConstants.LENT_DATE));
    }

    @Test
    @DisplayName("When lending a book which is already lent for another person, an exception is thrown")
    void testLendBook_AlreadyLentForAnother_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));
        given(bookLendingRepository.findLent(BookConstants.NUMBER))
            .willReturn(Optional.of(BookLendings.lentAlternativePerson()));

        // WHEN
        execution = () -> service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "lendingDate", BookConstants.LENT_DATE));
    }

    @Test
    @DisplayName("When lending a book before the last return date, an exception is thrown")
    void testLendBook_BeforeLastReturn_Exception() {
        final ThrowingCallable execution;
        final LocalDate        date;

        // GIVEN
        date = BookConstants.RETURNED_DATE.minusDays(1);

        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));
        given(bookLendingRepository.findReturned(BookConstants.NUMBER))
            .willReturn(Optional.of(BookLendings.returned()));

        // WHEN
        execution = () -> service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, date);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, new FieldFailure("invalid", "lendingDate", date));
    }

    @Test
    @DisplayName("When lending a book in the future, an exception is thrown")
    void testLendBook_InFuture_Exception() {
        final ThrowingCallable execution;
        final LocalDate        date;

        // GIVEN
        date = LocalDate.now()
            .plusDays(1);

        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));

        // WHEN
        execution = () -> service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, date);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, new FieldFailure("invalid", "lendingDate", date));
    }

    @Test
    @DisplayName("When lending a book for a not existing book, an exception is thrown")
    void testLendBook_NoBook_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookException.class);
    }

    @Test
    @DisplayName("When lending a book for a not existing member, an exception is thrown")
    void testLendBook_NoMember_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingPersonException.class);
    }

    @Test
    @DisplayName("When lending a book on the last return date, it is persisted")
    void testLendBook_OnLastReturn() {

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));
        given(bookLendingRepository.findReturned(BookConstants.NUMBER))
            .willReturn(Optional.of(BookLendings.returned()));

        // WHEN
        service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.RETURNED_DATE);

        // THEN
        verify(bookLendingRepository).save(BookLendings.lentAtReturn());
    }

    @Test
    @DisplayName("When lending a book, it is persisted with the current date")
    void testLendBook_PersistedData() {

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));

        // WHEN
        service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        verify(bookLendingRepository).save(BookLendings.lent());
    }

    @Test
    @DisplayName("When lending a book, it is returned with the current date")
    void testLendBook_ReturnedData() {
        final BookLending lending;

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));
        given(bookLendingRepository.save(BookLendings.lent())).willReturn(BookLendings.lent());

        // WHEN
        lending = service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEqualTo(BookLendings.lent());
    }

    @Test
    @DisplayName("When lending a book today, it is persisted with the current date")
    void testLendBook_Today() {

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(Books.full()));
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.noMembership()));

        // WHEN
        service.lendBook(BookConstants.NUMBER, PersonConstants.NUMBER, LocalDate.now());

        // THEN
        verify(bookLendingRepository).save(BookLendings.lentToday());
    }

}
