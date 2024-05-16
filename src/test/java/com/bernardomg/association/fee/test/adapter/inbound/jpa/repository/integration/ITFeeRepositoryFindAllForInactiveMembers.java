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

import java.time.Year;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.test.config.initializer.FeeInitializer;
import com.bernardomg.association.member.test.config.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.config.data.annotation.InactiveMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all for inactive")
class ITFeeRepositoryFindAllForInactiveMembers {

    @Autowired
    private FeeInitializer feeInitializer;

    @Autowired
    private FeeRepository  repository;

    @Test
    @DisplayName("With a not paid fee in the current month, and an inactive member, it returns nothing")
    @ActiveMember
    void testFindAllForInactiveMembers_Active_CurrentMonth_NotPaid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.CURRENT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month, and an inactive member, it returns nothing")
    @ActiveMember
    void testFindAllForInactiveMembers_Active_CurrentMonth_Paid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.CURRENT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the current month, it returns the calendar")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_CurrentMonth_NotPaid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.CURRENT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidCurrentMonth());
    }

    @Test
    @DisplayName("With a not paid fee in the current month and searching for the next year, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_CurrentMonth_NotPaid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.NEXT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the current month and searching for the previous year, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_CurrentMonth_NotPaid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.PREVIOUS_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month, it returns the calendar")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_CurrentMonth_Paid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.CURRENT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidCurrentMonth(1));
    }

    @Test
    @DisplayName("With a paid fee in the current month and searching for the next year, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_CurrentMonth_Paid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.NEXT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month and searching for the previous year, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_CurrentMonth_Paid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.PREVIOUS_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the next year, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_NextYear_NotPaid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeNextYear(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.NEXT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the next year, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_NextYear_Paid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeNextYear(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.NEXT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With no fees, it nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_NoFees() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.CURRENT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the previous month, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_PreviousMonth_NotPaid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(Year.of(FeeConstants.PREVIOUS_MONTH.getYear()), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the previous month, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_PreviousMonth_Paid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(Year.of(FeeConstants.PREVIOUS_MONTH.getYear()), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee two months back, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_TwoMonthsBack_NotPaid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(Year.of(FeeConstants.TWO_MONTHS_BACK.getYear()), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee two months back, it returns nothing")
    @InactiveMember
    void testFindAllForInactiveMembers_Inactive_TwoMonthsBack_Paid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(Year.of(FeeConstants.TWO_MONTHS_BACK.getYear()), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data, it returns nothing")
    void testFindAllForInactiveMembers_NoData() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllForInactiveMembers(FeeConstants.CURRENT_YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
