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

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.library.book.adapter.inbound.jpa.model.GameBookEntity;

public interface GameBookSpringRepository extends JpaRepository<GameBookEntity, Long> {

    public void deleteByNumber(final long number);

    public boolean existsByIsbn(final String isbn);

    public boolean existsByIsbnAndNumberNot(final String isbn, final long number);

    public boolean existsByNumber(final long number);

    public Optional<GameBookEntity> findByNumber(final long number);

    @Query("""
               SELECT COALESCE(MAX(b.number), 0) + 1
               FROM Book b
            """)
    public Long findNextNumber();

    @Query("""
               SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM Book b
               RIGHT JOIN BookLending l ON b.id = l.bookId
               WHERE b.id = :bookId
                 AND l.returnDate IS NULL
            """)
    public boolean isLent(@Param("bookId") final long bookId);

}
