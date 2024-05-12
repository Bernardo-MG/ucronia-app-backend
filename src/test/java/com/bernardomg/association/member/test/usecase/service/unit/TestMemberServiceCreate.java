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

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.config.factory.PersonConstants;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member service - create")
class TestMemberServiceCreate {

    @Mock
    private MemberRepository     memberRepository;

    @InjectMocks
    private DefaultMemberService service;

    public TestMemberServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a member with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final Member           member;

        // GIVEN
        member = Members.emptyName();

        // WHEN
        execution = () -> service.create(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("name", "empty", PersonName.builder()
            .withFirstName(" ")
            .withLastName(PersonConstants.SURNAME)
            .build()));
    }

    @Test
    @DisplayName("With a member with no name, an exception is thrown")
    void testCreate_NoName() {
        final ThrowingCallable execution;
        final Member           member;

        // GIVEN
        member = Members.missingName();

        // WHEN
        execution = () -> service.create(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("name", "empty", PersonName.builder()
            .withLastName(PersonConstants.SURNAME)
            .build()));
    }

    @Test
    @DisplayName("With a member with no surname, the member is persisted")
    void testCreate_NoSurname_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.missingSurname();

        given(memberRepository.findNextNumber()).willReturn(PersonConstants.NUMBER);

        // WHEN
        service.create(member);

        // THEN
        verify(memberRepository).save(Members.missingSurname());
    }

    @Test
    @DisplayName("With a member having padding whitespaces in name and surname, these whitespaces are removed and the member is persisted")
    void testCreate_Padded_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.paddedWithWhitespaces();

        given(memberRepository.findNextNumber()).willReturn(PersonConstants.NUMBER);

        // WHEN
        service.create(member);

        // THEN
        verify(memberRepository).save(Members.inactive());
    }

    @Test
    @DisplayName("With a valid member, the member is persisted")
    void testCreate_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.active();

        given(memberRepository.findNextNumber()).willReturn(PersonConstants.NUMBER);

        // WHEN
        service.create(member);

        // THEN
        verify(memberRepository).save(Members.inactive());
    }

    @Test
    @DisplayName("With a valid member, the created member is returned")
    void testCreate_ReturnedData() {
        final Member member;
        final Member created;

        // GIVEN
        member = Members.active();

        given(memberRepository.save(Members.inactive())).willReturn(Members.inactive());
        given(memberRepository.findNextNumber()).willReturn(PersonConstants.NUMBER);

        // WHEN
        created = service.create(member);

        // THEN
        Assertions.assertThat(created)
            .as("member")
            .isEqualTo(Members.inactive());
    }

}
