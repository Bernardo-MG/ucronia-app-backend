
package com.bernardomg.association.guest.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.GuestSpringRepository;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.data.annotation.ValidGuest;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GuestRepository - delete")
class ITGuestRepositoryDelete {

    @Autowired
    private ProfileSpringRepository profileSpringRepository;

    @Autowired
    private GuestRepository         repository;

    @Autowired
    private GuestSpringRepository   springRepository;

    @Test
    @DisplayName("When deleting a guest, then it is deleted")
    @ValidGuest
    void testDelete() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("When deleting a guest, then it is deleted")
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
    @DisplayName("When there is a profile with no guest role, then nothing is deleted")
    @ValidProfile
    void testDelete_NoGuest() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("When there is a profile with no guest role, then nothing is deleted")
            .isZero();
    }

    @Test
    @DisplayName("When deleting a guest, then the profile is deleted")
    @ValidGuest
    void testDelete_Profile() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profileSpringRepository.count())
            .as("When deleting a guest, then the profile is deleted")
            .isZero();
    }

}
