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

package com.bernardomg.association.library.author.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;

public interface AuthorSpringRepository extends JpaRepository<AuthorEntity, Long> {

    public void deleteByNumber(final Long number);

    public boolean existsByName(final String name);

    @Query("""
               SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM Author a
               WHERE a.number != :number
                 AND a.name = :name
            """)
    public boolean existsByNotNumberAndName(@Param("number") final Long number, @Param("name") final String name);

    public boolean existsByNumber(final Long number);

    public Collection<AuthorEntity> findAllByNumberIn(final Collection<Long> numbers);

    public Optional<AuthorEntity> findByNumber(final Long number);

    @Query("SELECT COALESCE(MAX(a.number), 0) + 1 FROM Author a")
    public Long findNextNumber();

}
