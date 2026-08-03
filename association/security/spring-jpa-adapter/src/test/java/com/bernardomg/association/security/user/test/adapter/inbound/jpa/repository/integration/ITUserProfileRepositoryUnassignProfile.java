/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.AssociationUserSpringRepository;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserAssignedProfileSpringRepository;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserProfileSpringRepository;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.TestApplication;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithProfile;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("UserProfileRepository - unassign profile")
class ITUserProfileRepositoryUnassignProfile {

    @Autowired
    private UserProfileSpringRepository         profileSpringRepository;

    @Autowired
    private UserProfileRepository               repository;

    @Autowired
    private UserAssignedProfileSpringRepository userProfileSpringRepository;

    @Autowired
    private AssociationUserSpringRepository                userSpringRepository;

    @Test
    @DisplayName("With a member assigned to the user, it removes the member")
    @ValidUserWithProfile
    void testUnassignProfile() {

        // WHEN
        repository.unassignProfile(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(userProfileSpringRepository.count())
            .as("user members")
            .isZero();
    }

    @Test
    @DisplayName("With no member assigned to the user, it does nothing")
    void testUnassignProfile_NoData() {

        // WHEN
        repository.unassignProfile(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(userProfileSpringRepository.count())
            .as("user members")
            .isZero();
    }

    @Test
    @DisplayName("With a member assigned to the user, it doesn't remove the profile")
    @ValidUserWithProfile
    void testUnassignProfile_ProfileNotRemoved() {

        // WHEN
        repository.unassignProfile(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profileSpringRepository.count())
            .as("profiles count")
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a member assigned to the user, it doesn't remove the user")
    @ValidUserWithProfile
    void testUnassignProfile_UserNotRemoved() {

        // WHEN
        repository.unassignProfile(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(userSpringRepository.count())
            .as("users count")
            .isEqualTo(1);
    }

}
