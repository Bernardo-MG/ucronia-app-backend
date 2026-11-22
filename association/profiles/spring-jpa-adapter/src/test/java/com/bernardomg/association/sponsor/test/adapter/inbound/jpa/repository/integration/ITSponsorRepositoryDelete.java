
package com.bernardomg.association.sponsor.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.SponsorSpringRepository;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.SponsorWithEmail;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("SponsorRepository - delete")
class ITSponsorRepositoryDelete {

    @Autowired
    private ProfileSpringRepository profileSpringRepository;

    @Autowired
    private SponsorRepository       repository;

    @Autowired
    private SponsorSpringRepository springRepository;

    @Test
    @DisplayName("When deleting an sponsor, then it is deleted")
    @SponsorWithEmail
    void testDelete_Active() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("When deleting an sponsor, then it is deleted")
            .isZero();
    }

    @Test
    @DisplayName("When deleting an sponsor, then the profile is deleted")
    @SponsorWithEmail
    void testDelete_Active_Profile() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profileSpringRepository.count())
            .as("When deleting an sponsor, then the profile is deleted")
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("When there is no data, then nothing is deleted")
            .isZero();
    }

    @Test
    @DisplayName("When there is a profile with no sponsor role, then nothing is deleted")
    @ValidProfile
    void testDelete_NoSponsor() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("When there is a profile with no sponsor role, then nothing is deleted")
            .isZero();
    }

}
