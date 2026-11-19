
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ProfileWithEmail;
import com.bernardomg.association.profile.test.configuration.data.annotation.ProfileWithType;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ProfileRepository - findOne")
class ITProfileRepositoryFindOne {

    @Autowired
    private ProfileRepository repository;

    @Test
    @DisplayName("When there is a profile, then it is returned")
    @ValidProfile
    void testFindOne() {
        final Optional<Profile> profile;

        // WHEN
        profile = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("profile")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a profile, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Profiles.valid()));
    }

    @Test
    @DisplayName("When there is no profile, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Profile> profile;

        // WHEN
        profile = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("When there is no profile, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a profile having a contact channel, then it is returned")
    @EmailContactMethod
    @ProfileWithEmail
    void testFindOne_WithContactChannel() {
        final Optional<Profile> profile;

        // WHEN
        profile = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("profile")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a profile having a contact channel, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Profiles.withEmail()));
    }

    @Test
    @DisplayName("When there is a profile with type, then it is returned")
    @ProfileWithType
    void testFindOne_WithType() {
        final Optional<Profile> profile;

        // WHEN
        profile = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("profile")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a profile with type, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Profiles.withType(ProfileConstants.TYPE_MEMBER)));
    }

}
