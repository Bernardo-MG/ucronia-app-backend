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

package com.bernardomg.association.auth.user.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.auth.user.adapter.inbound.jpa.model.UserMemberEntity;
import com.bernardomg.association.auth.user.adapter.inbound.jpa.repository.UserMemberSpringRepository;
import com.bernardomg.association.auth.user.domain.model.UserMember;
import com.bernardomg.association.auth.user.domain.repository.UserMemberRepository;
import com.bernardomg.association.auth.user.test.config.data.annotation.ValidUser;
import com.bernardomg.association.auth.user.test.config.data.annotation.ValidUserWithMember;
import com.bernardomg.association.auth.user.test.config.factory.UserConstants;
import com.bernardomg.association.auth.user.test.config.factory.UserMemberEntities;
import com.bernardomg.association.auth.user.test.config.factory.UserMembers;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("User member service - assign member")
class ITUserMemberRepositorySave {

    @Autowired
    private UserMemberRepository       repository;

    @Autowired
    private UserMemberSpringRepository userMemberSpringRepository;

    @Test
    @DisplayName("When the data exists, the relationship is persisted")
    @ValidUserWithMember
    void testAssignMember_Existing_PersistedData() {
        final Collection<UserMemberEntity> members;

        // WHEN
        repository.save(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        members = userMemberSpringRepository.findAll();
        Assertions.assertThat(members)
            .containsExactly(UserMemberEntities.valid());
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUserWithMember
    void testAssignMember_Existing_ReturnedData() {
        final UserMember member;

        // WHEN
        member = repository.save(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEqualTo(UserMembers.valid());
    }

    @Test
    @DisplayName("With valid data, the relationship is persisted")
    @ValidUser
    @ValidMember
    void testAssignMember_PersistedData() {
        final Collection<UserMemberEntity> members;

        // WHEN
        repository.save(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        members = userMemberSpringRepository.findAll();
        Assertions.assertThat(members)
            .containsExactly(UserMemberEntities.valid());
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUser
    @ValidMember
    void testAssignMember_ReturnedData() {
        final UserMember member;

        // WHEN
        member = repository.save(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEqualTo(UserMembers.valid());
    }

}
