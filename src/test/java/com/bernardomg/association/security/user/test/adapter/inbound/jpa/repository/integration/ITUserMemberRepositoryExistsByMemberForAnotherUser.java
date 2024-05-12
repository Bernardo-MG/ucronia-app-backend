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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.person.test.config.factory.PersonConstants;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.test.config.data.annotation.AlternativeUserWithMember;
import com.bernardomg.association.security.user.test.config.data.annotation.ValidUserWithMember;
import com.bernardomg.association.security.user.test.config.factory.UserConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("UserMemberRepository - exists by member for another user")
class ITUserMemberRepositoryExistsByMemberForAnotherUser {

    @Autowired
    private UserPersonRepository repository;

    @Test
    @DisplayName("When the member is assigned it exists")
    @AlternativeUserWithMember
    void testExistsByPersonForAnotherUser() {
        final boolean exists;

        // WHEN
        exists = repository.existsByPersonForAnotherUser(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .isTrue();
    }

    @Test
    @DisplayName("When the member is assigned to the user, it doesn't exist")
    @ValidUserWithMember
    void testExistsByPersonForAnotherUser_AssignedToUser() {
        final boolean exists;

        // WHEN
        exists = repository.existsByPersonForAnotherUser(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .isFalse();
    }

    @Test
    @DisplayName("When no data exists it doesn't exist")
    void testExistsByPersonForAnotherUser_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByPersonForAnotherUser(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .isFalse();
    }

}
