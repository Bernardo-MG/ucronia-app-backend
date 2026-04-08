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

import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.lending.domain.exception.MissingBookLendingException;
import com.bernardomg.association.library.lending.domain.exception.MissingBorrowerException;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;
import com.bernardomg.association.library.lending.domain.model.Borrower;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.domain.repository.BorrowerRepository;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotAlreadyLentRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotAlreadyReturnedRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotLentBeforeLastReturnRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotLentInFutureRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotReturnedBeforeLastReturnRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotReturnedBeforeLentRule;
import com.bernardomg.association.library.lending.usecase.validation.BookLendingNotReturnedInFutureRule;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import jakarta.transaction.Transactional;

@Transactional
public final class DefaultBookLendingService implements BookLendingService {

    /**
     * Logger for the class.
     */
    private static final Logger          log = LoggerFactory.getLogger(DefaultBookLendingService.class);

    private final BookLendingRepository  bookLendingRepository;

    private final BookRepository         bookRepository;

    private final BorrowerRepository     borrowerRepository;

    private final Validator<BookLending> lendBookValidator;

    private final Validator<BookLending> returnBookValidator;

    public DefaultBookLendingService(final BookLendingRepository bookLendingRepo, final BookRepository bookRepo,
            final BorrowerRepository borrowerRepo) {
        super();

        bookLendingRepository = Objects.requireNonNull(bookLendingRepo);
        bookRepository = Objects.requireNonNull(bookRepo);
        borrowerRepository = Objects.requireNonNull(borrowerRepo);

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

        borrower = borrowerRepository.findOne(borrowerNumber)
            .orElseThrow(() -> {
                log.debug("Missing profile {}", borrowerNumber);
                throw new MissingBorrowerException(borrowerNumber);
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
                throw new MissingBookLendingException(bookNumber, borrower);
            });

        lending = read.returned(date);

        returnBookValidator.validate(lending);

        returned = bookLendingRepository.save(lending);

        log.debug("Returned book {} from {}", bookNumber, borrower);

        return returned;
    }

}
