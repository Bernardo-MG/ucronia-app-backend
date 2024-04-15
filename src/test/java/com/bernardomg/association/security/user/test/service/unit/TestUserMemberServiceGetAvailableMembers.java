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

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
import com.bernardomg.association.security.user.usecase.service.DefaultUserMemberService;
import com.bernardomg.security.authentication.user.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("User member service - get member")
class TestUserMemberServiceGetAvailableMembers {

    @Mock
    private MemberRepository         memberRepository;

    @InjectMocks
    private DefaultUserMemberService service;

    @Mock
    private UserMemberRepository     userMemberRepository;

    @Mock
    private UserRepository           userRepository;

    @Test
    @DisplayName("When there are not assigned members, these are returned")
    void testGetMember() {
        final Collection<Member> members;
        final Pageable           page;

        // GIVEN
        page = Pageable.unpaged();

        given(userMemberRepository.findAllNotAssigned(page)).willReturn(List.of(Members.active()));

        // WHEN
        members = service.getAvailableMembers(page);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.active());
    }

    @Test
    @DisplayName("When there are no not assigned members, nothing is returned")
    void testGetMember_NoMember() {
        final Collection<Member> members;
        final Pageable           page;

        // GIVEN
        page = Pageable.unpaged();

        given(userMemberRepository.findAllNotAssigned(page)).willReturn(List.of());

        // WHEN
        members = service.getAvailableMembers(page);

        // THEN
        Assertions.assertThat(members)
            .isEmpty();
    }

}
