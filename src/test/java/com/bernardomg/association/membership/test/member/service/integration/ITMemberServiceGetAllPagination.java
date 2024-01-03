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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.MemberQuery;
import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.fee.config.MultipleFees;
import com.bernardomg.association.membership.test.member.config.MultipleMembers;
import com.bernardomg.association.membership.test.member.config.factory.Members;
import com.bernardomg.association.membership.test.member.config.factory.MembersQuery;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get all - pagination")
@MultipleMembers
@MultipleFees
class ITMemberServiceGetAllPagination {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("With an active pagination, the returned data is contained in a page")
    void testGetAll_Page_Container() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.ofSize(10);

        memberQuery = MembersQuery.empty();

        // WHEN
        members = service.getAll(memberQuery, pageable);

        // THEN
        Assertions.assertThat(members)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("With pagination for the first page, it returns the first page")
    void testGetAll_Page1() {
        final MemberQuery      memberQuery;
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 1);

        memberQuery = MembersQuery.empty();

        // WHEN
        members = service.getAll(memberQuery, pageable);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forIndex(1, false));
    }

    @Test
    @DisplayName("With pagination for the second page, it returns the second page")
    void testGetAll_Page2() {
        final MemberQuery      memberQuery;
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(1, 1);

        memberQuery = MembersQuery.empty();

        // WHEN
        members = service.getAll(memberQuery, pageable);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forIndex(2, false));
    }

    @Test
    @DisplayName("With an inactive pagination, the returned data is contained in a page")
    void testGetAll_Unpaged_Container() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        // WHEN
        members = service.getAll(memberQuery, pageable);

        // THEN
        Assertions.assertThat(members)
            .isInstanceOf(Page.class);
    }

}
