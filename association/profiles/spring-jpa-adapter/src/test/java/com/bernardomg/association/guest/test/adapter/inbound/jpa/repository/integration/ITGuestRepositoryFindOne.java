
package com.bernardomg.association.guest.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.data.annotation.GuestWithEmail;
import com.bernardomg.association.guest.test.configuration.data.annotation.ValidGuest;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GuestRepository - findOne")
class ITGuestRepositoryFindOne {

    @Autowired
    private GuestRepository repository;

    @Test
    @DisplayName("When there is a guest, then it is returned")
    @ValidGuest
    void testFindOne() {
        final Optional<Guest> guest;

        // WHEN
        guest = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .as("When there is a guest, then it is returned")
            .contains(Guests.withGames());
    }

    @Test
    @DisplayName("When there is no guest, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Guest> guest;

        // WHEN
        guest = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .as("When there is no guest, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a guest with no guest role, then it returns nothing")
    @ValidProfile
    void testFindOne_NoGuestship() {
        final Optional<Guest> guest;

        // WHEN
        guest = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .as("When there is a guest with no guest role, then it returns nothing")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a guest with a contact method, then it is returned")
    @GuestWithEmail
    void testFindOne_WithContactMethod() {
        final Optional<Guest> guest;

        // WHEN
        guest = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .as("When there is a guest with a contact method, then it is returned")
            .contains(Guests.withEmail());
    }

}
