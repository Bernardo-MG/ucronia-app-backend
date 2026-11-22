
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.filter.PublicMemberFilter;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.MultipleActiveMember;
import com.bernardomg.association.member.test.configuration.factory.PublicMemberFilters;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublicMemberRepository - findAll")
@PositiveFeeType
@MultipleActiveMember
@MultipleFees
class ITPublicMemberRepositoryFindAllSort {

    @Autowired
    private PublicMemberRepository repository;

    @Test
    @DisplayName("When sorting in ascending order by first name, then it returns the ordered data")
    void testFindAll_FirstName_Asc() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));
        filter = PublicMemberFilters.active();

        // WHEN
        // FIXME: names should be sorted ignoring case
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When sorting in ascending order by first name, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("joined", "totalMonths")
            .containsExactly(PublicMembers.forNumber(1), PublicMembers.forNumber(2), PublicMembers.forNumber(3),
                PublicMembers.forNumber(4), PublicMembers.forNumberNotPaid(5));
    }

    @Test
    @DisplayName("When sorting in descending order by first name, then it returns the ordered data")
    void testFindAll_FirstName_Desc() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.DESC)));
        filter = PublicMemberFilters.active();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When sorting in descending order by first name, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("joined", "totalMonths")
            .containsExactly(PublicMembers.forNumberNotPaid(5), PublicMembers.forNumber(4), PublicMembers.forNumber(3),
                PublicMembers.forNumber(2), PublicMembers.forNumber(1));
    }

    @Test
    @DisplayName("When sorting in ascending order by last name, then it returns the ordered data")
    void testFindAll_LastName_Asc() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("lastName", Sorting.Direction.ASC)));
        filter = PublicMemberFilters.active();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When sorting in ascending order by last name, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("joined", "totalMonths")
            .containsExactly(PublicMembers.forNumber(1), PublicMembers.forNumber(2), PublicMembers.forNumber(3),
                PublicMembers.forNumber(4), PublicMembers.forNumberNotPaid(5));
    }

    @Test
    @DisplayName("When sorting in descending order by last name, then it returns the ordered data")
    void testFindAll_LastName_Desc() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("lastName", Sorting.Direction.DESC)));
        filter = PublicMemberFilters.active();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When sorting in descending order by last name, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("joined", "totalMonths")
            .containsExactly(PublicMembers.forNumberNotPaid(5), PublicMembers.forNumber(4), PublicMembers.forNumber(3),
                PublicMembers.forNumber(2), PublicMembers.forNumber(1));
    }

}
