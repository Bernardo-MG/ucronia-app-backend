
package com.bernardomg.association.transaction.member.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.filter.PublicMemberFilter;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.member.test.configuration.factory.PublicMemberFilters;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.association.member.usecase.service.DefaultPublicMemberService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultPublicMemberService - getAll")
class TestPublicMemberServiceGetAll {

    @Mock
    private PublicMemberRepository     memberRepository;

    @InjectMocks
    private DefaultPublicMemberService service;

    @Test
    @DisplayName("When there is no data, then it returns nothing")
    void testGetAll_NoData() {
        final Page<PublicMember> members;
        final Page<PublicMember> existing;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.active();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(memberRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        members = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is data, then it returns all the members")
    void testGetAll_ReturnsData() {
        final Page<PublicMember> members;
        final Page<PublicMember> existing;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = PublicMemberFilters.active();

        existing = new Page<>(List.of(PublicMembers.active()), 0, 0, 0, 0, 0, false, false, sorting);
        given(memberRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        members = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .containsExactly(PublicMembers.active());
    }

}
