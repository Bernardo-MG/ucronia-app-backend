
package com.bernardomg.association.guest.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.guest.domain.exception.GuestExistsException;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.usecase.service.DefaultProfileGuestService;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultProfileGuestService - convertToGuest")
class TestProfileGuestServiceConvert {

    @Mock
    private GuestRepository            guestRepository;

    @Mock
    private ProfileRepository          profileRepository;

    @InjectMocks
    private DefaultProfileGuestService service;

    @Test
    @DisplayName("When there is an existing guest, then an exception is thrown")
    void testConvertToGuest_ExistingGuest_Exception() {
        final ThrowingCallable action;
        final Profile          profile;

        // GIVEN
        Guests.firstNameChange();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.convertToGuest(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is an existing guest, then an exception is thrown")
            .isInstanceOf(GuestExistsException.class);
    }

    @Test
    @DisplayName("When there is a not existing profile, then an exception is thrown")
    void testConvertToGuest_NotExistingProfile_Exception() {
        final ThrowingCallable action;

        // GIVEN
        Guests.firstNameChange();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.convertToGuest(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing profile, then an exception is thrown")
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("When converting to guest, then the change is persisted")
    void testConvertToGuest_PersistedData() {
        final Guest   guest;
        final Profile profile;

        // GIVEN
        guest = Guests.noGames();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(false);

        // WHEN
        service.convertToGuest(ProfileConstants.NUMBER);

        // THEN
        verify(guestRepository).save(guest);
    }

    @Test
    @DisplayName("When converting to guest, then the change is returned")
    void testConvertToGuest_ReturnedData() {
        final Guest   guest;
        final Profile profile;
        final Guest   updated;

        // GIVEN
        guest = Guests.noGames();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(false);
        given(guestRepository.save(guest)).willReturn(guest);

        // WHEN
        updated = service.convertToGuest(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(updated)
            .as("guest")
            .isEqualTo(guest);
    }

}
