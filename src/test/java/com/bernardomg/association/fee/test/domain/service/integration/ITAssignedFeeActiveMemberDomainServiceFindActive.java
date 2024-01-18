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

package com.bernardomg.association.fee.test.domain.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.fee.domain.service.AssignedFeeActiveMemberDomainService;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.test.data.fee.initializer.FeeInitializer;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("AssignedFeeActiveMemberDomainService - find active")
class ITAssignedFeeActiveMemberDomainServiceFindActive {

    @Autowired
    private FeeInitializer                       feeInitializer;

    @Autowired
    private AssignedFeeActiveMemberDomainService service;

    public ITAssignedFeeActiveMemberDomainServiceFindActive() {
        super();
    }

    @Test
    @DisplayName("With a member with a not paid fee for the current month it returns the member")
    @ValidMember
    void testFindActive_CurrentMonth_NotPaid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(Members.active());
    }

    @Test
    @DisplayName("With a member with a paid fee for the current month it returns the member")
    @ValidMember
    void testFindActive_CurrentMonth_Paid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(Members.active());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the last three months it returns the member")
    @ValidMember
    void testFindActive_LastThreeMonths_NotPaid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(Members.active());
    }

    @Test
    @DisplayName("With a member with a paid fee for the last three months it returns the member")
    @ValidMember
    void testFindActive_LastThreeMonths_Paid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(Members.active());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the next month it returns nothing")
    @ValidMember
    void testFindActive_NextMonth_NotPaid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeNextMonth(false);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a paid fee for the next month it returns nothing")
    @ValidMember
    void testFindActive_NextMonth_Paid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeNextMonth(true);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with no fees it returns no member")
    @ValidMember
    void testFindActive_NoFee() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a not paid fee for the previous month it returns nothing")
    @ValidMember
    void testFindActive_PreviousMonth_NotPaid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(false);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a paid fee for the previous month it returns nothing")
    @ValidMember
    void testFindActive_PreviousMonth_Paid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a not paid fee for two months back it returns the member")
    @ValidMember
    void testFindActive_TwoMonthsBack_NotPaid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a paid fee for two months back it returns the member")
    @ValidMember
    void testFindActive_TwoMonthsBack_Paid() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(true);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testFindAll_NoData() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        members = service.findActive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

}
