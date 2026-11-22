
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
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfileConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfiles;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("UserProfileRepository - findOne")
class ITUserProfileRepositoryFindOne {

    @Autowired
    private UserProfileRepository repository;

    @Test
    @DisplayName("When there is a profile, then it is returned")
    @ValidProfile
    void testFindOne() {
        final Optional<UserProfile> profile;

        // WHEN
        profile = repository.findOne(UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("When there is a profile, then it is returned")
            .contains(UserProfiles.valid());
    }

    @Test
    @DisplayName("When there is no profile, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<UserProfile> profile;

        // WHEN
        profile = repository.findOne(UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("When there is no profile, then nothing is returned")
            .isEmpty();
    }

}
