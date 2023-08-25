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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
@DisplayName("Fee service - get all")
class ITFeeServiceGetAll {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("With a full year it returns all the fees")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    void testGetAll_FullYear() {
        final Iterable<MemberFee> fees;
        final Iterator<MemberFee> feesItr;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(12);

        feesItr = fees.iterator();

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.MARCH, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.APRIL, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.MAY, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.JUNE, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.JULY, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.AUGUST, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.SEPTEMBER, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.OCTOBER, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.NOVEMBER, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.DECEMBER, 1, 0, 0))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With an inactive user it returns all the fees")
    @Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/single.sql" })
    void testGetAll_Inactive() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(1);

        FeeAssertions.isEqualTo(fees.iterator()
            .next(),
            DtoMemberFee.builder()
                .memberId(1L)
                .memberName("Member 1 Surname 1")
                .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
                .paid(true)
                .build());
    }

    @Test
    @DisplayName("With multiple fees it returns all the fees")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    void testGetAll_Multiple() {
        final Iterable<MemberFee> fees;
        final Iterator<MemberFee> feesItr;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isEqualTo(5);

        feesItr = fees.iterator();

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(2L)
            .memberName("Member 2 Surname 2")
            .date(LocalDateTime.of(2020, Month.MARCH, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(3L)
            .memberName("Member 3 Surname 3")
            .date(LocalDateTime.of(2020, Month.APRIL, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(4L)
            .memberName("Member 4 Surname 4")
            .date(LocalDateTime.of(2020, Month.MAY, 1, 0, 0))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), DtoMemberFee.builder()
            .memberId(5L)
            .memberName("Member 5 Surname 5")
            .date(LocalDateTime.of(2020, Month.JUNE, 1, 0, 0))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("With no data it returns nothing")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_NoFee() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(IterableUtils.size(fees))
            .isZero();
    }

}
