
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.model.FeeProfile;
import com.bernardomg.association.fee.domain.repository.FeeProfileRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeProfiles;
import com.bernardomg.association.fee.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeProfileRepository - findByUsername")
class ITFeeProfileRepositoryFindByUsername {

    @Autowired
    private FeeProfileRepository repository;

    @Test
    @DisplayName("When the user exists, then it is returned")
    @ValidUserWithProfile
    void testFindByUsername() {
        final Optional<FeeProfile> profile;

        // WHEN
        profile = repository.findByUsername(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profile)
            .as("When the user exists, then it is returned")
            .contains(FeeProfiles.valid());
    }

    @Test
    @DisplayName("When no data exists, then nothing is returned")
    void testFindByUsername_NoData() {
        final Optional<FeeProfile> profile;

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
        final Optional<FeeProfile> profile;

        // WHEN
        profile = repository.findByUsername(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profile)
            .as("When the profile doesn't exist, then nothing is returned")
            .isEmpty();
    }

}
