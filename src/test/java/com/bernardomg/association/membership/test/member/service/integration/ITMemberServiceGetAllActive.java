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
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Member service - get all - active status handling")
@ValidMember
class ITMemberServiceGetAllActive {

    @Autowired
    private FeeInitializer feeInitializer;

    @Autowired
    private MemberService  service;

    public ITMemberServiceGetAllActive() {
        super();
    }

    @Test
    @DisplayName("With a member with a not paid fee for the current month, and filtering by active, it returns the member")
    void testGetAll_FilterActive_CurrentMonth_NotPaid() {
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
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a paid fee for the current month, and filtering by active, it returns the member")
    void testGetAll_FilterActive_CurrentMonth_Paid() {
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
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the last three months, and filtering by active, it returns the member")
    void testGetAll_FilterActive_LastThreeMonths_NotPaid() {
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
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a paid fee for the last three months, and filtering by active, it returns the member")
    void testGetAll_FilterActive_LastThreeMonths_Paid() {
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
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with no fee for the current month, and filtering by active, it returns no member")
    void testGetAll_FilterActive_NoFee() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a not paid fee for the previous month, and filtering by active, it returns nothing")
    void testGetAll_FilterActive_PreviousMonth_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeePreviousMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a paid fee for the previous month, and filtering by active, it returns nothing")
    void testGetAll_FilterActive_PreviousMonth_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeePreviousMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a not paid fee for two months back, and filtering by active, it returns the member")
    void testGetAll_FilterActive_TwoMonthsBack_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a paid fee for two months back, and filtering by active, it returns the member")
    void testGetAll_FilterActive_TwoMonthsBack_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeTwoMonthsBack(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a not paid fee for the current month, and not filtering, it returns the member")
    void testGetAll_FilterDefault_CurrentMonth_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeCurrentMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a paid fee for the current month, and not filtering, it returns the member")
    void testGetAll_FilterDefault_CurrentMonth_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeCurrentMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the last three months, and not filtering, it returns the member")
    void testGetAll_FilterDefault_LastThreeMonths_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with a paid fee for the last three months, and not filtering, it returns the member")
    void testGetAll_FilterDefault_LastThreeMonths_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.active());
    }

    @Test
    @DisplayName("With a member with no fee for the current month, and not filtering, it returns the member")
    void testGetAll_FilterDefault_NoFee() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the previous month, and not filtering, it returns the member")
    void testGetAll_FilterDefault_PreviousMonth_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeePreviousMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a paid fee for the previous month, and not filtering, it returns the member")
    void testGetAll_FilterDefault_PreviousMonth_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeePreviousMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a not paid fee for two months back, and not filtering, it returns the member")
    void testGetAll_FilterDefault_TwoMonthsBack_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a paid fee for two months back, and not filtering, it returns the member")
    void testGetAll_FilterDefault_TwoMonthsBack_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeTwoMonthsBack(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the current month, and filtering by inactive, it returns the member")
    void testGetAll_FilterInactive_CurrentMonth_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeCurrentMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a paid fee for the current month, and filtering by inactive, it returns the member")
    void testGetAll_FilterInactive_CurrentMonth_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeCurrentMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a not paid fee for the last three months, and filtering by inactive, it returns the member")
    void testGetAll_FilterInactive_LastThreeMonths_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a paid fee for the last three months, and filtering by inactive, it returns the member")
    void testGetAll_FilterInactive_LastThreeMonths_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with no fee for the current month, and filtering by inactive, it returns the member")
    void testGetAll_FilterInactive_NoFee() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the previous month, and filtering by inactive, it returns the member")
    void testGetAll_FilterInactive_PreviousMonth_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeePreviousMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a paid fee for the previous month, and filtering by inactive, it returns the member")
    void testGetAll_FilterInactive_PreviousMonth_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeePreviousMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a not paid fee for two months back, and filtering by inactive, it returns the member")
    void testGetAll_FilterInactive_TwoMonthsBack_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a paid fee for two months back, and filtering by inactive, it returns the member")
    void testGetAll_FilterInactive_TwoMonthsBack_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        feeInitializer.registerFeeTwoMonthsBack(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

}
