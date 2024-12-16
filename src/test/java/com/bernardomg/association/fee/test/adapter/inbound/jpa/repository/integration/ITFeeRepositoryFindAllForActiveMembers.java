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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.test.configuration.initializer.FeeInitializer;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactivePerson;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all for active")
class ITFeeRepositoryFindAllForActiveMembers {

    @Autowired
    private FeeInitializer feeInitializer;

    @Autowired
    private FeeRepository  repository;

    @Test
    @DisplayName("With a not paid fee in the current month, it returns the calendar")
    @MembershipActivePerson
    void testFindAllForActiveMembers_Active_CurrentMonth_NotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.CURRENT_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidCurrentMonth());
    }

    @Test
    @DisplayName("With a not paid fee in the current month and searching for the next year, it returns nothing")
    @MembershipActivePerson
    void testFindAllForActiveMembers_Active_CurrentMonth_NotPaid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.NEXT_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the current month and searching for the previous year, it returns nothing")
    @MembershipActivePerson
    void testFindAllForActiveMembers_Active_CurrentMonth_NotPaid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.PREVIOUS_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month, it returns the calendar")
    @MembershipActivePerson
    void testFindAllForActiveMembers_Active_CurrentMonth_Paid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.CURRENT_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidCurrentMonth(1));
    }

    @Test
    @DisplayName("With a paid fee in the current month and searching for the next year, it returns nothing")
    @MembershipActivePerson
    void testFindAllForActiveMembers_Active_CurrentMonth_Paid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.NEXT_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month and searching for the previous year, it returns nothing")
    @MembershipActivePerson
    void testFindAllForActiveMembers_Active_CurrentMonth_Paid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.PREVIOUS_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the current month, and an inactive member, it returns nothing")
    @MembershipInactivePerson
    void testFindAllForActiveMembers_Inactive_CurrentMonth_NotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.CURRENT_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month, and an inactive member, it returns nothing")
    @MembershipInactivePerson
    void testFindAllForActiveMembers_Inactive_CurrentMonth_Paid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.CURRENT_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data, it returns nothing")
    void testFindAllForActiveMembers_NoData() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of());

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.CURRENT_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
