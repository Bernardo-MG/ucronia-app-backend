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

package com.bernardomg.association.person.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.association.person.test.configuration.factory.Contacts;
import com.bernardomg.association.person.usecase.service.DefaultMemberStatusService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member status service - apply renewal")
class TestMemberStatusApplyRenewal {

    @Mock
    private ContactRepository          contactRepository;

    @InjectMocks
    private DefaultMemberStatusService service;

    public TestMemberStatusApplyRenewal() {
        super();
    }

    @Test
    @DisplayName("When applying renewal to an active membership, the member is deactivated")
    void testApplyRenewal_Active() {
        // GIVEN
        given(contactRepository.findAllWithRenewalMismatch()).willReturn(List.of(Contacts.membershipActive()));

        // WHEN
        service.applyRenewal();

        // THEN
        verify(contactRepository).saveAll(List.of(Contacts.membershipInactiveNoRenew()));
    }

    @Test
    @DisplayName("When applying renewal to an inactive membership and an active membership, both are updated")
    void testApplyRenewal_ActiveAndInactive() {
        // GIVEN
        given(contactRepository.findAllWithRenewalMismatch())
            .willReturn(List.of(Contacts.membershipActive(), Contacts.alternativeMembershipInactive()));

        // WHEN
        service.applyRenewal();

        // THEN
        verify(contactRepository)
            .saveAll(List.of(Contacts.alternativeMembershipActive(), Contacts.membershipInactiveNoRenew()));
    }

    @Test
    @DisplayName("When applying renewal to an inactive membership, the member is activated")
    void testApplyRenewal_Inactive() {
        // GIVEN
        given(contactRepository.findAllWithRenewalMismatch()).willReturn(List.of(Contacts.membershipInactive()));

        // WHEN
        service.applyRenewal();

        // THEN
        verify(contactRepository).saveAll(List.of(Contacts.membershipActive()));
    }

}
