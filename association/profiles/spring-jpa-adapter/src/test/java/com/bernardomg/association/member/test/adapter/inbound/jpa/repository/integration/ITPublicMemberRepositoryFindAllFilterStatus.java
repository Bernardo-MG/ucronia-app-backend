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
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFeesWithGaps;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.domain.filter.PublicMemberFilter;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.factory.PublicMemberFilters;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublicMemberRepository - find all - filter by status")
class ITPublicMemberRepositoryFindAllFilterStatus {

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
        filter = PublicMemberFilters.active();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.active());
    }

    @Test
    @DisplayName("When filtering by active, an active member is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindAll_ActiveFilter_Active() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = PublicMemberFilters.active();

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.active());
    }

    @Test
    @DisplayName("When filtering by active, an inactive member isn't returned")
    @PositiveFeeType
    @InactiveMember
    void testFindAll_ActiveFilter_Inactive() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = PublicMemberFilters.active();

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
        filter = PublicMemberFilters.all();

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(PublicMembers.active());
    }

    @Test
    @DisplayName("When filtering by all, an inactive member is returned")
    @PositiveFeeType
    @InactiveMember
    void testFindAll_AllFilter_Inactive() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = PublicMemberFilters.all();

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(PublicMembers.inactive());
    }

    @Test
    @DisplayName("When filtering by inactive, an active member isn't returned")
    @PositiveFeeType
    @ActiveMember
    void testFindAll_InactiveFilter_Active() {
        final Page<PublicMember> members;
        final PublicMemberFilter filter;

        // GIVEN
        filter = PublicMemberFilters.inactive();

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
        filter = PublicMemberFilters.inactive();

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.inactive());
    }

    @Test
    @DisplayName("The last paid month is returned for an inactive member")
    @PositiveFeeType
    @InactiveMember
    @PaidFeesWithGaps
    void testFindAll_InactiveWithPaidFees() {
        final PublicMemberFilter filter;
        final Page<PublicMember> members;

        // GIVEN
        filter = PublicMemberFilters.inactive();

        // WHEN
        members = repository.findAll(filter, new Pagination(1, 100), Sorting.unsorted());

        // THEN
        Assertions.assertThat(members.content())
            .singleElement()
            .extracting(PublicMember::lastPaidMonth)
            .isEqualTo(Optional.of(FeeConstants.APRIL_DATE));
    }

}
