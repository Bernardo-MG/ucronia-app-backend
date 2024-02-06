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

package com.bernardomg.association.auth.user.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.auth.user.domain.model.UserMember;
import com.bernardomg.association.auth.user.domain.repository.UserMemberRepository;
import com.bernardomg.association.auth.user.test.config.factory.UserConstants;
import com.bernardomg.association.auth.user.test.config.factory.UserMembers;
import com.bernardomg.association.auth.user.test.config.factory.Users;
import com.bernardomg.association.auth.user.usecase.service.DefaultUserMemberService;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.security.authentication.user.domain.exception.MissingUserUsernameException;
import com.bernardomg.security.authentication.user.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("User member service - get member")
class TestUserMemberServiceGetMember {

    @Mock
    private MemberRepository         memberRepository;

    @InjectMocks
    private DefaultUserMemberService service;

    @Mock
    private UserMemberRepository     userMemberRepository;

    @Mock
    private UserRepository           userRepository;

    @Test
    @DisplayName("With a member assigned to the user, it returns the user")
    void testGetMember() {
        final Optional<UserMember> member;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(userMemberRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.of(UserMembers.valid()));

        // WHEN
        member = service.getMember(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(member)
            .contains(UserMembers.valid());
    }

    @Test
    @DisplayName("With no member, it throws an exception")
    void testGetMember_NoMember() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getMember(UserConstants.USERNAME);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingUserUsernameException.class);
    }

}
