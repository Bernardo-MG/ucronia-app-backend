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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFeesWithGaps;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.domain.filter.PublicMemberFilter;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveToNotRenewMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.MultipleActiveMember;
import com.bernardomg.association.member.test.configuration.factory.PublicMemberFilters;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublicMemberRepository - find all")
class ITPublicMemberRepositoryFindAll {

    @Autowired
    private PublicMemberRepository repository;

    @Test
    @DisplayName("With an active member, it is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindAll() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.empty();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.valid());
    }

    @Test
    @DisplayName("When filtering by active, an active member is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindAll_ActiveFilter_Active() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = new PublicMemberFilter(Optional.empty(), MemberStatus.ACTIVE);

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.valid());
    }

    @Test
    @DisplayName("When filtering by active, an inactive member isn't returned")
    @PositiveFeeType
    @InactiveMember
    void testFindAll_ActiveFilter_Inactive() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = new PublicMemberFilter(Optional.empty(), MemberStatus.ACTIVE);

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by all, an active member is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindAll_AllFilter_Active() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = new PublicMemberFilter(Optional.empty(), MemberStatus.ALL);

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(PublicMembers.valid());
    }

    @Test
    @DisplayName("When filtering by all, an inactive member is returned")
    @PositiveFeeType
    @InactiveMember
    void testFindAll_AllFilter_Inactive() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = new PublicMemberFilter(Optional.empty(), MemberStatus.ALL);

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(PublicMembers.valid());
    }

    @Test
    @DisplayName("With an inactive member, nothing is returned")
    @PositiveFeeType
    @InactiveMember
    void testFindAll_Inactive() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.empty();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by inactive, an active member isn't returned")
    @PositiveFeeType
    @ActiveMember
    void testFindAll_InactiveFilter_Active() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = new PublicMemberFilter(Optional.empty(), MemberStatus.INACTIVE);

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by inactive, an inactive member is returned")
    @PositiveFeeType
    @InactiveMember
    void testFindAll_InactiveFilter_Inactive() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = new PublicMemberFilter(Optional.empty(), MemberStatus.INACTIVE);

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.valid());
    }

    @Test
    @DisplayName("With no member, nothing is returned")
    void testFindAll_NoData() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.empty();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With an active member with no renewal, it is returned")
    @PositiveFeeType
    @ActiveToNotRenewMember
    void testFindAll_NoRenew() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.empty();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.noRenew());
    }

    @Test
    @DisplayName("With fees for several members, the data is returned")
    @PositiveFeeType
    @MultipleActiveMember
    @MultipleFees
    void testFindAll_PaidPerMember() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.empty();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.withMembership(1, FeeConstants.FEBRUARY_DATE, 1L),
                PublicMembers.withMembership(2, FeeConstants.MARCH_DATE, 1L),
                PublicMembers.withMembership(3, FeeConstants.APRIL_DATE, 1L),
                PublicMembers.withMembership(4, FeeConstants.MAY_DATE, 1L), PublicMembers.forNumber(5));
    }

    @Test
    @DisplayName("With paid fees separated by gaps, the data is returned")
    @PositiveFeeType
    @ActiveMember
    @PaidFeesWithGaps
    void testFindAll_PaidWithGaps() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.empty();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.withMembership(FeeConstants.FEBRUARY_DATE, 2L));
    }

    @Test
    @DisplayName("With a profile with no member role, nothing is returned")
    @ValidProfile
    void testFindAll_WithoutMembership() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.empty();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
