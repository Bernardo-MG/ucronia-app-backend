
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ProfileRepository - exists")
class ITProfileRepositoryExists {

    @Autowired
    private ProfileRepository repository;

    @Test
    @DisplayName("When there is an existing profile, then it exists")
    @ValidProfile
    void testExists() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is no profile, then nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
