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
import com.bernardomg.association.membership.member.model.MemberQuery;
import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.fee.config.MultipleFees;
import com.bernardomg.association.membership.test.member.configuration.MultipleMembers;
import com.bernardomg.association.membership.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.membership.test.member.util.model.MembersQuery;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get all")
@MultipleMembers
@MultipleFees
class ITMemberServiceGetAll {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("With multiple members it returns all the members")
    void testGetAll() {
        final Iterable<Member> members;
        final Iterator<Member> membersItr;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        Member                 member;

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(members)
            .hasSize(5);

        membersItr = members.iterator();

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 1")
            .surname("Surname 1")
            .phone("12345")
            .identifier("6789")
            .build());

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 2")
            .surname("Surname 2")
            .phone("12346")
            .identifier("6790")
            .build());

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 3")
            .surname("Surname 3")
            .phone("12347")
            .identifier("6791")
            .build());

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 4")
            .surname("Surname 4")
            .phone("12348")
            .identifier("6792")
            .build());

        member = membersItr.next();
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 5")
            .surname("Surname 5")
            .phone("12349")
            .identifier("6793")
            .build());
    }

}
