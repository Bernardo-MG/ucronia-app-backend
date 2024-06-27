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

package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.BookLendingId;

public interface BookLendingSpringRepository extends JpaRepository<BookLendingEntity, BookLendingId> {

    public Optional<BookLendingEntity> findByBookIdAndPersonId(final long book, final long person);

    @Query("""
               SELECT l
               FROM BookLending l
               WHERE l.bookId = :bookId
                 AND l.personId = :personId
                 AND l.lendingDate = :date
                 AND l.returnDate IS NOT NULL
            """)
    public Optional<BookLendingEntity> findReturnedByNumberAndPersonAndDate(@Param("bookId") final long book,
            @Param("personId") final long person, @Param("date") final LocalDate date);

    @Query("""
               SELECT l
               FROM BookLending l
               WHERE l.bookId = :bookId
                 AND l.returnDate IS NULL
            """)
    public Optional<BookLendingEntity> findLent(@Param("bookId") final Long bookId);

    @Query("""
               SELECT l
               FROM BookLending l
               WHERE l.bookId = :bookId
                 AND l.returnDate IS NOT NULL
            """)
    public Optional<BookLendingEntity> findReturned(@Param("bookId") final Long bookId);

}
