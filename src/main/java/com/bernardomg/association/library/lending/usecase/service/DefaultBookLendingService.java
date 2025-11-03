/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.library.lending.usecase.service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.lending.domain.exception.MissingBookLendingException;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotAlreadyLentRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotAlreadyReturnedRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotLentBeforeLastReturnRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotLentInFutureRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotReturnedBeforeLastReturnRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotReturnedBeforeLentRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotReturnedInFutureRule;
import com.bernardomg.association.person.domain.exception.MissingContactException;
import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.person.domain.model.ContactName;
import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

@Service
@Transactional
public final class DefaultBookLendingService implements BookLendingService {

    /**
     * Logger for the class.
     */
    private static final Logger          log = LoggerFactory.getLogger(DefaultBookLendingService.class);

    private final BookLendingRepository  bookLendingRepository;

    private final BookRepository         bookRepository;

    private final ContactRepository      contactRepository;

    private final Validator<BookLending> lendBookValidator;

    private final Validator<BookLending> returnBookValidator;

    public DefaultBookLendingService(final BookLendingRepository bookLendingRepo, final BookRepository bookRepo,
            final ContactRepository contactRepo) {
        super();

        bookLendingRepository = Objects.requireNonNull(bookLendingRepo);
        bookRepository = Objects.requireNonNull(bookRepo);
        contactRepository = Objects.requireNonNull(contactRepo);

        lendBookValidator = new FieldRuleValidator<>(new BookLendingNotAlreadyLentRule(bookLendingRepo),
            new BookLendingNotLentBeforeLastReturnRule(bookLendingRepo), new BookLendingNotLentInFutureRule());
        returnBookValidator = new FieldRuleValidator<>(new BookLendingNotAlreadyReturnedRule(bookLendingRepo),
            new BookLendingNotReturnedBeforeLastReturnRule(bookLendingRepo), new BookLendingNotReturnedBeforeLentRule(),
            new BookLendingNotReturnedInFutureRule());
    }

    @Override
    public final Page<BookLending> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<BookLending> lendings;

        log.debug("Reading book lendings with pagination {} and sorting {}", pagination, sorting);

        lendings = bookLendingRepository.findAll(pagination, sorting);

        log.debug("Read book lendings with pagination {} and sorting {}", pagination, sorting);

        return lendings;
    }

    @Override
    public final BookLending lendBook(final long bookNumber, final long borrowerNumber, final Instant date) {
        final BookLending    lending;
        final Borrower       borrower;
        final BookLending    created;
        final Optional<Book> readBook;
        final Book           book;
        final LentBook       lentBook;
        final Title          title;

        log.debug("Lending book {} to {}", bookNumber, borrowerNumber);

        readBook = bookRepository.findOne(bookNumber);
        if (readBook.isEmpty()) {
            log.debug("Missing book {}", bookNumber);
            throw new MissingBookException(bookNumber);
        }
        book = readBook.get();

        borrower = contactRepository.findOne(borrowerNumber)
            .map(this::toBorrower)
            .orElseThrow(() -> {
                log.debug("Missing contact {}", borrowerNumber);
                throw new MissingContactException(borrowerNumber);
            });

        title = book.title();
        lentBook = new LentBook(bookNumber, title);
        lending = new BookLending(lentBook, borrower, date);

        lendBookValidator.validate(lending);

        created = bookLendingRepository.save(lending);

        log.debug("Lent book {} to {}", bookNumber, borrowerNumber);

        return created;
    }

    @Override
    public final BookLending returnBook(final long bookNumber, final long borrower, final Instant date) {
        final BookLending read;
        final BookLending lending;
        final BookLending returned;

        log.debug("Returning book {} from {}", bookNumber, borrower);

        read = bookLendingRepository.findOne(bookNumber, borrower)
            .orElseThrow(() -> {
                log.error("Missing book {}", bookNumber + "-" + borrower);
                throw new MissingBookLendingException(bookNumber + "-" + borrower);
            });

        lending = read.returned(date);

        // TODO: not allow returning a book lent to another
        returnBookValidator.validate(lending);

        returned = bookLendingRepository.save(lending);

        log.debug("Returned book {} from {}", bookNumber, borrower);

        return returned;
    }

    private final Borrower toBorrower(final Contact contact) {
        final ContactName name;

        name = new ContactName(contact.name()
            .firstName(),
            contact.name()
                .lastName());
        return new Borrower(contact.number(), name);
    }

}
