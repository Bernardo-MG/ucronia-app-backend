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

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypeConstants;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.exception.MissingMemberFeeTypeException;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberFeeTypeRepository;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberProfiles;
import com.bernardomg.association.member.usecase.service.DefaultMemberProfileService;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberProfileService - patch")
class TestMemberProfileServicePatch {

    @Mock
    private ContactMethodRepository     contactMethodRepository;

    @Mock
    private MemberFeeTypeRepository     memberFeeTypeRepository;

    @Mock
    private MemberProfileRepository     memberProfileRepository;

    @InjectMocks
    private DefaultMemberProfileService service;

    public TestMemberProfileServicePatch() {
        super();
    }

    @Test
    @DisplayName("With a member with an existing identifier, an exception is thrown")
    void testPatch_IdentifierExists() {
        final ThrowingCallable execution;
        final MemberProfile    member;

        // GIVEN
        member = MemberProfiles.active();

        given(memberFeeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);
        given(memberProfileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(member));
        given(
            memberProfileRepository.existsByIdentifierForAnother(ProfileConstants.NUMBER, ProfileConstants.IDENTIFIER))
                .willReturn(true);

        // WHEN
        execution = () -> service.patch(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", ProfileConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a not existing member, an exception is thrown")
    void testPatch_NotExisting_Exception() {
        final MemberProfile    member;
        final ThrowingCallable execution;

        // GIVEN
        member = MemberProfiles.nameChange();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.patch(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("With a not existing feeType, an exception is thrown")
    void testPatch_NotExistingFeeType_Exception() {
        final MemberProfile    member;
        final ThrowingCallable execution;

        // GIVEN
        member = MemberProfiles.nameChange();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER))
            .willReturn(Optional.of(MemberProfiles.active()));
        given(memberFeeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.patch(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberFeeTypeException.class);
    }

    @Test
    @DisplayName("When patching the name, the change is persisted")
    void testPatch_OnlyName_PersistedData() {
        final MemberProfile member;

        // GIVEN
        member = MemberProfiles.nameChangePatch();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER))
            .willReturn(Optional.of(MemberProfiles.active()));
        given(memberFeeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.patch(member);

        // THEN
        verify(memberProfileRepository).save(MemberProfiles.nameChange());
    }

    @Test
    @DisplayName("With a member having padding whitespaces in first and last name, these whitespaces are removed")
    void testPatch_Padded_PersistedData() {
        final MemberProfile member;

        // GIVEN
        member = MemberProfiles.padded();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER))
            .willReturn(Optional.of(MemberProfiles.active()));
        given(memberFeeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.patch(member);

        // THEN
        verify(memberProfileRepository).save(MemberProfiles.active());
    }

    @Test
    @DisplayName("When updating a member, the change is persisted")
    void testPatch_PersistedData() {
        final MemberProfile member;

        // GIVEN
        member = MemberProfiles.nameChange();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER))
            .willReturn(Optional.of(MemberProfiles.active()));
        given(memberFeeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.patch(member);

        // THEN
        verify(memberProfileRepository).save(MemberProfiles.nameChange());
    }

    @Test
    @DisplayName("When updating a member, the change is returned")
    void testPatch_ReturnedData() {
        final MemberProfile member;
        final MemberProfile updated;

        // GIVEN
        member = MemberProfiles.nameChange();

        given(memberProfileRepository.findOne(ProfileConstants.NUMBER))
            .willReturn(Optional.of(MemberProfiles.active()));
        given(memberFeeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);
        given(memberProfileRepository.save(MemberProfiles.nameChange())).willReturn(MemberProfiles.nameChange());

        // WHEN
        updated = service.patch(member);

        // THEN
        Assertions.assertThat(updated)
            .as("member")
            .isEqualTo(MemberProfiles.nameChange());
    }

}
