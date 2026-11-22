
package com.bernardomg.association.guest.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.GuestSpringRepository;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.data.annotation.ValidGuest;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.test.factory.GuestEntities;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GuestRepository - save")
class ITGuestRepositorySave {

    @Autowired
    private ProfileSpringRepository profileSpringRepository;

    @Autowired
    private GuestRepository         repository;

    @Autowired
    private GuestSpringRepository   springRepository;

    @Test
    @DisplayName("When a guest exists and a contact method is added, then the guest is persisted")
    @EmailContactMethod
    @ValidGuest
    void testSave_Existing_AddContactMethod_PersistedData() {
        final Guest                 guest;
        final Iterable<GuestEntity> entities;

        // GIVEN
        guest = Guests.withEmail();

        // WHEN
        repository.save(guest);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.contactChannels.id",
                "profile.contactChannels.profileId", "profile.contactChannels.profile", "profile.audit")
            .containsExactly(GuestEntities.createdWithEmail());
    }

    @Test
    @DisplayName("When a guest exists, then the guest is persisted")
    @ValidGuest
    void testSave_Existing_ChangeName_PersistedData() {
        final Guest                 guest;
        final Iterable<GuestEntity> entities;

        // GIVEN
        guest = Guests.firstNameChange();

        // WHEN
        repository.save(guest);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.contactChannels.id",
                "profile.contactChannels.profileId", "profile.contactChannels.profile", "profile.audit")
            .containsExactly(GuestEntities.firstNameChange());
    }

    @Test
    @DisplayName("When a guest exists, then the created guest is returned")
    @ValidGuest
    void testSave_Existing_ChangeName_ReturnedData() {
        final Guest guest;
        final Guest saved;

        // GIVEN
        guest = Guests.firstNameChange();

        // WHEN
        saved = repository.save(guest);

        // THEN
        Assertions.assertThat(saved)
            .as("guest")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Guests.firstNameChange());
    }

    @Test
    @DisplayName("When the guest doesn't exist but the profile exists, then the guest is persisted")
    @ValidProfile
    void testSave_ExistingProfile_PersistedData() {
        final Guest                 guest;
        final Iterable<GuestEntity> entities;

        // GIVEN
        guest = Guests.noGames();

        // WHEN
        repository.save(guest);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.contactChannels.id",
                "profile.contactChannels.profileId", "profile.contactChannels.profile", "profile.audit")
            .containsExactly(GuestEntities.noGames());
    }

    @Test
    @DisplayName("When there is a guest, then the guest is persisted")
    void testSave_PersistedData() {
        final Guest                 guest;
        final Iterable<GuestEntity> entities;

        // GIVEN
        guest = Guests.noGames();

        // WHEN
        repository.save(guest);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile",
                "profile.audit")
            .containsExactly(GuestEntities.noGames());
    }

    @Test
    @DisplayName("When the type is removed, then the guest is not changed")
    void testSave_RemoveType_NoChange() {
        final Guest                 guest;
        final Iterable<GuestEntity> entities;

        // GIVEN
        guest = Guests.withoutType();

        // WHEN
        repository.save(guest);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile",
                "profile.audit")
            .containsExactly(GuestEntities.created());
    }

    @Test
    @DisplayName("When there is a guest, then the created guest is returned")
    void testSave_ReturnedData() {
        final Guest guest;
        final Guest saved;

        // GIVEN
        guest = Guests.noGames();

        // WHEN
        saved = repository.save(guest);

        // THEN
        Assertions.assertThat(saved)
            .as("guest")
            .isEqualTo(Guests.created());
    }

    @Test
    @DisplayName("When the guest is persisted, then the profile types includes the guest type")
    void testSave_SetsType() {
        final Guest         guest;
        final ProfileEntity profile;

        // GIVEN
        guest = Guests.noGames();

        // WHEN
        repository.save(guest);

        // THEN
        profile = profileSpringRepository.findByNumber(1L)
            .get();

        Assertions.assertThat(profile)
            .as("profile")
            .extracting(ProfileEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(GuestEntityConstants.PROFILE_TYPE);
    }

    @Test
    @DisplayName("When there is a guest with a contact method, then the guest is persisted")
    @EmailContactMethod
    void testSave_WithContactMethod_PersistedData() {
        final Guest                 guest;
        final Iterable<GuestEntity> entities;

        // GIVEN
        guest = Guests.withEmail();

        // WHEN
        repository.save(guest);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile",
                "profile.audit")
            .containsExactly(GuestEntities.createdWithEmail());
    }

}
