
package com.bernardomg.association.guest.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.data.annotation.ValidGuest;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GuestRepository - exists")
class ITGuestRepositoryExists {

    @Autowired
    private GuestRepository repository;

    @Test
    @DisplayName("When there is a guest, then it exists")
    @ValidGuest
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
    @DisplayName("When there is no guest, then nothing exists")
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
    @DisplayName("When there is no guest role, then nothing exists")
    @ValidProfile
    void testExists_NoGuest() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
