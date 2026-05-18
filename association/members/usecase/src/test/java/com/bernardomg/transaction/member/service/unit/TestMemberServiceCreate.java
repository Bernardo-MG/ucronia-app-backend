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
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;
import com.bernardomg.association.member.domain.repository.MemberFeeTypeRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberService - create")
class TestMemberServiceCreate {

    @Mock
    private MemberContactMethodRepository memberContactMethodRepository;

    @Mock
    private MemberFeeTypeRepository       memberFeeTypeRepository;

    @Mock
    private MemberRepository              memberRepository;

    @InjectMocks
    private DefaultMemberService          service;

    public TestMemberServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a member with an existing identifier, an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable execution;
        final Member           member;

        // GIVEN
        member = Members.active();

        given(memberFeeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);
        given(memberRepository.existsByIdentifier(MemberConstants.IDENTIFIER)).willReturn(true);

        // WHEN
        execution = () -> service.create(member);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "identifier", MemberConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("With a member having padding whitespaces in first and last name, these whitespaces are removed and the profile is persisted")
    void testCreate_Padded_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.padded();

        given(memberFeeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);

        // WHEN
        service.create(member);

        // THEN
        verify(memberRepository).save(Members.active());
    }

    @Test
    @DisplayName("With a valid member, the profile is persisted")
    void testCreate_PersistedData() {
        final Member member;

        // GIVEN
        member = Members.active();

        given(memberFeeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);

        // WHEN
        service.create(member);

        // THEN
        verify(memberRepository).save(Members.active());
    }

    @Test
    @DisplayName("With a valid member, the created profile is returned")
    void testCreate_ReturnedData() {
        final Member member;
        final Member created;

        // GIVEN
        member = Members.active();

        given(memberRepository.save(member)).willReturn(Members.active());
        given(memberFeeTypeRepository.exists(FeeConstants.FEE_TYPE_NUMBER)).willReturn(true);

        // WHEN
        created = service.create(member);

        // THEN
        Assertions.assertThat(created)
            .as("profile")
            .isEqualTo(Members.active());
    }

}
