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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.auth.user.model.UserMember;
import com.bernardomg.association.auth.user.service.UserMemberService;
import com.bernardomg.association.auth.user.test.data.annotation.ValidUserWithMember;
import com.bernardomg.association.auth.user.test.util.model.UserConstants;
import com.bernardomg.association.auth.user.test.util.model.UserMembers;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("User member service - get member")
class ITUserMemberServiceGetMember {

    @Autowired
    private UserMemberService service;

    public ITUserMemberServiceGetMember() {
        super();
    }

    @Test
    @DisplayName("With a member assigned to the user, it returns the user")
    @ValidUserWithMember
    void testGetMember() {
        final Optional<UserMember> member;

        // WHEN
        member = service.getMember(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(member)
            .contains(UserMembers.valid());
    }

}
