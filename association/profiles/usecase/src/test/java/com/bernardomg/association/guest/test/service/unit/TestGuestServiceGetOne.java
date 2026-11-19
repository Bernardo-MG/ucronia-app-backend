
package com.bernardomg.association.guest.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.guest.domain.exception.MissingGuestException;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.usecase.service.DefaultGuestService;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGuestService - getOne")
class TestGuestServiceGetOne {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private GuestRepository         guestRepository;

    @InjectMocks
    private DefaultGuestService     service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetOne() {
        final Optional<Guest> guest;

        // GIVEN
        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Guests.noGames()));

        // WHEN
        guest = service.getOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .as("When there is data, then it is returned")
            .contains(Guests.noGames());
    }

    @Test
    @DisplayName("When the guest doesn't exist, then an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the guest doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingGuestException.class);
    }

}
