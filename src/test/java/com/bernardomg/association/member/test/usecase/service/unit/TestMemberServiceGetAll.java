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

package com.bernardomg.association.member.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberQuery;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.test.configuration.factory.MembersQuery;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("Public member service - get all")
class TestMemberServiceGetAll {

    @Mock
    private MemberRepository     publicMemberRepository;

    @InjectMocks
    private DefaultMemberService service;

    @Test
    @DisplayName("When filtering with by active it returns the active members")
    void testGetAll_FilterActive_ReturnsData() {
        final Iterable<Member>   members;
        final MemberQuery        memberQuery;
        final Pagination         pagination;
        final Sorting            sorting;
        final Collection<Member> readMembers;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();

        readMembers = List.of(Members.valid());
        given(publicMemberRepository.findActive(pagination, sorting)).willReturn(readMembers);

        memberQuery = MembersQuery.active();

        // WHEN
        members = service.getAll(memberQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEqualTo(readMembers);
    }

    @Test
    @DisplayName("When filtering with the default filter, and there is no data, it returns nothing")
    void testGetAll_FilterDefault_NoData() {
        final Iterable<Member>   members;
        final MemberQuery        memberQuery;
        final Pagination         pagination;
        final Sorting            sorting;
        final Collection<Member> readMembers;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();

        readMembers = List.of();
        given(publicMemberRepository.findAll(pagination, sorting)).willReturn(readMembers);

        memberQuery = MembersQuery.empty();

        // WHEN
        members = service.getAll(memberQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering with the default filter it returns all the members")
    void testGetAll_FilterDefault_ReturnsData() {
        final Iterable<Member>   members;
        final MemberQuery        memberQuery;
        final Pagination         pagination;
        final Sorting            sorting;
        final Collection<Member> readMembers;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();

        readMembers = List.of(Members.valid());
        given(publicMemberRepository.findAll(pagination, sorting)).willReturn(readMembers);

        memberQuery = MembersQuery.empty();

        // WHEN
        members = service.getAll(memberQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEqualTo(readMembers);
    }

    @Test
    @DisplayName("When filtering with by active it returns the not active members")
    void testGetAll_FilterNotActive_ReturnsData() {
        final Iterable<Member>   members;
        final MemberQuery        memberQuery;
        final Pagination         pagination;
        final Sorting            sorting;
        final Collection<Member> readMembers;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = Sorting.unsorted();

        readMembers = List.of(Members.valid());
        given(publicMemberRepository.findInactive(pagination, sorting)).willReturn(readMembers);

        memberQuery = MembersQuery.inactive();

        // WHEN
        members = service.getAll(memberQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEqualTo(readMembers);
    }

}
