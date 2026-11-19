
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
@DisplayName("PublicMemberRepository - findAll")
class ITPublicMemberRepositoryFindAllFilterStatus {

    @Autowired
    private PublicMemberRepository repository;

    @Test
    @DisplayName("When there is an active member, then it is returned")
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
            .as("When there is an active member, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.active());
    }

    @Test
    @DisplayName("When filtering by active, then an active member is returned")
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
            .as("When filtering by active, then an active member is returned")
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.active());
    }

    @Test
    @DisplayName("When filtering by active, then an inactive member isn't returned")
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
            .as("When filtering by active, then an inactive member isn't returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by all, then an active member is returned")
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
            .as("When filtering by all, then an active member is returned")
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(PublicMembers.active());
    }

    @Test
    @DisplayName("When filtering by all, then an inactive member is returned")
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
            .as("When filtering by all, then an inactive member is returned")
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactlyInAnyOrder(PublicMembers.inactive());
    }

    @Test
    @DisplayName("When filtering by inactive, then an active member isn't returned")
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
            .as("When filtering by inactive, then an active member isn't returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering by inactive, then an inactive member is returned")
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
            .as("When filtering by inactive, then an inactive member is returned")
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.inactive());
    }

    @Test
    @DisplayName("When a member is inactive, then the last paid month is returned")
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
            .as("The last paid month is returned for an inactive member")
            .singleElement()
            .extracting(PublicMember::lastPaidMonth)
            .isEqualTo(Optional.of(FeeConstants.APRIL_DATE));
    }

}
