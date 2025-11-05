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

package com.bernardomg.association.member.test.usecase.service.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.contact.domain.repository.ContactMethodRepository;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberFilters;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Property;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member service - get all")
class TestMemberServiceGetAll {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private MemberRepository        memberRepository;

    @InjectMocks
    private DefaultMemberService    service;

    public TestMemberServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When there is no data, it returns nothing")
    void testGetAll_NoData() {
        final Page<Member> members;
        final Page<Member> existing;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = MemberFilters.empty();

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
    @DisplayName("When getting all the members, it returns all the members")
    void testGetAll_ReturnsData() {
        final Page<Member> members;
        final Page<Member> existing;
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = MemberFilters.empty();

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

    @Test
    @DisplayName("When sorting ascending by first name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Asc_FirstName() {
        final Pagination   pagination;
        final Sorting      sorting;
        final MemberFilter filter;
        final Page<Member> existing;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.asc("firstName");
        filter = MemberFilters.empty();

        existing = new Page<>(List.of(Members.active()), 0, 0, 0, 0, 0, false, false, sorting);
        given(memberRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        service.getAll(filter, pagination, sorting);

        // THEN
        verify(memberRepository).findAll(eq(filter), eq(pagination), assertArg(s -> assertThat(s).as("sort")
            .extracting(Sorting::properties)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Property.asc("firstName"))));
    }

    @Test
    @DisplayName("When sorting descending by first name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Desc_FirstName() {
        final Pagination   pagination;
        final Sorting      sorting;
        final Page<Member> existing;
        final MemberFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.desc("firstName");
        filter = MemberFilters.empty();

        existing = new Page<>(List.of(Members.active()), 0, 0, 0, 0, 0, false, false, sorting);
        given(memberRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        service.getAll(filter, pagination, sorting);

        // THEN
        verify(memberRepository).findAll(eq(filter), eq(pagination), assertArg(s -> assertThat(s).as("sort")
            .extracting(Sorting::properties)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Property.desc("firstName"))));
    }

}
