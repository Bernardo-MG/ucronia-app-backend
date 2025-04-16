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

package com.bernardomg.association.library.lending.adapter.inbound.jpa.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingId;

public interface BookLendingSpringRepository extends JpaRepository<BookLendingEntity, BookLendingId> {

    @Query("""
               SELECT l
               FROM BookLending l
                 INNER JOIN Book b ON b.id = l.bookId
                 INNER JOIN Person p ON p.id = l.personId
               WHERE b.number = :bookNumber
                 AND p.number = :personNumber
               ORDER BY l.returnDate DESC
            """)
    public List<BookLendingEntity> find(@Param("bookNumber") final long bookNumber,
            @Param("personNumber") final long personNumber, final Pageable pageable);

    public Collection<BookLendingEntity> findAllByBookId(Long id);

    @Query("""
               SELECT l
               FROM BookLending l
                 INNER JOIN Book b ON b.id = l.bookId
               WHERE b.number = :bookNumber
                 AND l.returnDate IS NULL
               ORDER BY l.returnDate DESC
            """)
    public List<BookLendingEntity> findAllForBookLent(@Param("bookNumber") final long bookNumber,
            final Pageable pageable);

    @Query("""
               SELECT l
               FROM BookLending l
                 INNER JOIN Book b ON b.id = l.bookId
               WHERE b.number = :bookNumber
                 AND l.returnDate IS NOT NULL
               ORDER BY l.returnDate DESC
            """)
    public List<BookLendingEntity> findAllForBookReturned(@Param("bookNumber") final long bookNumber,
            final Pageable pageable);

    @Query("""
               SELECT l
               FROM BookLending l
                 INNER JOIN Book b ON b.id = l.bookId
                 INNER JOIN Person p ON p.id = l.personId
               WHERE b.number = :bookNumber
                 AND p.number = :personNumber
                 AND l.lendingDate = :lendingDate
                 AND l.returnDate IS NOT NULL
               ORDER BY l.returnDate DESC
            """)
    public List<BookLendingEntity> findAllReturned(@Param("bookNumber") final long bookNumber,
            @Param("personNumber") final long personNumber, @Param("lendingDate") final LocalDate lendingDate,
            final Pageable pageable);

    @Query("""
               SELECT l
               FROM BookLending l
               WHERE l.returnDate IS null
            """)
    public Page<BookLendingEntity> findAllReturned(final Pageable pageable);

}
