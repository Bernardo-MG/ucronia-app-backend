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

import com.bernardomg.association.auth.user.model.UserMember;
import com.bernardomg.association.auth.user.persistence.repository.UserMemberRepository;
import com.bernardomg.association.auth.user.service.UserMemberService;
import com.bernardomg.association.auth.user.test.util.model.UserConstants;
import com.bernardomg.association.auth.user.test.util.model.UserMembers;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.association.test.data.auth.user.annotation.ValidUser;
import com.bernardomg.association.test.data.member.annotation.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("User member service - assign member")
class ITUserMemberServiceAssignMember {

    @Autowired
    private UserMemberService    service;

    @Autowired
    private UserMemberRepository userMemberRepository;

    public ITUserMemberServiceAssignMember() {
        super();
    }

    @Test
    @DisplayName("With valid data, the relationship is persisted")
    @ValidUser
    @ValidMember
    void testAssignMember_PersistedData() {
        // WHEN
        service.assignMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(userMemberRepository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUser
    @ValidMember
    void testAssignMember_ReturnedData() {
        final UserMember member;

        // WHEN
        member = service.assignMember(UserConstants.USERNAME, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEqualTo(UserMembers.valid());
    }

}
