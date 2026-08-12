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

package com.bernardomg.association.transaction.member.service.unit;

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

import com.bernardomg.association.fee.domain.exception.MissingFeeTypeException;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypeConstants;
import com.bernardomg.association.member.domain.exception.MissingKeyException;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.KeyRepository;
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberService - patch")
class TestMemberServicePatch {

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

    public TestMemberServicePatch() {
        super();
    }

    @Test
    @DisplayName("With a missing contact method, an exception is thrown")
    void testPatch_ContactMethodMissing() {
        final Member           member;
        final ThrowingCallable execution;

        // GIVEN
        member = Members.withEmail();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(member.feeType()
            .number())).willReturn(true);
        given(memberContactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.patch(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("With a not existing feeType, an exception is thrown")
    void testPatch_FeeTypeMissing() {
        final Member           member;
        final ThrowingCallable execution;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.patch(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingFeeTypeException.class);
    }

    @Test
    @DisplayName("With a member with an existing identifier, an exception is thrown")
    void testPatch_IdentifierExists() {
        final ThrowingCallable execution;
        final Member           member;

        // GIVEN
        member = Members.active();

        given(feeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(member));
        given(memberRepository.existsByIdentifierForAnother(MemberConstants.NUMBER, MemberConstants.IDENTIFIER))
            .willReturn(true);

        // WHEN
        execution = () -> service.patch(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", MemberConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a missing key, an exception is thrown")
    void testPatch_KeyMissing() {
        final Member           member;
        final ThrowingCallable execution;

        // GIVEN
        member = Members.withKey();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);
        given(keyRepository.exists(KeyConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.patch(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingKeyException.class);
    }

    @Test
    @DisplayName("With a not existing member, an exception is thrown")
    void testPatch_NotExisting_Exception() {
        final Member           member;
        final ThrowingCallable execution;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.patch(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("When patching the name, the change is persisted")
    void testPatch_OnlyName_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.nameChangePatch();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.patch(member);

        // THEN
        verify(memberRepository).save(Members.nameChange());
    }

    @Test
    @DisplayName("With a member having padding whitespaces in first and last name, these whitespaces are removed")
    void testPatch_Padded_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.padded();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.patch(member);

        // THEN
        verify(memberRepository).save(Members.active());
    }

    @Test
    @DisplayName("When updating a member, the change is persisted")
    void testPatch_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.patch(member);

        // THEN
        verify(memberRepository).save(Members.nameChange());
    }

    @Test
    @DisplayName("When updating a member, the change is returned")
    void testPatch_ReturnedData() {
        final Member member;
        final Member updated;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeTypeRepository.exists(FeeTypeConstants.NUMBER)).willReturn(true);
        given(memberRepository.save(Members.nameChange())).willReturn(Members.nameChange());

        // WHEN
        updated = service.patch(member);

        // THEN
        Assertions.assertThat(updated)
            .as("member")
            .isEqualTo(Members.nameChange());
    }

}
