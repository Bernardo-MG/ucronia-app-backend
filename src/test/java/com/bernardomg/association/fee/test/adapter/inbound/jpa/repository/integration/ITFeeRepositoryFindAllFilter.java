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

package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.config.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.test.config.factory.FeesQuery;
import com.bernardomg.association.member.test.config.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.config.data.annotation.MultipleMembers;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all - filter")
class ITFeeRepositoryGetAllFilter {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With a filter applied to the end date, the returned data is filtered")
    @MultipleMembers
    @MultipleFees
    void testFindAll_EndDate() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.endDate(YearMonth.of(2020, Month.FEBRUARY));

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("With a filter applied to the end date which covers no fee, no data is returned")
    @MultipleMembers
    @MultipleFees
    void testFindAll_EndDate_NotInRange() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.endDate(YearMonth.of(2020, Month.JANUARY));

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .isEmpty();
    }

    @Test
    @DisplayName("With a filter applied to the date, the returned data is filtered")
    @MultipleMembers
    @MultipleFees
    void testFindAll_InDate() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.inDate(YearMonth.of(2020, Month.MARCH));

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(2, Month.MARCH));
    }

    @Test
    @DisplayName("With a filter applied to the date using the lowest date of the year, the returned data is filtered")
    @ActiveMember
    @FeeFullYear
    void testFindAll_InDate_FirstDay_Data() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.endDate(YearMonth.of(2020, Month.JANUARY));

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(Month.JANUARY.getValue()));
    }

    @Test
    @DisplayName("With a filter applied to the date using the highest date of the year, the returned data is filtered")
    @ActiveMember
    @FeeFullYear
    void testFindAll_InDate_LastDay_Data() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.inDate(YearMonth.of(2020, Month.DECEMBER));

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With a filter applied to the date using a not existing date, no data is returned")
    @MultipleMembers
    @MultipleFees
    void testFindAll_InDate_NotExisting() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.inDate(YearMonth.of(2020, Month.NOVEMBER));

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a filter applied to the start date, the returned data is filtered")
    @MultipleMembers
    @MultipleFees
    void testFindAll_StartDate() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.startDate(YearMonth.of(2020, Month.JUNE));

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidAt(5, Month.JUNE));
    }

    @Test
    @DisplayName("With a filter applied to the start date which covers no fee, no data is returned")
    @MultipleMembers
    @MultipleFees
    void testFindAll_StartDate_NotInRange() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.startDate(YearMonth.of(2020, Month.JULY));

        // WHEN
        fees = repository.findAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
