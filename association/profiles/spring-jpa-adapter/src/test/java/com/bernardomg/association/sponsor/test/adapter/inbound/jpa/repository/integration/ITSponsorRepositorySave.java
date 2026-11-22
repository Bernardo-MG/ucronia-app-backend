
package com.bernardomg.association.sponsor.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.SponsorSpringRepository;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.SponsorWithEmail;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.ValidSponsor;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.association.sponsor.test.factory.SponsorEntities;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("SponsorRepository - save")
class ITSponsorRepositorySave {

    @Autowired
    private ProfileSpringRepository profileSpringRepository;

    @Autowired
    private SponsorRepository       repository;

    @Autowired
    private SponsorSpringRepository springRepository;

    @Test
    @DisplayName("When a sponsor exists and a contact method is added, then the sponsor is persisted")
    @EmailContactMethod
    @ValidSponsor
    void testSave_Existing_AddContactMethod_PersistedData() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.withEmail();

        // WHEN
        repository.save(sponsor);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.contactChannels.id",
                "profile.contactChannels.profileId", "profile.contactChannels.profile", "profile.audit")
            .containsExactly(SponsorEntities.withEmail());
    }

    @Test
    @DisplayName("When a sponsor name is changed, then the sponsor is persisted")
    @ValidSponsor
    void testSave_Existing_ChangeName_PersistedData() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.firstNameChange();

        // WHEN
        repository.save(sponsor);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.contactChannels.id",
                "profile.contactChannels.profileId", "profile.contactChannels.profile", "profile.audit")
            .containsExactly(SponsorEntities.firstNameChange());
    }

    @Test
    @DisplayName("When a sponsor name is changed, then the created sponsor is returned")
    @ValidSponsor
    void testSave_Existing_ChangeName_ReturnedData() {
        final Sponsor sponsor;
        final Sponsor saved;

        // GIVEN
        sponsor = Sponsors.firstNameChange();

        // WHEN
        saved = repository.save(sponsor);

        // THEN
        Assertions.assertThat(saved)
            .as("sponsor")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Sponsors.firstNameChange());
    }

    @Test
    @DisplayName("When a sponsor exists and a contact method is removed, then the sponsor is persisted")
    @SponsorWithEmail
    void testSave_Existing_RemoveContactMethod_PersistedData() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        repository.save(sponsor);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.contactChannels.id",
                "profile.contactChannels.profileId", "profile.contactChannels.profile", "profile.audit")
            .containsExactly(SponsorEntities.valid());
    }

    @Test
    @DisplayName("When a sponsor doesn't exist but the profile does, then the sponsor is persisted")
    @ValidProfile
    void testSave_ExistingProfile_ChangeName_PersistedData() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        repository.save(sponsor);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.contactChannels.id",
                "profile.contactChannels.profileId", "profile.contactChannels.profile", "profile.audit")
            .containsExactly(SponsorEntities.valid());
    }

    @Test
    @DisplayName("When there is an sponsor, then the sponsor is persisted")
    void testSave_PersistedData() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        repository.save(sponsor);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile",
                "profile.audit")
            .containsExactly(SponsorEntities.created());
    }

    @Test
    @DisplayName("When the type is removed, then the sponsor is not changed")
    void testSave_RemoveType_NoChange() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.withoutType();

        // WHEN
        repository.save(sponsor);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile",
                "profile.audit")
            .containsExactly(SponsorEntities.created());
    }

    @Test
    @DisplayName("When there is an sponsor, then the created sponsor is returned")
    void testSave_ReturnedData() {
        final Sponsor sponsor;
        final Sponsor saved;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        saved = repository.save(sponsor);

        // THEN
        Assertions.assertThat(saved)
            .as("sponsor")
            .isEqualTo(Sponsors.created());
    }

    @Test
    @DisplayName("When the sponsor is persisted, then the profile types includes the sponsor type")
    void testSave_SetsType() {
        final Sponsor       sponsor;
        final ProfileEntity profile;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        repository.save(sponsor);

        // THEN
        profile = profileSpringRepository.findByNumber(1L)
            .get();

        Assertions.assertThat(profile)
            .as("profile")
            .extracting(ProfileEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(SponsorEntityConstants.PROFILE_TYPE);
    }

    @Test
    @DisplayName("When there is an sponsor with contact method, then the sponsor is persisted")
    @EmailContactMethod
    void testSave_WithContactMethod_PersistedData() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.withEmail();

        // WHEN
        repository.save(sponsor);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile",
                "profile.audit")
            .containsExactly(SponsorEntities.createdWithEmail());
    }

}
