
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

import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberFilters;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberService - getAll")
class TestMemberServiceGetAll {

    @Mock
    private FeeTypeRepository             feeTypeRepository;

    @Mock
    private KeyRepository                 keyRepository;

    @Mock
    private MemberContactMethodRepository memberContactMethodRepository;

    @Mock
    private MemberRepository              memberRepository;

    @InjectMocks
    private DefaultMemberService          service;

    @Test
    @DisplayName("When there is no data, then it returns nothing")
    void testGetAll_NoData() {
        final Page<Member> members;
        final Page<Member> existing;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = MemberFilters.all();

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
        final Page<Member> members;
        final Page<Member> existing;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = MemberFilters.all();

        existing = new Page<>(List.of(Members.active()), 0, 0, 0, 0, 0, false, false, sorting);
        given(memberRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        members = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .containsExactly(Members.active());
    }

}
