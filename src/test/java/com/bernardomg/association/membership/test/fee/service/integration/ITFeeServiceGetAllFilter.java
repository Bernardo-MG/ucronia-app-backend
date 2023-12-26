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

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.model.request.FeeQuery;
import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.association.membership.test.fee.config.FeeFullYear;
import com.bernardomg.association.membership.test.fee.config.MultipleFees;
import com.bernardomg.association.membership.test.fee.util.assertion.FeeAssertions;
import com.bernardomg.association.membership.test.fee.util.model.FeesQuery;
import com.bernardomg.association.membership.test.fee.util.model.MemberFees;
import com.bernardomg.association.membership.test.member.configuration.MultipleMembers;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee service - get all - filter")
class ITFeeServiceGetAllFilter {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("With a filter applied to the end date, the returned data is filtered")
    @MultipleMembers
    @MultipleFees
    void testGetAll_EndDate() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.endDate(YearMonth.of(2020, Month.FEBRUARY));

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(MemberFees.paid());
    }

    @Test
    @DisplayName("With a filter applied to the end date which covers no fee, no data is returned")
    @MultipleMembers
    @MultipleFees
    void testGetAll_EndDate_NotInRange() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.endDate(YearMonth.of(2020, Month.JANUARY));

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .isEmpty();
    }

    @Test
    @DisplayName("With a filter applied to the date, the returned data is filtered")
    @MultipleMembers
    @MultipleFees
    void testGetAll_InDate() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.inDate(YearMonth.of(2020, Month.MARCH));

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(MemberFees.paidAt(2, Month.MARCH));
    }

    @Test
    @DisplayName("With a filter applied to the date using the lowest date of the year, the returned data is filtered")
    @ValidMember
    @FeeFullYear
    void testGetAll_InDate_FirstDay_Data() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.endDate(YearMonth.of(2020, Month.JANUARY));

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(MemberFees.paidAt(Month.JANUARY));
    }

    @Test
    @DisplayName("With a filter applied to the date using the highest date of the year, the returned data is filtered")
    @ValidMember
    @FeeFullYear
    void testGetAll_InDate_LastDay_Data() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.inDate(YearMonth.of(2020, Month.DECEMBER));

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(MemberFees.paidAt(Month.DECEMBER));
    }

    @Test
    @DisplayName("With a filter applied to the date using a not existing date, no data is returned")
    @MultipleMembers
    @MultipleFees
    void testGetAll_InDate_NotExisting() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.inDate(YearMonth.of(2020, Month.NOVEMBER));

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a filter applied to the start date, the returned data is filtered")
    @MultipleMembers
    @MultipleFees
    void testGetAll_StartDate() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.startDate(YearMonth.of(2020, Month.JUNE));

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        Assertions.assertThat(fees)
            .as("fees")
            .hasSize(1);

        // THEN
        FeeAssertions.isEqualTo(IterableUtils.first(fees), MemberFees.notPaidAt(5, Month.JUNE));
    }

    @Test
    @DisplayName("With a filter applied to the start date which covers no fee, no data is returned")
    @MultipleMembers
    @MultipleFees
    void testGetAll_StartDate_NotInRange() {
        final Iterable<MemberFee> fees;
        final FeeQuery            feeQuery;
        final Pageable            pageable;

        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.startDate(YearMonth.of(2020, Month.JULY));

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
