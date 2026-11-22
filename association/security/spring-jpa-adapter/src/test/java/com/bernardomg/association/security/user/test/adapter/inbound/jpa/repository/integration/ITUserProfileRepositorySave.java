
package com.bernardomg.association.security.user.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserAssignedProfileEntity;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserAssignedProfileSpringRepository;
import com.bernardomg.association.security.user.domain.model.UserProfile;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.TestApplication;
import com.bernardomg.association.security.user.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithProfile;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfileConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfiles;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("UserProfileRepository - assignProfile")
class ITUserProfileRepositorySave {

    @Autowired
    private UserProfileRepository               repository;

    @Autowired
    private UserAssignedProfileSpringRepository userProfileSpringRepository;

    @Test
    @DisplayName("When the data already exists, then the relationship is persisted")
    @ValidUserWithProfile
    @AlternativeProfile
    void testAssignProfile_Existing_PersistedData() {
        final Collection<UserAssignedProfileEntity> profiles;

        // WHEN
        repository.assignProfile(UserConstants.USERNAME, UserProfileConstants.ALTERNATIVE_NUMBER);

        // THEN
        profiles = userProfileSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserAssignedProfileEntity profile;

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
                .isEqualTo(UserProfileConstants.ALTERNATIVE_NUMBER);
            softly.assertThat(profile.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("When there is valid data, then the created relationship is returned")
    @ValidUserWithProfile
    @AlternativeProfile
    void testAssignProfile_Existing_ReturnedData() {
        final UserProfile profile;

        // WHEN
        profile = repository.assignProfile(UserConstants.USERNAME, UserProfileConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("When there is valid data, then the created relationship is returned")
            .isEqualTo(UserProfiles.alternativeProfile());
    }

    @Test
    @DisplayName("When the profile is missing, then nothing is persisted")
    @ValidUser
    void testAssignProfile_MissingProfile_PersistedData() {
        final Collection<UserAssignedProfileEntity> profiles;

        // WHEN
        repository.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        profiles = userProfileSpringRepository.findAll();
        Assertions.assertThat(profiles)
            .as("When the profile is missing, then nothing is persisted")
            .isEmpty();
    }

    @Test
    @DisplayName("When the profile is missing, then nothing is returned")
    @ValidUser
    void testAssignProfile_MissingProfile_ReturnedData() {
        final UserProfile profile;

        // WHEN
        profile = repository.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("When the profile is missing, then nothing is returned")
            .isNull();
    }

    @Test
    @DisplayName("When the user is missing, then nothing is persisted")
    @ValidProfile
    void testAssignProfile_MissingUser_PersistedData() {
        final Collection<UserAssignedProfileEntity> profiles;

        // WHEN
        repository.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        profiles = userProfileSpringRepository.findAll();
        Assertions.assertThat(profiles)
            .as("When the user is missing, then nothing is persisted")
            .isEmpty();
    }

    @Test
    @DisplayName("When the user is missing, then nothing is returned")
    @ValidProfile
    void testAssignProfile_MissingUser_ReturnedData() {
        final UserProfile profile;

        // WHEN
        profile = repository.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("When the user is missing, then nothing is returned")
            .isNull();
    }

    @Test
    @DisplayName("When there is valid data, then the relationship is persisted")
    @ValidUser
    @ValidProfile
    void testAssignProfile_PersistedData() {
        final Collection<UserAssignedProfileEntity> profiles;

        // WHEN
        repository.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        profiles = userProfileSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserAssignedProfileEntity profile;

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
                .isEqualTo(UserProfileConstants.NUMBER);
            softly.assertThat(profile.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("When there is valid data, then the created relationship is returned")
    @ValidUser
    @ValidProfile
    void testAssignProfile_ReturnedData() {
        final UserProfile profile;

        // WHEN
        profile = repository.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("When there is valid data, then the created relationship is returned")
            .isEqualTo(UserProfiles.valid());
    }

}
