
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
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
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublicMemberRepository - findAll")
@PositiveFeeType
@MultipleActiveMember
class ITPublicMemberRepositoryFindAllPaginated extends AbstractPaginationIT<PublicMember> {

    @Autowired
    private PublicMemberRepository repository;

    public ITPublicMemberRepositoryFindAllPaginated() {
        super(5);
    }

    @Override
    protected final Page<PublicMember> read(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(PublicMemberFilters.active(), pagination, sorting);
    }

    @Test
    @DisplayName("When requesting pagination for the first page, then it returns the first page")
    void testFindAll_Page1() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.active();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When requesting pagination for the first page, then it returns the first page")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.forNumberNotPaid(1));
    }

    @Test
    @DisplayName("When requesting pagination for the second page, then it returns the second page")
    void testFindAll_Page2() {
        final Page<PublicMember> members;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(2, 1);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.active();

        // WHEN
        members = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .as("When requesting pagination for the second page, then it returns the second page")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(PublicMembers.forNumberNotPaid(2));
    }

}
