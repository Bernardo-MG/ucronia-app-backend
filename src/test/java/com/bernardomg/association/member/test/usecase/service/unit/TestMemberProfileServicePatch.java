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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.association.member.usecase.service.DefaultMemberProfileService;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberProfileService - patch")
class TestMemberProfileServicePatch {

    @Mock
    private MemberProfileRepository     memberProfileRepository;

    @InjectMocks
    private DefaultMemberProfileService service;

    public TestMemberProfileServicePatch() {
        super();
    }

    @Test
    @DisplayName("With a not existing guest, an exception is thrown")
    void testPatch_NotExisting_Exception() {
        final MemberProfile    guest;
        final ThrowingCallable execution;

        // GIVEN
        guest = MemberContacts.nameChange();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.patch(guest);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("When patching the name, the change is persisted")
    void testPatch_OnlyName_PersistedData() {
        final MemberProfile guest;

        // GIVEN
        guest = MemberContacts.nameChangePatch();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER))
            .willReturn(Optional.of(MemberContacts.active()));

        // WHEN
        service.patch(guest);

        // THEN
        verify(memberProfileRepository).save(MemberContacts.nameChange());
    }

    @Test
    @DisplayName("With a guest having padding whitespaces in first and last name, these whitespaces are removed")
    void testPatch_Padded_PersistedData() {
        final MemberProfile guest;

        // GIVEN
        guest = MemberContacts.padded();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER))
            .willReturn(Optional.of(MemberContacts.active()));

        // WHEN
        service.patch(guest);

        // THEN
        verify(memberProfileRepository).save(MemberContacts.active());
    }

    @Test
    @DisplayName("When updating a guest, the change is persisted")
    void testPatch_PersistedData() {
        final MemberProfile guest;

        // GIVEN
        guest = MemberContacts.nameChange();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER))
            .willReturn(Optional.of(MemberContacts.active()));

        // WHEN
        service.patch(guest);

        // THEN
        verify(memberProfileRepository).save(MemberContacts.nameChange());
    }

    @Test
    @DisplayName("When updating a guest, the change is returned")
    void testPatch_ReturnedData() {
        final MemberProfile guest;
        final MemberProfile updated;

        // GIVEN
        guest = MemberContacts.nameChange();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER))
            .willReturn(Optional.of(MemberContacts.active()));
        given(memberProfileRepository.save(MemberContacts.nameChange())).willReturn(MemberContacts.nameChange());

        // WHEN
        updated = service.patch(guest);

        // THEN
        Assertions.assertThat(updated)
            .as("guest")
            .isEqualTo(MemberContacts.nameChange());
    }

}
