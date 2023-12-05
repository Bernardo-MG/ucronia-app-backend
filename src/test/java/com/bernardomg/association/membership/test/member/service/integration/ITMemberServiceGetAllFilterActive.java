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

package com.bernardomg.association.membership.test.member.service.integration;

import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.fee.util.initializer.FeeInitializer;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.association.membership.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.membership.test.member.util.model.DtoMembers;
import com.bernardomg.association.membership.test.member.util.model.MembersQuery;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get all - filter by active")
@ValidMember
class ITMemberServiceGetAllFilterActive {

    @Autowired
    private FeeInitializer feeInitializer;

    @Autowired
    private MemberService  service;

    public ITMemberServiceGetAllFilterActive() {
        super();
    }

    @Test
    @DisplayName("With a member with a not paid fee for the current month it returns the member")
    void testGetAll_CurrentMonth_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeCurrentMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a paid fee for the current month it returns the member")
    void testGetAll_CurrentMonth_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeCurrentMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the last three months it returns the member")
    void testGetAll_LastThreeMonths_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a paid fee for the last three months it returns the member")
    void testGetAll_LastThreeMonths_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the next month it returns nothing")
    void testGetAll_NextMonth_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeNextMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a paid fee for the next month it returns nothing")
    void testGetAll_NextMonth_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeNextMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with no fees it returns no member")
    void testGetAll_NoFee() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a not paid fee for the previous month it returns nothing")
    void testGetAll_PreviousMonth_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeePreviousMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a paid fee for the previous month it returns nothing")
    void testGetAll_PreviousMonth_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeePreviousMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a not paid fee for two months back it returns the member")
    void testGetAll_TwoMonthsBack_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with a paid fee for two months back it returns the member")
    void testGetAll_TwoMonthsBack_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeTwoMonthsBack(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

}
