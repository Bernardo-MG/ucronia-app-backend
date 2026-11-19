
package com.bernardomg.association.sponsor.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.SponsorWithEmail;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("SponsorRepository - exists")
class ITSponsorRepositoryExists {

    @Autowired
    private SponsorRepository repository;

    @Test
    @DisplayName("When there is an sponsor, then it exists")
    @SponsorWithEmail
    void testExists_Active() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is no sponsor, then nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is a sponsor with no sponsor role, then it returns nothing")
    @ValidProfile
    void testExists_NoSponsor() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
