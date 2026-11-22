
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
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.MultipleActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.MultipleActiveMemberAccents;
import com.bernardomg.association.member.test.configuration.factory.MemberFilters;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - findAll")
@PositiveFeeType
@MultipleActiveMember
@MultipleFees
class ITMemberRepositoryFindAllSort {

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("When sorting in ascending order by first name, then it returns the ordered data")
    void testFindAll_FirstName_Asc() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));
        filter = MemberFilters.all();

        // WHEN
        // FIXME: names should be sorted ignoring case
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When sorting in ascending order by first name, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Members.forNumber(1), Members.forNumber(2), Members.forNumber(3), Members.forNumber(4),
                Members.forNumber(5));
    }

    @Test
    @DisplayName("When sorting in descending order by first name, then it returns the ordered data")
    void testFindAll_FirstName_Desc() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.DESC)));
        filter = MemberFilters.all();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When sorting in descending order by first name, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Members.forNumber(5), Members.forNumber(4), Members.forNumber(3), Members.forNumber(2),
                Members.forNumber(1));
    }

    @Test
    @DisplayName("When sorting in ascending order by first name with accents, then it returns the ordered data")
    @PositiveFeeType
    @MultipleActiveMemberAccents
    void testFindAll_FirstNameAccents_Asc() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));
        filter = MemberFilters.all();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When sorting in ascending order by first name with accents, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.list(Member.class))
            .extracting(member -> member.name()
                .fullName())
            .containsExactly("Name á Last name 1", "Name é Last name 2", "Name í Last name 3", "Name ó Last name 4",
                "Name ú Last name 5");
    }

    @Test
    @DisplayName("When sorting in ascending order by last name, then it returns the ordered data")
    void testFindAll_LastName_Asc() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("lastName", Sorting.Direction.ASC)));
        filter = MemberFilters.all();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When sorting in ascending order by last name, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Members.forNumber(1), Members.forNumber(2), Members.forNumber(3), Members.forNumber(4),
                Members.forNumber(5));
    }

    @Test
    @DisplayName("When sorting in descending order by last name, then it returns the ordered data")
    void testFindAll_LastName_Desc() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("lastName", Sorting.Direction.DESC)));
        filter = MemberFilters.all();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When sorting in descending order by last name, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Members.forNumber(5), Members.forNumber(4), Members.forNumber(3), Members.forNumber(2),
                Members.forNumber(1));
    }

}
