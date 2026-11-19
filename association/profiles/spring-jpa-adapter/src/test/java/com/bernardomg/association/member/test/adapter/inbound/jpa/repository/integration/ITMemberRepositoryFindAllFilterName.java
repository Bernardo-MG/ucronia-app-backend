
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithEmail;
import com.bernardomg.association.member.test.configuration.factory.MemberFilters;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - findAll")
class ITMemberRepositoryFindAllFilterName {

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("When there is an active member, then it is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindAll() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = MemberFilters.all();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When there is an active member, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Members.active());
    }

    @Test
    @DisplayName("When there is no member and filtering by all, then nothing is returned")
    void testFindAll_NoData() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = MemberFilters.all();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When there is no member and filtering by all, then nothing is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a member with a contact method, then it is returned")
    @PositiveFeeType
    @ActiveMemberWithEmail
    void testFindAll_WithContactMethod() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = MemberFilters.active();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When there is a member with a contact method, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Members.withEmail());
    }

    @Test
    @DisplayName("When there is a profile with no member role and filtering by all, then it is returned")
    @ValidProfile
    void testFindAll_WithoutMembership() {
        final Page<Member> members;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = MemberFilters.all();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When there is a profile with no member role and filtering by all, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
