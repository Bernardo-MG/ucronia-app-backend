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

package com.bernardomg.association.membership.test.fee.service.integration;

import java.time.Month;
import java.time.YearMonth;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.bernardomg.association.membership.fee.model.ImmutableMemberFee;
import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.model.request.FeeQuery;
import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.association.membership.test.fee.configuration.FeeFullYear;
import com.bernardomg.association.membership.test.fee.configuration.MultipleFees;
import com.bernardomg.association.membership.test.fee.configuration.PaidFee;
import com.bernardomg.association.membership.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.membership.test.fee.util.model.FeesQuery;
import com.bernardomg.association.membership.test.member.configuration.MultipleMembers;
import com.bernardomg.association.membership.test.member.configuration.NoNameOrSurnameMember;
import com.bernardomg.association.membership.test.member.configuration.NoSurnameMember;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
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
    @ValidMember
    @FeeFullYear
    void testGetAll_FullYear() {
        final Iterable<MemberFee> fees;
        final Iterator<MemberFee> feesItr;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 20, Direction.ASC, "date");

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(fees)
            .hasSize(12);

        feesItr = fees.iterator();

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.JANUARY))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.MARCH))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.APRIL))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.MAY))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.JUNE))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.JULY))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.AUGUST))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.SEPTEMBER))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.OCTOBER))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.NOVEMBER))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.DECEMBER))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("With multiple fees it returns all the fees")
    @MultipleMembers
    @MultipleFees
    void testGetAll_Multiple() {
        final Iterable<MemberFee> fees;
        final Iterator<MemberFee> feesItr;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(fees)
            .hasSize(5);

        feesItr = fees.iterator();

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .date(YearMonth.of(2020, Month.FEBRUARY))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(2L)
            .memberName("Member 2 Surname 2")
            .date(YearMonth.of(2020, Month.MARCH))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(3L)
            .memberName("Member 3 Surname 3")
            .date(YearMonth.of(2020, Month.APRIL))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(4L)
            .memberName("Member 4 Surname 4")
            .date(YearMonth.of(2020, Month.MAY))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(feesItr.next(), ImmutableMemberFee.builder()
            .memberId(5L)
            .memberName("Member 5 Surname 5")
            .date(YearMonth.of(2020, Month.JUNE))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("With no data it returns nothing")
    @ValidMember
    void testGetAll_NoFee() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(fees)
            .isEmpty();
    }

    @Test
    @DisplayName("With no name or surname it returns an empty name")
    @NoNameOrSurnameMember
    @PaidFee
    void testGetAll_NoNameOrSurname() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(fees)
            .hasSize(1);

        FeeAssertions.isEqualTo(fees.iterator()
            .next(),
            ImmutableMemberFee.builder()
                .memberId(1L)
                .memberName("")
                .date(YearMonth.of(2020, Month.FEBRUARY))
                .paid(true)
                .build());
    }

    @Test
    @DisplayName("With no surname it returns only the name")
    @NoSurnameMember
    @PaidFee
    void testGetAll_NoSurname() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(fees)
            .hasSize(1);

        FeeAssertions.isEqualTo(fees.iterator()
            .next(),
            ImmutableMemberFee.builder()
                .memberId(1L)
                .memberName("Member 1")
                .date(YearMonth.of(2020, Month.FEBRUARY))
                .paid(true)
                .build());
    }

    @Test
    @DisplayName("With a single fee it returns all the fees")
    @ValidMember
    @PaidFee
    void testGetAll_Single() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(fees)
            .hasSize(1);

        FeeAssertions.isEqualTo(fees.iterator()
            .next(),
            ImmutableMemberFee.builder()
                .memberId(1L)
                .memberName("Member 1 Surname 1")
                .date(YearMonth.of(2020, Month.FEBRUARY))
                .paid(true)
                .build());
    }

}
