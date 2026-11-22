
package com.bernardomg.association.sponsor.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.SponsorWithEmail;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.ValidSponsor;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("SponsorRepository - findOne")
class ITSponsorRepositoryFindOne {

    @Autowired
    private SponsorRepository repository;

    @Test
    @DisplayName("When there is an sponsor, then it is returned")
    @ValidSponsor
    void testFindOne() {
        final Optional<Sponsor> sponsor;

        // WHEN
        sponsor = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(sponsor)
            .as("When there is an sponsor, then it is returned")
            .contains(Sponsors.valid());
    }

    @Test
    @DisplayName("When there is no sponsor, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Sponsor> sponsor;

        // WHEN
        sponsor = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(sponsor)
            .as("When there is no sponsor, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a sponsor with no sponsorship, then it returns nothing")
    @ValidProfile
    void testFindOne_NoSponsorship() {
        final Optional<Sponsor> sponsor;

        // WHEN
        sponsor = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(sponsor)
            .as("When there is a sponsor with no sponsorship, then it returns nothing")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is an sponsor with a contact method, then it is returned")
    @SponsorWithEmail
    void testFindOne_WithContactMethod() {
        final Optional<Sponsor> sponsor;

        // WHEN
        sponsor = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(sponsor)
            .as("When there is an sponsor with a contact method, then it is returned")
            .contains(Sponsors.withEmail());
    }

}
