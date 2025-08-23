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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidAndNotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactivePerson;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all in year for active")
class ITFeeRepositoryFindAllInYearForActiveMembers {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With a not paid fee, it returns the calendar")
    @MembershipActivePerson
    @NotPaidFee
    void testFindAllInYearForActiveMembers_Active_NotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidForMonth(1, Month.FEBRUARY));
    }

    @Test
    @DisplayName("With a not paid fee and searching for the next year, it returns nothing")
    @MembershipActivePerson
    @NotPaidFee
    void testFindAllInYearForActiveMembers_Active_NotPaid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR.plusYears(1), sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee and searching for the previous year, it returns nothing")
    @MembershipActivePerson
    @NotPaidFee
    void testFindAllInYearForActiveMembers_Active_NotPaid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR.minusYears(1), sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee, it returns the calendar")
    @MembershipActivePerson
    @PaidFee
    void testFindAllInYearForActiveMembers_Active_Paid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY));
    }

    @Test
    @DisplayName("With a paid fee and searching for the next year, it returns nothing")
    @MembershipActivePerson
    @PaidFee
    void testFindAllInYearForActiveMembers_Active_Paid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR.plusYears(1), sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee and searching for the previous year, it returns nothing")
    @MembershipActivePerson
    @PaidFee
    void testFindAllInYearForActiveMembers_Active_Paid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR.minusYears(1), sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With both a paid and not paid fees, it returns the calendar")
    @MembershipActivePerson
    @PaidAndNotPaidFee
    void testFindAllInYearForActiveMembers_Active_PaidAndNotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY), Fees.notPaidForMonth(2, 10, Month.MARCH));
    }

    @Test
    @DisplayName("With a not paid fee, and an inactive member, it returns nothing")
    @MembershipInactivePerson
    @PaidFee
    void testFindAllInYearForActiveMembers_Inactive_NotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month, and an inactive member, it returns nothing")
    @MembershipInactivePerson
    @PaidFee
    void testFindAllInYearForActiveMembers_Inactive_Paid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data, it returns nothing")
    void testFindAllInYearForActiveMembers_NoData() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
