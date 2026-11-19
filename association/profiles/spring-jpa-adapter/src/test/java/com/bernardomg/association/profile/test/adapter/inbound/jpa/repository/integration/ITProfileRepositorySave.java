
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.PhoneContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ProfileWithEmail;
import com.bernardomg.association.profile.test.configuration.data.annotation.ProfileWithType;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.association.profile.test.factory.ProfileEntities;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ProfileRepository - save")
class ITProfileRepositorySave {

    @Autowired
    private ProfileRepository       repository;

    @Autowired
    private ProfileSpringRepository springRepository;

    @Test
    @DisplayName("When a profile exists and a contact method is added, then the profile is persisted")
    @EmailContactMethod
    @ValidProfile
    void testSave_Existing_AddContactMethod_PersistedData() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.withEmail();

        // WHEN
        repository.save(profile);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id", "audit")
            .containsExactly(ProfileEntities.withEmail());
    }

    @Test
    @DisplayName("When a profile exists, then the profile is persisted")
    @ValidProfile
    void testSave_Existing_NameChange_PersistedData() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.firstNameChange();

        // WHEN
        repository.save(profile);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "audit")
            .containsExactly(ProfileEntities.firstNameChange());
    }

    @Test
    @DisplayName("When a profile exists, then the created profile is returned")
    @ValidProfile
    void testSave_Existing_NameChange_ReturnedData() {
        final Profile profile;
        final Profile saved;

        // GIVEN
        profile = Profiles.firstNameChange();

        // WHEN
        saved = repository.save(profile);

        // THEN
        Assertions.assertThat(saved)
            .as("profile")
            .isEqualTo(Profiles.firstNameChange());
    }

    @Test
    @DisplayName("When a profile exists and a contact method is removed, then the profile is persisted")
    @EmailContactMethod
    @ProfileWithEmail
    void testSave_Existing_RemoveContactMethod_PersistedData() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.valid();

        // WHEN
        repository.save(profile);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "audit")
            .containsExactly(ProfileEntities.valid());
    }

    @Test
    @DisplayName("When a profile exists and a type is removed, then the profile is not changed")
    @ProfileWithType
    void testSave_Existing_RemoveType_NoChange() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.valid();

        // WHEN
        repository.save(profile);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "audit")
            .containsExactly(ProfileEntities.withType(ProfileConstants.TYPE_MEMBER));
    }

    @Test
    @DisplayName("When there is a valid profile, then the profile is persisted")
    void testSave_PersistedData() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.valid();

        // WHEN
        repository.save(profile);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "audit")
            .containsExactly(ProfileEntities.valid());
    }

    @Test
    @DisplayName("When there is a valid profile, then the created profile is returned")
    void testSave_ReturnedData() {
        final Profile profile;
        final Profile saved;

        // GIVEN
        profile = Profiles.valid();

        // WHEN
        saved = repository.save(profile);

        // THEN
        Assertions.assertThat(saved)
            .as("profile")
            .isEqualTo(Profiles.created());
    }

    @Test
    @DisplayName("When there is a profile with a contact method, then the profile is persisted")
    @EmailContactMethod
    void testSave_WithContactMethod_PersistedData() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.withEmail();

        // WHEN
        repository.save(profile);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id",
                "contactChannels.profile", "contactChannels.contactMethod", "audit")
            .containsExactly(ProfileEntities.withEmail());
    }

    @Test
    @DisplayName("When there is a profile with a contact method, then the profile is returned")
    @EmailContactMethod
    void testSave_WithContactMethod_ReturnedData() {
        final Profile profile;
        final Profile saved;

        // GIVEN
        profile = Profiles.withEmail();

        // WHEN
        saved = repository.save(profile);

        // THEN
        Assertions.assertThat(saved)
            .as("profile")
            .isEqualTo(Profiles.createdWithEmail());
    }

    @Test
    @DisplayName("When there is a profile with two profile channels for different methods, then the profile is persisted")
    @EmailContactMethod
    @PhoneContactMethod
    void testSave_WithTwoContactMethods_DifferentMethod_PersistedData() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.withEmailAndPhone();

        // WHEN
        repository.save(profile);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id",
                "contactChannels.profile", "contactChannels.contactMethod", "audit")
            .containsExactly(ProfileEntities.withEmailAndPhone());
    }

    @Test
    @DisplayName("When there is a profile with two profile channels for the same method, then the profile is persisted")
    @EmailContactMethod
    void testSave_WithTwoContactMethods_SameMethod_PersistedData() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.withTwoEmails();

        // WHEN
        repository.save(profile);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id",
                "contactChannels.profile", "contactChannels.contactMethod", "audit")
            .containsExactly(ProfileEntities.withTwoEmails());
    }

}
