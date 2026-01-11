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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberProfiles;
import com.bernardomg.association.member.usecase.service.DefaultMemberStatusService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member status service - apply renewal")
class TestMemberStatusServiceApplyRenewal {

    @Mock
    private MemberProfileRepository    memberProfileRepository;

    @InjectMocks
    private DefaultMemberStatusService service;

    public TestMemberStatusServiceApplyRenewal() {
        super();
    }

    @Test
    @DisplayName("When applying renewal to an active membership, the member is deactivated")
    void testApplyRenewal_Active() {
        // GIVEN
        given(memberProfileRepository.findAllWithRenewalMismatch()).willReturn(List.of(MemberProfiles.active()));

        // WHEN
        service.applyRenewal();

        // THEN
        verify(memberProfileRepository).saveAll(List.of(MemberProfiles.inactiveNoRenew()));
    }

    @Test
    @DisplayName("When applying renewal to an inactive membership and an active membership, both are updated")
    void testApplyRenewal_ActiveAndInactive() {
        // GIVEN
        given(memberProfileRepository.findAllWithRenewalMismatch())
            .willReturn(List.of(MemberProfiles.active(), MemberProfiles.alternativeInactive()));

        // WHEN
        service.applyRenewal();

        // THEN
        verify(memberProfileRepository)
            .saveAll(List.of(MemberProfiles.alternativeActive(), MemberProfiles.inactiveNoRenew()));
    }

    @Test
    @DisplayName("When applying renewal to an inactive membership, the member is activated")
    void testApplyRenewal_Inactive() {
        // GIVEN
        given(memberProfileRepository.findAllWithRenewalMismatch()).willReturn(List.of(MemberProfiles.inactive()));

        // WHEN
        service.applyRenewal();

        // THEN
        verify(memberProfileRepository).saveAll(List.of(MemberProfiles.active()));
    }

}
