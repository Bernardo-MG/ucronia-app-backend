
package com.bernardomg.association.security.user.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.security.user.domain.model.UserProfile;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.TestApplication;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithProfile;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfiles;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("UserProfileRepository - findByUsername")
class ITUserProfileRepositoryFindByUsername {

    @Autowired
    private UserProfileRepository repository;

    @Test
    @DisplayName("When the user exists, then it is returned")
    @ValidUserWithProfile
    void testFindByUsername() {
        final Optional<UserProfile> profile;

        // WHEN
        profile = repository.findByUsername(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profile)
            .as("When the user exists, then it is returned")
            .contains(UserProfiles.valid());
    }

    @Test
    @DisplayName("When no data exists, then nothing is returned")
    void testFindByUsername_NoData() {
        final Optional<UserProfile> profile;

        // WHEN
        profile = repository.findByUsername(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profile)
            .as("When no data exists, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When the profile doesn't exist, then nothing is returned")
    @ValidUser
    void testFindByUsername_NoMember() {
        final Optional<UserProfile> profile;

        // WHEN
        profile = repository.findByUsername(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profile)
            .as("When the profile doesn't exist, then nothing is returned")
            .isEmpty();
    }

}
