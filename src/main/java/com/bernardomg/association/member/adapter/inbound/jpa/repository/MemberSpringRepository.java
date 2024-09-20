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

package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MinimalMember;

public interface MemberSpringRepository extends JpaRepository<MemberEntity, Long> {

    @Modifying
    @Query("""
            DELETE
            FROM Member m
            WHERE m.person.number = :number
            """)
    public void deleteByNumber(@Param("number") final Long number);

    @Query("""
            SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END AS exists
            FROM Member m
              JOIN m.person p
            WHERE p.number = :number
            """)
    public boolean existsByNumber(@Param("number") final Long number);

    @Query("""
            SELECT m
            FROM Member m
            WHERE m.active = true
            """)
    public Page<MemberEntity> findAllActive(final Pageable pageable);

    @Query("""
            SELECT m.person.id AS personId
            FROM Member m
            WHERE m.active = true
            ORDER BY id ASC
            """)
    public Collection<Long> findAllActivePersonIds();

    @Query("""
            SELECT m.active AS active,
              m.person.firstName AS firstName,
              m.person.lastName AS lastName,
              m.person.number AS number
            FROM Member m
            WHERE m.active = true
            """)
    public Page<MinimalMember> findAllActivePublic(final Pageable pageable);

    @Query("""
            SELECT m
            FROM Member m
              JOIN m.person p
            WHERE p.number IN :numbers
            """)
    public Collection<MemberEntity> findAllByNumber(@Param("numbers") final Iterable<Long> numbers);

    @Query("""
            SELECT m
            FROM Member m
            WHERE m.active = false
            """)
    public Page<MemberEntity> findAllInactive(final Pageable pageable);

    @Query("""
            SELECT m.person.id AS personId
            FROM Member m
            WHERE m.active = false
            ORDER BY id ASC
            """)
    public Collection<Long> findAllInactivePersonIds();

    @Query("""
            SELECT m.active AS active,
              m.person.firstName AS firstName,
              m.person.lastName AS lastName,
              m.person.number AS number
            FROM Member m
            WHERE m.active = false
            """)
    public Page<MinimalMember> findAllInactivePublic(final Pageable pageable);

    @Query("""
            SELECT m.active AS active,
              m.person.firstName AS firstName,
              m.person.lastName AS lastName,
              m.person.number AS number
            FROM Member m
            """)
    public Page<MinimalMember> findAllPublic(final Pageable pageable);

    @Query("""
            SELECT m
            FROM Member m
              JOIN m.person p
            WHERE p.number = :number
            """)
    public Optional<MemberEntity> findByNumber(@Param("number") final Long number);

    @Query("""
            SELECT m.active AS active,
              m.person.firstName AS firstName,
              m.person.lastName AS lastName,
              m.person.number AS number
            FROM Member m
              JOIN m.person p
            WHERE p.number = :number
            """)
    public Optional<MinimalMember> findByNumberPublic(@Param("number") final Long number);

    @Query("""
            SELECT m.active
            FROM Member m
              JOIN m.person p
            WHERE p.number = :number
            """)
    public Boolean isActive(@Param("number") final Long number);

}
