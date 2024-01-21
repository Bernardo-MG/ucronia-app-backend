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

package com.bernardomg.association.member.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.outbound.model.MemberQuery;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.member.test.config.factory.MembersQuery;
import com.bernardomg.association.member.usecase.DefaultMemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member service - get all")
class ITMemberServiceGetAllFilter {

    @Mock
    private MemberRepository       activeMemberSource;

    @Mock
    private MemberSpringRepository memberRepository;

    @InjectMocks
    private DefaultMemberService   service;

    public ITMemberServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("When filtering with by active it returns the active members")
    void testGetAll_FilterActive_ReturnsData() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        final Page<Member>     readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(activeMemberSource.findActive(ArgumentMatchers.any())).willReturn(readMembers);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        // WHEN
        members = service.getAll(memberQuery, pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEqualTo(readMembers);
    }

    @Test
    @DisplayName("When filtering with the default filter it returns all the members")
    void testGetAll_FilterDefault_ReturnsData() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        final Page<Member>     readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(activeMemberSource.findAll(ArgumentMatchers.any())).willReturn(readMembers);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.empty();

        // WHEN
        members = service.getAll(memberQuery, pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEqualTo(readMembers);
    }

    @Test
    @DisplayName("When filtering with by active it returns the not active members")
    void testGetAll_FilterNotActive_ReturnsData() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        final Page<Member>     readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(activeMemberSource.findInactive(ArgumentMatchers.any())).willReturn(readMembers);

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.inactive();

        // WHEN
        members = service.getAll(memberQuery, pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEqualTo(readMembers);
    }

}
