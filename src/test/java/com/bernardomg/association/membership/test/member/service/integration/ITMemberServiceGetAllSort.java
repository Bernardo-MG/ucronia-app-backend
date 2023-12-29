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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.MemberQuery;
import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.fee.config.MultipleFees;
import com.bernardomg.association.membership.test.member.configuration.MultipleMembers;
import com.bernardomg.association.membership.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.membership.test.member.util.model.MembersQuery;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get all - sort")
@MultipleMembers
@MultipleFees
class ITMemberServiceGetAllSort {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAllSort() {
        super();
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    void testGetAll_Name_Asc() {
        final Iterator<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        pageable = PageRequest.of(0, 10, Direction.ASC, "name");

        memberQuery = MembersQuery.empty();

        // FIXME: names should be sorted ignoring case
        members = service.getAll(memberQuery, pageable)
            .iterator();

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 2")
            .surname("Surname 2")
            .phone("12346")
            .identifier("6790")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 3")
            .surname("Surname 3")
            .phone("12347")
            .identifier("6791")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 4")
            .surname("Surname 4")
            .phone("12348")
            .identifier("6792")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 5")
            .surname("Surname 5")
            .phone("12349")
            .identifier("6793")
            .build());
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    void testGetAll_Name_Desc() {
        final Iterator<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        pageable = PageRequest.of(0, 10, Direction.DESC, "name");

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable)
            .iterator();

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 5")
            .surname("Surname 5")
            .phone("12349")
            .identifier("6793")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 4")
            .surname("Surname 4")
            .phone("12348")
            .identifier("6792")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 3")
            .surname("Surname 3")
            .phone("12347")
            .identifier("6791")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 2")
            .surname("Surname 2")
            .phone("12346")
            .identifier("6790")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .build());
    }

    @Test
    @DisplayName("With ascending order by surname it returns the ordered data")
    void testGetAll_Surname_Asc() {
        final Iterator<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        pageable = PageRequest.of(0, 10, Direction.ASC, "surname");

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable)
            .iterator();

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 2")
            .surname("Surname 2")
            .phone("12346")
            .identifier("6790")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 3")
            .surname("Surname 3")
            .phone("12347")
            .identifier("6791")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 4")
            .surname("Surname 4")
            .phone("12348")
            .identifier("6792")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 5")
            .surname("Surname 5")
            .phone("12349")
            .identifier("6793")
            .build());
    }

    @Test
    @DisplayName("With descending order by surname it returns the ordered data")
    void testGetAll_Surname_Desc() {
        final Iterator<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        pageable = PageRequest.of(0, 10, Direction.DESC, "surname");

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable)
            .iterator();

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 5")
            .surname("Surname 5")
            .phone("12349")
            .identifier("6793")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 4")
            .surname("Surname 4")
            .phone("12348")
            .identifier("6792")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 3")
            .surname("Surname 3")
            .phone("12347")
            .identifier("6791")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 2")
            .surname("Surname 2")
            .phone("12346")
            .identifier("6790")
            .build());

        member = members.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .build());
    }

}
