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

package com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;

public interface PublisherSpringRepository extends JpaRepository<PublisherEntity, Long> {

    public void deleteByNumber(final long number);

    public boolean existsByName(final String name);

    @Query("""
               SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM Publisher p
               WHERE p.number != :number AND p.name = :name
            """)
    public boolean existsByNotNumberAndName(@Param("number") final Long number, @Param("name") final String name);

    public boolean existsByNumber(final long number);

    public Collection<PublisherEntity> findAllByNumberIn(final Collection<Long> numbers);

    public Optional<PublisherEntity> findByNumber(final long number);

    @Query("SELECT COALESCE(MAX(p.number), 0) + 1 FROM Publisher p")
    public Long findNextNumber();

}
