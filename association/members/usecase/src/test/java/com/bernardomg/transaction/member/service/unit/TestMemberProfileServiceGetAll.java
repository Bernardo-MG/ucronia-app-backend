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

package com.bernardomg.transaction.member.service.unit;

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
import com.bernardomg.association.member.domain.filter.MemberProfileFilter;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberProfiles;
import com.bernardomg.association.member.usecase.service.DefaultMemberProfileService;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberProfileService - get all")
class TestMemberProfileServiceGetAll {

    @Mock
    private ContactMethodRepository     contactMethodRepository;

    @Mock
    private FeeTypeRepository           feeTypeRepository;

    @Mock
    private MemberProfileRepository     memberProfileRepository;

    @InjectMocks
    private DefaultMemberProfileService service;

    @Test
    @DisplayName("When there is no data, it returns nothing")
    void testGetAll_NoData() {
        final Page<MemberProfile> members;
        final Page<MemberProfile> existing;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = new MemberProfileFilter(MemberStatus.ALL, "");

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(memberProfileRepository.findAll(filter, pagination, sorting)).willReturn(existing);

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
    @DisplayName("When there is data, it returns all the members")
    void testGetAll_ReturnsData() {
        final Page<MemberProfile> members;
        final Page<MemberProfile> existing;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = new MemberProfileFilter(MemberStatus.ALL, "");

        existing = new Page<>(List.of(MemberProfiles.active()), 0, 0, 0, 0, 0, false, false, sorting);
        given(memberProfileRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        members = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("members")
            .containsExactly(MemberProfiles.active());
    }

}
