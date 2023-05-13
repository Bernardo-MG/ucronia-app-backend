/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.fee.repository;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.PersistentFee;

public interface FeeRepository extends JpaRepository<PersistentFee, Long> {

    @Query("SELECT new com.bernardomg.association.fee.model.DtoMemberFee(f.id AS id, m.id AS memberId, m.name AS name, m.surname AS surname, f.date AS date, f.paid AS paid) FROM Fee f JOIN Member m ON f.memberId = m.id")
    public Page<MemberFee> findAllWithMember(final Pageable pageable);

    @Query("SELECT new com.bernardomg.association.fee.model.DtoMemberFee(f.id AS id, m.id AS memberId, m.name AS name, m.surname AS surname, f.date AS date, f.paid AS paid) FROM Fee f JOIN Member m ON f.memberId = m.id WHERE f.date >= :date")
    public Page<MemberFee> findAllWithMemberAfter(@Param("date") final Calendar date, final Pageable pageable);

    @Query("SELECT new com.bernardomg.association.fee.model.DtoMemberFee(f.id AS id, m.id AS memberId, m.name AS name, m.surname AS surname, f.date AS date, f.paid AS paid) FROM Fee f JOIN Member m ON f.memberId = m.id WHERE f.date <= :date")
    public Page<MemberFee> findAllWithMemberBefore(@Param("date") final Calendar date, final Pageable pageable);

    @Query("SELECT new com.bernardomg.association.fee.model.DtoMemberFee(f.id AS id, m.id AS memberId, m.name AS name, m.surname AS surname, f.date AS date, f.paid AS paid) FROM Fee f JOIN Member m ON f.memberId = m.id WHERE f.date = :date")
    public Page<MemberFee> findAllWithMemberByDate(@Param("date") final Calendar date, final Pageable pageable);

    @Query("SELECT new com.bernardomg.association.fee.model.DtoMemberFee(f.id AS id, m.id AS memberId, m.name AS name, m.surname AS surname, f.date AS date, f.paid AS paid) FROM Fee f JOIN Member m ON f.memberId = m.id WHERE f.date >= :start AND f.date <= :end")
    public Page<MemberFee> findAllWithMemberInRange(@Param("start") final Calendar start,
            @Param("end") final Calendar end, final Pageable pageable);

    @Query("SELECT new com.bernardomg.association.fee.model.DtoMemberFee(f.id, m.id, m.name, m.surname, f.date, f.paid) FROM Fee f JOIN Member m ON f.memberId = m.id WHERE f.id = :id")
    public Optional<MemberFee> findOneByIdWithMember(@Param("id") final Long id);

}
