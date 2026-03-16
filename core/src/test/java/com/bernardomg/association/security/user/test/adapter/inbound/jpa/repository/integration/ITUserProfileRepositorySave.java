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

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserProfileEntity;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserProfileSpringRepository;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithProfile;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("UserProfileRepository - save")
class ITUserProfileRepositorySave {

    @Autowired
    private UserProfileRepository       repository;

    @Autowired
    private UserProfileSpringRepository userProfileSpringRepository;

    @Test
    @DisplayName("When the data already exists, the relationship is persisted")
    @ValidUserWithProfile
    void testSave_Existing_PersistedData() {
        final Collection<UserProfileEntity> profiles;

        // WHEN
        repository.assignProfile(UserConstants.USERNAME, ProfileConstants.NUMBER);

        // THEN
        profiles = userProfileSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserProfileEntity profile;

            softly.assertThat(profiles)
                .as("profiles")
                .hasSize(1);

            profile = profiles.iterator()
                .next();
            softly.assertThat(profile.getUserId())
                .as("user id")
                .isNotNull();
            softly.assertThat(profile.getProfile()
                .getNumber())
                .as("profile number")
                .isEqualTo(ProfileConstants.NUMBER);
            softly.assertThat(profile.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUserWithProfile
    void testSave_Existing_ReturnedData() {
        final Profile profile;

        // WHEN
        profile = repository.assignProfile(UserConstants.USERNAME, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .isEqualTo(Profiles.valid());
    }

    @Test
    @DisplayName("When the profile is missing, nothing is returned")
    @ValidUser
    void testSave_MissingProfile_ReturnedData() {
        final Profile profile;

        // WHEN
        profile = repository.assignProfile(UserConstants.USERNAME, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .isNull();
    }

    @Test
    @DisplayName("When the user is missing, nothing is returned")
    @ValidProfile
    void testSave_MissingUser_ReturnedData() {
        final Profile profile;

        // WHEN
        profile = repository.assignProfile(UserConstants.USERNAME, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .isNull();
    }

    @Test
    @DisplayName("With valid data, the relationship is persisted")
    @ValidUser
    @ValidProfile
    void testSave_PersistedData() {
        final Collection<UserProfileEntity> profiles;

        // WHEN
        repository.assignProfile(UserConstants.USERNAME, ProfileConstants.NUMBER);

        // THEN
        profiles = userProfileSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserProfileEntity profile;

            softly.assertThat(profiles)
                .as("profiles")
                .hasSize(1);

            profile = profiles.iterator()
                .next();
            softly.assertThat(profile.getUserId())
                .as("user id")
                .isNotNull();
            softly.assertThat(profile.getProfile()
                .getNumber())
                .as("profile number")
                .isEqualTo(ProfileConstants.NUMBER);
            softly.assertThat(profile.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUser
    @ValidProfile
    void testSave_ReturnedData() {
        final Profile profile;

        // WHEN
        profile = repository.assignProfile(UserConstants.USERNAME, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .isEqualTo(Profiles.valid());
    }

}
