
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

import com.bernardomg.association.guest.domain.exception.MissingGuestException;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.usecase.service.DefaultGuestService;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGuestService - patch")
class TestGuestServicePatch {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private GuestRepository         guestRepository;

    @InjectMocks
    private DefaultGuestService     service;

    @Test
    @DisplayName("When there is a guest with an existing identifier, then an exception is thrown")
    void testPatch_IdentifierExists() {
        final ThrowingCallable action;
        final Guest            guest;

        // GIVEN
        guest = Guests.noGames();

        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(guest));
        given(guestRepository.existsByIdentifierForAnother(ProfileConstants.NUMBER, ProfileConstants.IDENTIFIER))
            .willReturn(true);

        // WHEN
        action = () -> service.patch(guest);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "identifier", ProfileConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("When there is a not existing guest, then an exception is thrown")
    void testPatch_NotExisting_Exception() {
        final Guest            guest;
        final ThrowingCallable action;

        // GIVEN
        guest = Guests.firstNameChange();

        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.patch(guest);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing guest, then an exception is thrown")
            .isInstanceOf(MissingGuestException.class);
    }

    @Test
    @DisplayName("When patching the name, then the change is persisted")
    void testPatch_OnlyName_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.nameChangePatch();

        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Guests.noGames()));

        // WHEN
        service.patch(guest);

        // THEN
        verify(guestRepository).save(Guests.firstNameChange());
    }

    @Test
    @DisplayName("When there is a guest having padding whitespaces in first and last name, then these whitespaces are removed")
    void testPatch_Padded_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.padded();

        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Guests.noGames()));

        // WHEN
        service.patch(guest);

        // THEN
        verify(guestRepository).save(Guests.withGames());
    }

    @Test
    @DisplayName("When updating a guest, then the change is persisted")
    void testPatch_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.firstNameChange();

        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Guests.noGames()));

        // WHEN
        service.patch(guest);

        // THEN
        verify(guestRepository).save(Guests.firstNameChange());
    }

    @Test
    @DisplayName("When updating a guest, then the change is returned")
    void testPatch_ReturnedData() {
        final Guest guest;
        final Guest updated;

        // GIVEN
        guest = Guests.firstNameChange();

        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Guests.noGames()));
        given(guestRepository.save(Guests.firstNameChange())).willReturn(Guests.firstNameChange());

        // WHEN
        updated = service.patch(guest);

        // THEN
        Assertions.assertThat(updated)
            .as("guest")
            .isEqualTo(Guests.firstNameChange());
    }

}
