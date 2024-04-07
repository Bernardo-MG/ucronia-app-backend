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

package com.bernardomg.association.security.user.test.service.unit;

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
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
import com.bernardomg.association.security.user.test.config.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.config.factory.UserConstants;
import com.bernardomg.association.security.user.test.config.factory.Users;
import com.bernardomg.association.security.user.usecase.service.DefaultUserMemberService;
import com.bernardomg.security.authentication.user.domain.exception.MissingUserException;
import com.bernardomg.security.authentication.user.domain.repository.UserRepository;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@ExtendWith(MockitoExtension.class)
@DisplayName("User member service - assign member")
class TestUserMemberServiceAssignMember {

    @Mock
    private MemberRepository         memberRepository;

    @InjectMocks
    private DefaultUserMemberService service;

    @Mock
    private UserMemberRepository     userMemberRepository;

    @Mock
    private UserRepository           userRepository;

    @Test
    @DisplayName("When the member has already been assigned, it throws an exception")
    @ValidMember
    void testAssignMember_ExistingMember() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        given(userMemberRepository.existsByMember(MemberConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.assignMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            FieldFailure.of("member", "existing", MemberConstants.NUMBER));
    }

    @Test
    @DisplayName("When the user already has a member, it throws an exception")
    @ValidMember
    void testAssignMember_ExistingUser() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        given(userMemberRepository.exists(UserConstants.USERNAME)).willReturn(true);

        // WHEN
        execution = () -> service.assignMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            FieldFailure.of("username", "existing", UserConstants.USERNAME));
    }

    @Test
    @DisplayName("With no member, it throws an exception")
    @ValidUser
    void testAssignMember_NoMember() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // TODO: assign when the user already has a member

        // WHEN
        execution = () -> service.assignMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("With no user, it throws an exception")
    @ValidMember
    void testAssignMember_NoUser() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.assignMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingUserException.class);
    }

    @Test
    @DisplayName("With valid data, the relationship is persisted")
    void testAssignMember_PersistedData() {

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        service.assignMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        verify(userMemberRepository).save(UserConstants.USERNAME, MemberConstants.NUMBER);
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    void testAssignMember_ReturnedData() {
        final Member member;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(userMemberRepository.save(UserConstants.USERNAME, MemberConstants.NUMBER)).willReturn(Members.active());

        // WHEN
        member = service.assignMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEqualTo(Members.active());
    }

}
