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

import com.bernardomg.association.auth.user.test.config.data.annotation.ValidUserWithMember;
import com.bernardomg.association.auth.user.test.util.model.UserConstants;
import com.bernardomg.auth.association.user.adapter.inbound.jpa.repository.UserMemberRepository;
import com.bernardomg.auth.association.user.usecase.service.UserMemberService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("User member service - delete member")
class ITUserMemberServiceDeleteMember {

    @Autowired
    private UserMemberService    service;

    @Autowired
    private UserMemberRepository userMemberRepository;

    public ITUserMemberServiceDeleteMember() {
        super();
    }

    @Test
    @DisplayName("With a member assigned to the user, it removes the member")
    @ValidUserWithMember
    void testDeleteMember() {

        // WHEN
        service.deleteMember(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(userMemberRepository.count())
            .as("user members")
            .isZero();
    }

}
