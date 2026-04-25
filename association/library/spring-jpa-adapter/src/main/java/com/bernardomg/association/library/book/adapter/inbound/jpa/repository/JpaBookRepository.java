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

package com.bernardomg.association.library.book.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntityMapper;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.Borrower;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;

@Transactional
public final class JpaBookRepository implements BookRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaBookRepository.class);

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final BookSpringRepository        bookSpringRepository;

    private final BorrowerSpringRepository    borrowerSpringRepository;

    public JpaBookRepository(final BookSpringRepository bookSpringRepo,
            final BorrowerSpringRepository borrowerSpringRepo,
            final BookLendingSpringRepository bookLendingSpringRepo) {
        super();

        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        borrowerSpringRepository = Objects.requireNonNull(borrowerSpringRepo);
        bookLendingSpringRepository = Objects.requireNonNull(bookLendingSpringRepo);
    }

    @Override
    public final Optional<Book> findOne(final long number) {
        final Optional<Book> book;

        log.debug("Finding book {}", number);

        book = bookSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found book {}: {}", number, book);

        return book;
    }

    private final Book toDomain(final BookEntity entity) {
        final boolean                     lent;
        final Collection<BookLendingInfo> lendings;

        // Lendings
        lendings = bookLendingSpringRepository.findAllByBookIdOrderByReturnDate(entity.getId())
            .stream()
            .map(l -> toDomain(entity, l))
            .toList();
        lent = bookSpringRepository.isLent(entity.getId());

        return BookEntityMapper.toDomain(entity, lent, lendings);
    }

    private final BookLendingInfo toDomain(final BookEntity bookEntity, final BookLendingEntity entity) {
        final Borrower borrower;
        borrower = borrowerSpringRepository.findByProfileId(entity.getProfileId())
            .map(BookEntityMapper::toDomain)
            .orElseThrow(() -> {
                log.error("Missing profile {}", entity.getProfileId());
                throw new MissingProfileException(entity.getProfileId());
            });
        new Title(bookEntity.getSupertitle(), bookEntity.getTitle(), bookEntity.getSubtitle());
        return new BookLendingInfo(borrower, entity.getLendingDate(), entity.getReturnDate());
    }

}
