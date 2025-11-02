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
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActiveContact;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactiveContact;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all in year for inactive")
class ITFeeRepositoryFindAllInYearForInactiveMembers {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With a not paid fee, and an inactive member, it returns nothing")
    @MembershipActiveContact
    @NotPaidFee
    void testFindAllInYearForInactiveMembers_Active_NotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee, and an inactive member, it returns nothing")
    @MembershipActiveContact
    @PaidFee
    void testFindAllInYearForInactiveMembers_Active_Paid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee, it returns the calendar")
    @MembershipInactiveContact
    @NotPaidFee
    void testFindAllInYearForInactiveMembers_Inactive_NotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidForMonth(1, Month.FEBRUARY));
    }

    @Test
    @DisplayName("With a not paid fee and searching for the next year, it returns nothing")
    @MembershipInactiveContact
    @NotPaidFee
    void testFindAllInYearForInactiveMembers_Inactive_NotPaid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR.plusYears(1), sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee and searching for the previous year, it returns nothing")
    @MembershipInactiveContact
    @NotPaidFee
    void testFindAllInYearForInactiveMembers_Inactive_NotPaid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR.minusYears(1), sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee, it returns the calendar")
    @MembershipInactiveContact
    @PaidFee
    void testFindAllInYearForInactiveMembers_Inactive_Paid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY));
    }

    @Test
    @DisplayName("With a paid fee and searching for the next year, it returns nothing")
    @MembershipInactiveContact
    @PaidFee
    void testFindAllInYearForInactiveMembers_Inactive_Paid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR.plusYears(1), sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee and searching for the previous year, it returns nothing")
    @MembershipInactiveContact
    @PaidFee
    void testFindAllInYearForInactiveMembers_Inactive_Paid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR.minusYears(1), sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With both a paid and not paid fees, it returns the calendar")
    @MembershipInactiveContact
    @PaidAndNotPaidFee
    void testFindAllInYearForInactiveMembers_Inactive_PaidAndNotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY), Fees.notPaidForMonth(2, 10, Month.MARCH));
    }

    @Test
    @DisplayName("With no data, it returns nothing")
    void testFindAllInYearForInactiveMembers_NoData() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        fees = repository.findAllInYearForInactiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
