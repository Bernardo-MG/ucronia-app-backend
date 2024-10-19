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

package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;

public interface PersonSpringRepository extends JpaRepository<PersonEntity, Long> {

    @Modifying
    @Query("""
            DELETE
            FROM Person p
            WHERE p.number = :number
            """)
    public void deleteByNumber(@Param("number") final Long number);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END AS exists
            FROM Person p
            WHERE p.number = :number
            """)
    public boolean existsByNumber(@Param("number") final Long number);

    @Query("""
            SELECT p
            FROM Person p
            WHERE p.activeMember = true
            """)
    public Page<PersonEntity> findAllActive(final Pageable pageable);

    @Query("""
            SELECT p.id AS id
            FROM Person p
            WHERE p.activeMember = true
            ORDER BY id ASC
            """)
    public Collection<Long> findAllActiveIds();

    public Collection<PersonEntity> findAllByNumberIn(final Collection<Long> numbers);

    @Query("""
            SELECT p
            FROM Person p
            WHERE p.activeMember = false
            """)
    public Page<PersonEntity> findAllInactive(final Pageable pageable);

    @Query("""
            SELECT p.id AS id
            FROM Person p
            WHERE p.activeMember = false
            ORDER BY id ASC
            """)
    public Collection<Long> findAllInactiveIds();

    @Query("""
            SELECT p
            FROM Person p
            WHERE p.activeMember IS NOT NULL
            """)
    public Page<PersonEntity> findAllWithMembership(final Pageable pageable);

    public Optional<PersonEntity> findByNumber(final Long number);

    @Query("""
            SELECT p
            FROM Person p
            WHERE p.activeMember IS NOT NULL
              AND p.number = :number
            """)
    public Optional<PersonEntity> findByNumberWithMembership(@Param("number") final Long number);

    @Query("SELECT COALESCE(MAX(p.number), 0) + 1 FROM Person p")
    public Long findNextNumber();

    @Query("""
            SELECT p.activeMember
            FROM Person p
            WHERE p.activeMember IS NOT NULL
              AND p.number = :number
            """)
    public Boolean isActive(@Param("number") final Long number);

}
