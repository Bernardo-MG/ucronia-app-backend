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

package com.bernardomg.association.library.lending.adapter.inbound.jpa.repository;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.BookSpringRepository;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntityMapper;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BorrowerEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BorrowerEntityMapper;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;
import com.bernardomg.association.library.lending.domain.model.Borrower;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaBookLendingRepository implements BookLendingRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaBookLendingRepository.class);

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final BookSpringRepository        bookSpringRepository;

    private final BorrowerSpringRepository    borrowerSpringRepository;

    public JpaBookLendingRepository(final BookLendingSpringRepository bookLendingSpringRepo,
            final BookSpringRepository bookSpringRepo, final BorrowerSpringRepository borrowerSpringRepo) {
        super();

        bookLendingSpringRepository = Objects.requireNonNull(bookLendingSpringRepo);
        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        borrowerSpringRepository = Objects.requireNonNull(borrowerSpringRepo);
    }

    @Override
    public final Page<BookLending> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<BookLending> read;
        final Pageable                                          pageable;

        log.debug("Finding all the book lendings");

        // TODO: test pagination and sorting
        pageable = SpringPagination.toPageable(pagination, sorting);
        read = bookLendingSpringRepository.findAllReturned(pageable)
            .map(this::toDomain);

        log.debug("Found all the book lendings: {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<BookLending> findLent(final long bookNumber) {
        final Optional<BookLending> lending;
        final Pageable              pageable;

        log.debug("Finding lent book lending for book {}", bookNumber);

        pageable = Pageable.ofSize(1);
        lending = bookLendingSpringRepository.findAllForBookLent(bookNumber, pageable)
            .stream()
            .findFirst()
            .map(this::toDomain);

        log.debug("Found lent book lending for book {}: {}", bookNumber, lending);

        return lending;
    }

    @Override
    public final Optional<BookLending> findOne(final long book, final long borrower) {
        final Optional<BookLending> lending;
        final Pageable              pageable;

        log.debug("Finding book lending for book {} and borrower {}", book, borrower);

        pageable = Pageable.ofSize(1);
        lending = bookLendingSpringRepository.find(book, borrower, pageable)
            .stream()
            .findFirst()
            .map(this::toDomain);

        log.debug("Found lending for book {} and borrower {}: {}", book, borrower, lending);

        return lending;
    }

    @Override
    public final Optional<BookLending> findReturned(final long book) {
        final Optional<BookLending> lending;
        final Pageable              pageable;

        log.debug("Finding returned book lending for book {}", book);

        pageable = Pageable.ofSize(1);
        lending = bookLendingSpringRepository.findAllForBookReturned(book, pageable)
            .stream()
            .findFirst()
            .map(this::toDomain);

        log.debug("Found returned book lending for book {}: {}", book, lending);

        return lending;
    }

    @Override
    public final Optional<BookLending> findReturned(final long book, final long borrower, final Instant lendingDate) {
        final Optional<BookLending> lending;
        final Pageable              pageable;

        log.debug("Finding returned book {} for borrower {} and date {}", book, borrower, lendingDate);

        pageable = Pageable.ofSize(1);
        lending = bookLendingSpringRepository.findAllReturned(book, borrower, lendingDate, pageable)
            .stream()
            .findFirst()
            .map(this::toDomain);

        log.debug("Found returned book lending for book {}: {}", book, lending);

        return lending;
    }

    @Override
    public final BookLending save(final BookLending lending) {
        final BookLendingEntity        toCreate;
        final BookLendingEntity        created;
        final BookLending              saved;
        final Optional<BookEntity>     bookEntity;
        final Optional<BorrowerEntity> borrower;

        log.debug("Saving book lending {}", lending);

        bookEntity = bookSpringRepository.findByNumber(lending.book()
            .number());
        borrower = borrowerSpringRepository.findByNumber(lending.borrower()
            .number());

        if ((bookEntity.isPresent()) && (borrower.isPresent())) {
            toCreate = BookLendingEntityMapper.toEntity(lending, bookEntity.get(), borrower.get());

            created = bookLendingSpringRepository.save(toCreate);
            saved = BookLendingEntityMapper.toDomain(created, bookEntity.get(), borrower.get());

            log.debug("Saved book lending {}", lending);
        } else {
            log.debug("Couldn't save book lending {}", lending);
            saved = null;
        }

        return saved;
    }

    private final BookLending toDomain(final BookLendingEntity entity) {
        final Optional<Borrower>   borrower;
        final Optional<BookEntity> bookEntity;
        final LentBook             lentBook;
        final Title                title;

        bookEntity = bookSpringRepository.findById(entity.getBookId());
        borrower = borrowerSpringRepository.findById(entity.getProfileId())
            .map(BorrowerEntityMapper::toDomain);
        title = new Title(bookEntity.get()
            .getSupertitle(),
            bookEntity.get()
                .getTitle(),
            bookEntity.get()
                .getSubtitle());
        lentBook = new LentBook(bookEntity.get()
            .getNumber(), title);
        return new BookLending(lentBook, borrower.get(), entity.getLendingDate(), entity.getReturnDate());
    }

}
