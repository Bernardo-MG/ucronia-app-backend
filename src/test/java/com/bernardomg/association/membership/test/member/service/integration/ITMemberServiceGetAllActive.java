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

import java.time.YearMonth;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.membership.test.member.util.model.DtoMembers;
import com.bernardomg.association.membership.test.member.util.model.MembersQuery;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Member service - get all - active status handling")
@Sql({ "/db/queries/member/multiple.sql" })
@Sql({ "/db/queries/fee/multiple.sql" })
class ITMemberServiceGetAllActive {

    private static final YearMonth CURRENT_MONTH   = YearMonth.now();

    private static final YearMonth PREVIOUS_MONTH  = YearMonth.now()
        .minusMonths(1);

    private static final YearMonth TWO_MONTHS_BACK = YearMonth.now()
        .minusMonths(2);

    @Autowired
    private FeeRepository          feeRepository;

    @Autowired
    private MemberService          service;

    public ITMemberServiceGetAllActive() {
        super();
    }

    private final void registerFeeCurrentMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(CURRENT_MONTH);

        feeRepository.save(fee);
    }

    private final void registerFeePreviousMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(PREVIOUS_MONTH);

        feeRepository.save(fee);
    }

    private final void registerFeeTwoMonthsBack(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(TWO_MONTHS_BACK);

        feeRepository.save(fee);
    }

    @Test
    @DisplayName("With a member with a not paid fee for the current month, and filtering by active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterActive_CurrentMonth_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeCurrentMonth(false);

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
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterActive_CurrentMonth_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeCurrentMonth(true);

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
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterActive_LastThreeMonths_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

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
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterActive_LastThreeMonths_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

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
    @Sql({ "/db/queries/member/single.sql" })
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
    @DisplayName("With a member with a not paid fee for the previous month, and filtering by active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterActive_PreviousMonth_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeePreviousMonth(false);

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
    @DisplayName("With a member with a paid fee for the previous month, and filtering by active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterActive_PreviousMonth_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeePreviousMonth(true);

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
    @DisplayName("With a member with a not paid fee for two months back, and filtering by active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterActive_TwoMonthsBack_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a paid fee for two months back, and filtering by active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterActive_TwoMonthsBack_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        registerFeeTwoMonthsBack(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a not paid fee for the current month, and not filtering, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterDefault_CurrentMonth_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeCurrentMonth(false);

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
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterDefault_CurrentMonth_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeCurrentMonth(true);

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
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterDefault_LastThreeMonths_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

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
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterDefault_LastThreeMonths_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

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
    @Sql({ "/db/queries/member/single.sql" })
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
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterDefault_PreviousMonth_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeePreviousMonth(false);

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
    @DisplayName("With a member with a paid fee for the previous month, and not filtering, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterDefault_PreviousMonth_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeePreviousMonth(true);

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
    @DisplayName("With a member with a not paid fee for two months back, and not filtering, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterDefault_TwoMonthsBack_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeTwoMonthsBack(false);

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
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterDefault_TwoMonthsBack_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeTwoMonthsBack(true);

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
    @DisplayName("With a member with a not paid fee for the current month, and filtering by not active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterInactive_CurrentMonth_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        registerFeeCurrentMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a paid fee for the current month, and filtering by not active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterInactive_CurrentMonth_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        registerFeeCurrentMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a not paid fee for the last three months, and filtering by not active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterInactive_LastThreeMonths_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a paid fee for the last three months, and filtering by not active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterInactive_LastThreeMonths_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with no fee for the current month, and filtering by not active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterInactive_NoFee() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a not paid fee for the previous month, and filtering by not active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterInactive_PreviousMonth_NotPaid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        registerFeePreviousMonth(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a paid fee for the previous month, and filtering by not active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterInactive_PreviousMonth_Paid() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        registerFeePreviousMonth(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a member with a not paid fee for two months back, and filtering by not active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterInactive_TwoMonthsBack_NotPaid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeTwoMonthsBack(false);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

    @Test
    @DisplayName("With a member with a paid fee for two months back, and filtering by not active, it returns the member")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetAll_FilterInactive_TwoMonthsBack_Paid() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        registerFeeTwoMonthsBack(true);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(1);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, DtoMembers.inactive());
    }

}
