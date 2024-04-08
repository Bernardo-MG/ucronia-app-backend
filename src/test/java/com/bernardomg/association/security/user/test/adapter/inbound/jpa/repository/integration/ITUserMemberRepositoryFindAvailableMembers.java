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

package com.bernardomg.association.security.user.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
import com.bernardomg.association.security.user.test.config.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.config.data.annotation.ValidUserWithMember;
import com.bernardomg.association.security.user.test.config.factory.UserConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("UserMemberRepository - find available members")
class ITUserMemberRepositoryFindAvailableMembers {

    @Autowired
    private UserMemberRepository repository;

    @Test
    @DisplayName("When the member is not assigned, it is returned")
    @ValidUser
    @ValidMember
    void testFindAvailableMembers() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAvailableMembers(UserConstants.USERNAME, Pageable.unpaged());

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.inactive());
    }

    @Test
    @DisplayName("When the member is assigned, nothing is returned")
    @ValidUserWithMember
    void testFindAvailableMembers_Assigned() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAvailableMembers(UserConstants.USERNAME, Pageable.unpaged());

        // THEN
        Assertions.assertThat(members)
            .isEmpty();
    }

    @Test
    @DisplayName("When no data exists, nothing is returned")
    void testFindAvailableMembers_NoData() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAvailableMembers(UserConstants.USERNAME, Pageable.unpaged());

        // THEN
        Assertions.assertThat(members)
            .isEmpty();
    }

    @Test
    @DisplayName("When there are no members, nothing is returned")
    @ValidUser
    void testFindAvailableMembers_NoMember() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAvailableMembers(UserConstants.USERNAME, Pageable.unpaged());

        // THEN
        Assertions.assertThat(members)
            .isEmpty();
    }

}
