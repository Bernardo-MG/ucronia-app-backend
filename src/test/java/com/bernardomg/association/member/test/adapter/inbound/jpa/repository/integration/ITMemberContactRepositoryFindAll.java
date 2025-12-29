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

import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberContactRepository - find all")
class ITMemberContactRepositoryFindAll {

    @Autowired
    private MemberContactRepository repository;

    @Test
    @DisplayName("With an active member and filtering by active, it is returned")
    @ActiveMember
    void testFindAll_Active_Active() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ACTIVE, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(MemberContacts.active());
    }

    @Test
    @DisplayName("With an inactive member and filtering by active, nothing is returned")
    @InactiveMember
    void testFindAll_Active_Inactive() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ACTIVE, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With no member and filtering by active, nothing is returned")
    void testFindAll_Active_NoData() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ACTIVE, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a profile with no member role and filtering by active, nothing is returned")
    @ValidProfile
    void testFindAll_Active_WithoutMembership() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ACTIVE, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With an active member and filtering by all, it is returned")
    @ActiveMember
    void testFindAll_All_Active() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(MemberContacts.active());
    }

    @Test
    @DisplayName("With an inactive member and filtering by all, it is returned")
    @InactiveMember
    void testFindAll_All_Inactive() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(MemberContacts.inactive());
    }

    @Test
    @DisplayName("With no member and filtering by all, nothing is returned")
    void testFindAll_All_NoData() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a profile with no member role and filtering by all, it is returned")
    @ValidProfile
    void testFindAll_All_WithoutMembership() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With an active member and filtering by inactive, nothing is returned")
    @ActiveMember
    void testFindAll_Inactive_Active() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.INACTIVE, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With an inactive member and filtering by inactive, it is returned")
    @InactiveMember
    void testFindAll_Inactive_Inactive() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.INACTIVE, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(MemberContacts.inactive());
    }

    @Test
    @DisplayName("With no member and filtering by inactive, nothing is returned")
    void testFindAll_Inactive_NoData() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.INACTIVE, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a profile with no member role and filtering by inactive, nothing is returned")
    @ValidProfile
    void testFindAll_Inactive_WithoutMembership() {
        final Page<MemberProfile> members;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.INACTIVE, "");

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
