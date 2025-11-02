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

package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactEntity;

public interface ContactSpringRepository
        extends JpaRepository<ContactEntity, Long>, JpaSpecificationExecutor<ContactEntity> {

    @Modifying
    @Query("""
            DELETE
            FROM Contact c
            WHERE c.number = :number
            """)
    public void deleteByNumber(@Param("number") final Long number);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END AS exists
            FROM Contact c
            WHERE c.identifier = :identifier
            """)
    public boolean existsByIdentifier(@Param("identifier") final String identifier);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END AS exists
            FROM Contact c
            WHERE c.number != :number
              AND c.identifier = :identifier
            """)
    public boolean existsByIdentifierForAnother(@Param("number") final Long number,
            @Param("identifier") final String identifier);

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END AS exists
            FROM Contact c
            WHERE c.number = :number
            """)
    public boolean existsByNumber(@Param("number") final Long number);

    @Query("""
            SELECT p.id AS id
            FROM Contact c
            WHERE c.member = true
              AND c.active = true
            ORDER BY id ASC
            """)
    public Collection<Long> findAllActiveMemberIds();

    @Query("""
            SELECT p
            FROM Contact c
            WHERE c.member = true
              AND c.active = true
            """)
    public Page<ContactEntity> findAllActiveMembers(final Pageable pageable);

    public Collection<ContactEntity> findAllByMemberTrueAndRenewMembershipTrue();

    public Collection<ContactEntity> findAllByNumberIn(final Collection<Long> numbers);

    @Query("""
            SELECT p.id AS id
            FROM Contact c
            WHERE c.member = true
              AND c.active = false
            ORDER BY id ASC
            """)
    public Collection<Long> findAllInactiveMemberIds();

    @Query("""
            SELECT p
            FROM Contact c
            WHERE c.member = true
              AND c.active != p.renewMembership
            """)
    public Collection<ContactEntity> findAllWithRenewalMismatch();

    public Optional<ContactEntity> findByNumber(final Long number);

    @Query("""
            SELECT p
            FROM Contact c
            WHERE c.member = true
              AND c.number = :number
            """)
    public Optional<ContactEntity> findByNumberWithMembership(@Param("number") final Long number);

    @Query("SELECT COALESCE(MAX(c.number), 0) + 1 FROM Contact c")
    public Long findNextNumber();

    @Query("""
            SELECT c.active
            FROM Contact c
            WHERE c.member = true
              AND c.number = :number
            """)
    public Boolean isActive(@Param("number") final Long number);

}
