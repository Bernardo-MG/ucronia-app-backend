/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.test.member.service.integration;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.DtoMember;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.MemberQuery;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.test.member.util.model.MembersQuery;

@IntegrationTest
@DisplayName("Member service - get all - pagination")
@Sql({ "/db/queries/member/multiple.sql" })
public class ITMemberServiceGetAllPagination {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("With an active pagination, the returned data is contained in a page")
    public void testGetAll_Page_Container() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        pageable = Pageable.ofSize(10);

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("With pagination for the first page, it returns the first page")
    public void testGetAll_Page1() {
        final MemberQuery      memberQuery;
        final Iterable<Member> members;
        final Member           member;
        final Pageable         pageable;

        pageable = PageRequest.of(0, 1);

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(IterableUtils.size(members))
            .isEqualTo(1);

        member = members.iterator()
            .next();
        MemberAssertions.isEqualTo(member, DtoMember.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build());
    }

    @Test
    @DisplayName("With pagination for the second page, it returns the second page")
    public void testGetAll_Page2() {
        final MemberQuery      memberQuery;
        final Iterable<Member> members;
        final Member           member;
        final Pageable         pageable;

        pageable = PageRequest.of(1, 1);

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(IterableUtils.size(members))
            .isEqualTo(1);

        member = members.iterator()
            .next();
        MemberAssertions.isEqualTo(member, DtoMember.builder()
            .name("Member 2")
            .surname("Surname 2")
            .phone("12346")
            .identifier("6790")
            .active(true)
            .build());
    }

    @Test
    @DisplayName("With an inactive pagination, the returned data is contained in a page")
    public void testGetAll_Unpaged_Container() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .isInstanceOf(Page.class);
    }

}