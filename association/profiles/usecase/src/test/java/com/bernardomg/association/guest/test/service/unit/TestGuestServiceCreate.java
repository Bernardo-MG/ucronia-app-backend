
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

import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.usecase.service.DefaultGuestService;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGuestService - create")
class TestGuestServiceCreate {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private GuestRepository         guestRepository;

    @InjectMocks
    private DefaultGuestService     service;

    @Test
    @DisplayName("When there is a guest with an existing identifier, then an exception is thrown")
    void testCreate_IdentifierExists() {
        final ThrowingCallable action;
        final Guest            guest;

        // GIVEN
        guest = Guests.noGames();

        given(guestRepository.existsByIdentifier(ProfileConstants.IDENTIFIER)).willReturn(true);

        // WHEN
        action = () -> service.create(guest);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "identifier", ProfileConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("When there is a guest having padding whitespaces, then these whitespaces are removed and the profile is persisted")
    void testCreate_Padded_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.padded();

        // WHEN
        service.create(guest);

        // THEN
        verify(guestRepository).save(Guests.withGames());
    }

    @Test
    @DisplayName("When there is a valid guest, then the profile is persisted")
    void testCreate_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.noGames();

        // WHEN
        service.create(guest);

        // THEN
        verify(guestRepository).save(Guests.noGames());
    }

    @Test
    @DisplayName("When there is a valid guest, then the created profile is returned")
    void testCreate_ReturnedData() {
        final Guest guest;
        final Guest created;

        // GIVEN
        guest = Guests.noGames();

        given(guestRepository.save(guest)).willReturn(Guests.noGames());

        // WHEN
        created = service.create(guest);

        // THEN
        Assertions.assertThat(created)
            .as("profile")
            .isEqualTo(Guests.noGames());
    }

}
