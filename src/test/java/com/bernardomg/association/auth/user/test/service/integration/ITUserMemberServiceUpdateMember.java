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

package com.bernardomg.association.auth.user.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.auth.user.adapter.inbound.jpa.repository.UserMemberSpringRepository;
import com.bernardomg.association.auth.user.domain.model.UserMember;
import com.bernardomg.association.auth.user.test.config.data.annotation.ValidUser;
import com.bernardomg.association.auth.user.test.config.data.annotation.ValidUserWithMember;
import com.bernardomg.association.auth.user.test.config.factory.UserConstants;
import com.bernardomg.association.auth.user.test.config.factory.UserMembers;
import com.bernardomg.association.auth.user.usecase.service.UserMemberService;
import com.bernardomg.association.member.test.config.data.annotation.AlternativeMember;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("User member service - update member")
class ITUserMemberServiceUpdateMember {

    @Autowired
    private UserMemberService          service;

    @Autowired
    private UserMemberSpringRepository userMemberRepository;

    public ITUserMemberServiceUpdateMember() {
        super();
    }

    @Test
    @DisplayName("With no existing relationship, the relationship is persisted")
    @ValidUser
    @ValidMember
    void testUpdateMember_NoRelationship_PersistedData() {
        // WHEN
        service.updateMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(userMemberRepository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With no existing relationship, the created relationship is returned")
    @ValidUser
    @ValidMember
    void testUpdateMember_NoRelationship_ReturnedData() {
        final UserMember member;

        // WHEN
        member = service.updateMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEqualTo(UserMembers.valid());
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUserWithMember
    @AlternativeMember
    void testUpdateMember_PersistedData() {
        // WHEN
        service.updateMember(UserConstants.USERNAME, MemberConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(userMemberRepository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUserWithMember
    @AlternativeMember
    void testUpdateMember_ReturnedData() {
        final UserMember member;

        // WHEN
        member = service.updateMember(UserConstants.USERNAME, MemberConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEqualTo(UserMembers.alternative());
    }

}
