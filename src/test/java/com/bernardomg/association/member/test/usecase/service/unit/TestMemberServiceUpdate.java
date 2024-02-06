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

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.exception.MissingMemberIdException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberChange;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.factory.MemberChanges;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;

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
    @DisplayName("With a not existing entity, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final MemberChange     memberRequest;
        final ThrowingCallable execution;

        // GIVEN
        memberRequest = MemberChanges.nameChange();

        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberIdException.class);
    }

    @Test
    @DisplayName("With a member having padding whitespaces in name and surname, these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final MemberChange memberRequest;

        // GIVEN
        memberRequest = MemberChanges.paddedWithWhitespaces();

        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        verify(memberRepository).save(Members.inactive());
    }

    @Test
    @DisplayName("When updating a member, the change is persisted")
    void testUpdate_PersistedData() {
        final MemberChange memberRequest;

        // GIVEN
        memberRequest = MemberChanges.nameChange();

        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        verify(memberRepository).save(Members.nameChange());
    }

    @Test
    @DisplayName("When updating an active member, the change is returned")
    void testUpdate_ReturnedData() {
        final MemberChange memberRequest;
        final Member       member;

        // GIVEN
        memberRequest = MemberChanges.nameChange();

        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(memberRepository.save(Members.nameChange())).willReturn(Members.nameChange());

        // WHEN
        member = service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        Assertions.assertThat(member)
            .as("member")
            .isEqualTo(Members.nameChange());
    }

}
