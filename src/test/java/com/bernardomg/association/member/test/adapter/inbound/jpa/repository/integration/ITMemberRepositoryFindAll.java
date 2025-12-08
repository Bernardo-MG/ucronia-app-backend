/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.test.configuration.data.annotation.ValidContact;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - find all public")
class ITMemberRepositoryFindAll {

    @Autowired
    private MemberRepository repository;

    public ITMemberRepositoryFindAll() {
        super();
    }

    @Test
    @DisplayName("When filtering by active with an active member, it returns the member")
    @ActiveMember
    void testFindAll_Active_Active() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberStatus status;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        status = MemberStatus.ACTIVE;

        // WHEN
        members = repository.findAll(status, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .containsExactly(Members.active());
    }

    @Test
    @DisplayName("When filtering by active with an inactive member, it returns nothing")
    @InactiveMember
    void testFindAll_Active_Inactive() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberStatus status;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        status = MemberStatus.ACTIVE;

        // WHEN
        members = repository.findAll(status, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by all with an active member, it returns the member")
    @ActiveMember
    void testFindAll_All_Active() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberStatus status;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        status = MemberStatus.ALL;

        // WHEN
        members = repository.findAll(status, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .containsExactly(Members.active());
    }

    @Test
    @DisplayName("When filtering by all with an inactive member, it returns the member")
    @InactiveMember
    void testFindAll_All_Inactive() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberStatus status;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        status = MemberStatus.ALL;

        // WHEN
        members = repository.findAll(status, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .containsExactly(Members.inactive());
    }

    @Test
    @DisplayName("When filtering by all with no data, it returns nothing")
    void testFindAll_All_NoData() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberStatus status;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        status = MemberStatus.ALL;

        // WHEN
        members = repository.findAll(status, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by all with a contact with no membership, it returns nothing")
    @ValidContact
    void testFindAll_All_NoMembership() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberStatus status;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        status = MemberStatus.ALL;

        // WHEN
        members = repository.findAll(status, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by active with an active member, it returns nothing")
    @ActiveMember
    void testFindAll_Inactive_Active() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberStatus status;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        status = MemberStatus.INACTIVE;

        // WHEN
        members = repository.findAll(status, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by inactive with an inactive member, it returns the member")
    @InactiveMember
    void testFindAll_Inactive_Inactive() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberStatus status;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        status = MemberStatus.INACTIVE;

        // WHEN
        members = repository.findAll(status, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .containsExactly(Members.inactive());
    }

}
