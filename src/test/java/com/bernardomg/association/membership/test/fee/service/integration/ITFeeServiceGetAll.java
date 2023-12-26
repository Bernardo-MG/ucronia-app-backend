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
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.model.request.FeeQuery;
import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.association.membership.test.fee.config.FeeFullYear;
import com.bernardomg.association.membership.test.fee.config.MultipleFees;
import com.bernardomg.association.membership.test.fee.config.PaidFee;
import com.bernardomg.association.membership.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.membership.test.fee.util.model.FeesQuery;
import com.bernardomg.association.membership.test.fee.util.model.MemberFees;
import com.bernardomg.association.membership.test.member.configuration.MultipleMembers;
import com.bernardomg.association.membership.test.member.configuration.NoSurnameMember;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
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

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .hasSize(12);

        feesItr = fees.iterator();

        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.JANUARY));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.FEBRUARY));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.MARCH));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.APRIL));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.MAY));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.JUNE));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.JULY));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.AUGUST));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.SEPTEMBER));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.OCTOBER));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.NOVEMBER));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(Month.DECEMBER));
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

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .hasSize(5);

        feesItr = fees.iterator();

        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(1, Month.FEBRUARY));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(2, Month.MARCH));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(3, Month.APRIL));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.paidAt(4, Month.MAY));
        FeeAssertions.isEqualTo(feesItr.next(), MemberFees.notPaidAt(5, Month.JUNE));
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

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
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

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(MemberFees.noSurname());
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

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(MemberFees.paid());
    }

}
