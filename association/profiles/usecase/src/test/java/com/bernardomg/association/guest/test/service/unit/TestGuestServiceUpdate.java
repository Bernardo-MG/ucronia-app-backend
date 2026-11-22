
package com.bernardomg.association.guest.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
@DisplayName("DefaultGuestService - update")
class TestGuestServiceUpdate {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private GuestRepository         guestRepository;

    @InjectMocks
    private DefaultGuestService     service;

    @Test
    @DisplayName("When there is a guest with an existing identifier, then an exception is thrown")
    void testUpdate_IdentifierExists() {
        final ThrowingCallable action;
        final Guest            guest;

        // GIVEN
        guest = Guests.firstNameChange();

        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(guestRepository.existsByIdentifierForAnother(ProfileConstants.NUMBER, ProfileConstants.IDENTIFIER))
            .willReturn(true);

        // WHEN
        action = () -> service.update(guest);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "identifier", ProfileConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("When there is a not existing guest, then an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Guest            guest;
        final ThrowingCallable action;

        // GIVEN
        guest = Guests.firstNameChange();

        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(guest);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing guest, then an exception is thrown")
            .isInstanceOf(MissingGuestException.class);
    }

    @Test
    @DisplayName("When there is a guest having padding whitespaces in first and last name, then these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.padded();

        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(guest);

        // THEN
        verify(guestRepository).save(Guests.withGames());
    }

    @Test
    @DisplayName("When updating a guest, then the change is persisted")
    void testUpdate_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.firstNameChange();

        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(guestRepository.save(Guests.firstNameChange())).willReturn(Guests.firstNameChange());

        // WHEN
        service.update(guest);

        // THEN
        verify(guestRepository).save(Guests.firstNameChange());
    }

    @Test
    @DisplayName("When updating a guest, then the change is returned")
    void testUpdate_ReturnedData() {
        final Guest guest;
        final Guest updated;

        // GIVEN
        guest = Guests.firstNameChange();

        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(guestRepository.save(Guests.firstNameChange())).willReturn(Guests.firstNameChange());

        // WHEN
        updated = service.update(guest);

        // THEN
        Assertions.assertThat(updated)
            .as("guest")
            .isEqualTo(Guests.firstNameChange());
    }

}
