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

package com.bernardomg.association.test.fee.service.integration;

import java.util.GregorianCalendar;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.FeeQuery;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.test.fee.util.model.FeesQuery;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Fee service - get all - sort")
@Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
class ITFeeServiceGetAllSort {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetAllSort() {
        super();
    }

    @Test
    @DisplayName("With ascending order by date it returns the ordered data")
    void testGetAll_Date_Asc() {
        final Iterator<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "date");

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable)
            .iterator();

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(1L)
            .name("Member 1 Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(2L)
            .name("Member 2 Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(3L)
            .name("Member 3 Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(4L)
            .name("Member 4 Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(5L)
            .name("Member 5 Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("With descending order by date it returns the ordered data")
    void testGetAll_Date_Desc() {
        final Iterator<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "date");

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable)
            .iterator();

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(5L)
            .name("Member 5 Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(4L)
            .name("Member 4 Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(3L)
            .name("Member 3 Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(2L)
            .name("Member 2 Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(1L)
            .name("Member 1 Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    void testGetAll_Name_Asc() {
        final Iterator<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "name");

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable)
            .iterator();

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(1L)
            .name("Member 1 Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(2L)
            .name("Member 2 Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(3L)
            .name("Member 3 Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(4L)
            .name("Member 4 Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(5L)
            .name("Member 5 Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    void testGetAll_Name_Desc() {
        final Iterator<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "name");

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable)
            .iterator();

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(5L)
            .name("Member 5 Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(4L)
            .name("Member 4 Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(3L)
            .name("Member 3 Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(2L)
            .name("Member 2 Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(1L)
            .name("Member 1 Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With an invalid field ordering throws an exception")
    @Disabled
    void testGetAll_NotExisting() {
        final FeeQuery         feeQuery;
        final Pageable         pageable;
        final ThrowingCallable executable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "abc");

        feeQuery = FeesQuery.empty();

        executable = () -> service.getAll(feeQuery, pageable)
            .iterator();

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(BadSqlGrammarException.class);
    }

    @Test
    @DisplayName("With ascending order by paid flag it returns the ordered data")
    void testGetAll_Paid_Asc() {
        final Iterator<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "paid");

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable)
            .iterator();

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(5L)
            .name("Member 5 Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(1L)
            .name("Member 1 Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(2L)
            .name("Member 2 Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(3L)
            .name("Member 3 Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(4L)
            .name("Member 4 Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With descending order by paid flag it returns the ordered data")
    void testGetAll_Paid_Desc() {
        final Iterator<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "paid");

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable)
            .iterator();

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(1L)
            .name("Member 1 Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(2L)
            .name("Member 2 Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(3L)
            .name("Member 3 Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(4L)
            .name("Member 4 Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(fees.next(), DtoMemberFee.builder()
            .memberId(5L)
            .name("Member 5 Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());
    }

}
