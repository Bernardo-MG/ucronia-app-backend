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
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberFeeTypeRepository;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.MemberProfiles;
import com.bernardomg.association.member.usecase.service.DefaultMemberProfileService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberProfileService - update")
class TestMemberProfileServiceUpdate {

    @Mock
    private MemberContactMethodRepository memberContactMethodRepository;

    @Mock
    private MemberFeeTypeRepository       memberFeeTypeRepository;

    @Mock
    private MemberProfileRepository       memberProfileRepository;

    @InjectMocks
    private DefaultMemberProfileService   service;

    public TestMemberProfileServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With a profile with an existing identifier, an exception is thrown")
    void testUpdate_IdentifierExists() {
        final ThrowingCallable execution;
        final MemberProfile    member;

        // GIVEN
        member = MemberProfiles.active();

        given(memberFeeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);
        given(memberProfileRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(memberProfileRepository.existsByIdentifierForAnother(MemberConstants.NUMBER, MemberConstants.IDENTIFIER))
            .willReturn(true);

        // WHEN
        execution = () -> service.update(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", MemberConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a not existing member, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final MemberProfile    member;
        final ThrowingCallable execution;

        // GIVEN
        member = MemberProfiles.nameChange();

        given(memberProfileRepository.exists(MemberConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("With a not existing member, an exception is thrown")
    void testUpdate_NotExistingFeeType_Exception() {
        final MemberProfile    member;
        final ThrowingCallable execution;

        // GIVEN
        member = MemberProfiles.nameChange();

        given(memberProfileRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(memberFeeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberFeeTypeException.class);
    }

    @Test
    @DisplayName("With a member having padding whitespaces in first and last name, these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final MemberProfile member;

        // GIVEN
        member = MemberProfiles.padded();

        given(memberProfileRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(memberFeeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(member);

        // THEN
        verify(memberProfileRepository).save(MemberProfiles.active());
    }

    @Test
    @DisplayName("When updating a member, the change is persisted")
    void testUpdate_PersistedData() {
        final MemberProfile member;

        // GIVEN
        member = MemberProfiles.nameChange();
        given(memberProfileRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(memberFeeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);
        given(memberProfileRepository.save(MemberProfiles.nameChange())).willReturn(MemberProfiles.nameChange());

        // WHEN
        service.update(member);

        // THEN
        verify(memberProfileRepository).save(MemberProfiles.nameChange());
    }

    @Test
    @DisplayName("When updating a member, the change is returned")
    void testUpdate_ReturnedData() {
        final MemberProfile member;
        final MemberProfile updated;

        // GIVEN
        member = MemberProfiles.nameChange();

        given(memberProfileRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(memberFeeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);
        given(memberProfileRepository.save(MemberProfiles.nameChange())).willReturn(MemberProfiles.nameChange());

        // WHEN
        updated = service.update(member);

        // THEN
        Assertions.assertThat(updated)
            .as("member")
            .isEqualTo(MemberProfiles.nameChange());
    }

}
