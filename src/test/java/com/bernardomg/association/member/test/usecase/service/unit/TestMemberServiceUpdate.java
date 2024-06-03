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
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.association.person.test.config.factory.PersonConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member service - update")
class TestMemberServiceUpdate {

    @Mock
    private MemberRepository     memberRepository;

    @InjectMocks
    private DefaultMemberService service;

    public TestMemberServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("When disabling a member, the change is persisted")
    void testUpdate_Inactive_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.inactive();

        given(memberRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        service.update(member);

        // THEN
        verify(memberRepository).save(Members.inactive());
    }

    @Test
    @DisplayName("When disabling a member, the change is returned")
    void testUpdate_Inactive_ReturnedData() {
        final Member member;
        final Member updated;

        // GIVEN
        member = Members.inactive();

        given(memberRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.save(Members.nameChange())).willReturn(Members.nameChange());

        // WHEN
        updated = service.update(member);

        // THEN
        Assertions.assertThat(updated)
            .as("member")
            .isEqualTo(Members.inactive());
    }

    @Test
    @DisplayName("With a not existing member, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Member           member;
        final ThrowingCallable execution;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.update(member);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("With a member having padding whitespaces in first name and last name, these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.paddedWithWhitespaces();

        given(memberRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        service.update(member);

        // THEN
        verify(memberRepository).save(Members.active());
    }

    @Test
    @DisplayName("When updating a member, the change is persisted")
    void testUpdate_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        service.update(member);

        // THEN
        verify(memberRepository).save(Members.nameChange());
    }

    @Test
    @DisplayName("When updating a member, the change is returned")
    void testUpdate_ReturnedData() {
        final Member member;
        final Member updated;

        // GIVEN
        member = Members.nameChange();

        given(memberRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.save(Members.nameChange())).willReturn(Members.nameChange());

        // WHEN
        updated = service.update(member);

        // THEN
        Assertions.assertThat(updated)
            .as("member")
            .isEqualTo(Members.nameChange());
    }

}
