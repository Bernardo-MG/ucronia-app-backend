/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.member.test.config.factory.ReducedMembers;
import com.bernardomg.association.member.usecase.service.DefaultReducedMemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Public member service - get all")
class TestPublicMemberServiceGetAll {

    @Mock
    private MemberRepository            memberRepository;

    @Captor
    private ArgumentCaptor<Pageable>    pageableCaptor;

    @InjectMocks
    private DefaultReducedMemberService service;

    public TestPublicMemberServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When filtering with the default filter, and there is no data, it returns nothing")
    void testGetAll_NoData() {
        final Iterable<PublicMember> members;
        final Pageable               pageable;
        final Page<Member>           readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of());
        given(memberRepository.findActive(ArgumentMatchers.any())).willReturn(readMembers);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.getAll(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When filtering with the default filter it returns all the members")
    void testGetAll_ReturnsData() {
        final Iterable<PublicMember> members;
        final Pageable               pageable;
        final Page<Member>           readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(memberRepository.findActive(ArgumentMatchers.any())).willReturn(readMembers);

        pageable = Pageable.unpaged();

        // WHEN
        members = service.getAll(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(ReducedMembers.active());
    }

    @Test
    @DisplayName("When sorting ascending by full name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Asc_FullName() {
        final Pageable     pageable;
        final Page<Member> readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(memberRepository.findActive(pageableCaptor.capture())).willReturn(readMembers);

        pageable = PageRequest.of(0, 1, Sort.by("fullName"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.asc("person.firstName"), Order.asc("person.lastName"));
    }

    @Test
    @DisplayName("When sorting ascending by full name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Asc_Number() {
        final Pageable     pageable;
        final Page<Member> readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(memberRepository.findActive(pageableCaptor.capture())).willReturn(readMembers);

        pageable = PageRequest.of(0, 1, Sort.by("number"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.asc("person.number"));
    }

    @Test
    @DisplayName("When sorting descending by full name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Desc_FullName() {
        final Pageable     pageable;
        final Page<Member> readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(memberRepository.findActive(pageableCaptor.capture())).willReturn(readMembers);

        pageable = PageRequest.of(0, 1, Sort.by(Direction.DESC, "fullName"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.desc("person.firstName"), Order.desc("person.lastName"));
    }

    @Test
    @DisplayName("When sorting descending by full name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Desc_Number() {
        final Pageable     pageable;
        final Page<Member> readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(memberRepository.findActive(pageableCaptor.capture())).willReturn(readMembers);

        pageable = PageRequest.of(0, 1, Sort.by(Direction.DESC, "number"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.desc("person.number"));
    }

    @Test
    @DisplayName("When sorting ascending by full name, and not applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Unpaged_Asc_FullName() {
        final Pageable     pageable;
        final Page<Member> readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(memberRepository.findActive(pageableCaptor.capture())).willReturn(readMembers);

        pageable = Pageable.unpaged(Sort.by("fullName"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.asc("person.firstName"), Order.asc("person.lastName"));
    }

    @Test
    @DisplayName("When sorting ascending by full name, and not applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Unpaged_Asc_Number() {
        final Pageable     pageable;
        final Page<Member> readMembers;

        // GIVEN
        readMembers = new PageImpl<>(List.of(Members.active()));
        given(memberRepository.findActive(pageableCaptor.capture())).willReturn(readMembers);

        pageable = Pageable.unpaged(Sort.by("number"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.asc("person.number"));
    }

}
