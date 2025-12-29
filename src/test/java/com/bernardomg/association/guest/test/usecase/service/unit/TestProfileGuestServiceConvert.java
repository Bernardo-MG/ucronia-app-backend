/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.guest.test.usecase.service.unit;

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

import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
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
@DisplayName("DefaultProfileGuestService - convert to guest")
class TestProfileGuestServiceConvert {

    @Mock
    private GuestRepository            guestRepository;

    @Mock
    private ProfileRepository          profileRepository;

    @InjectMocks
    private DefaultProfileGuestService service;

    public TestProfileGuestServiceConvert() {
        super();
    }

    @Test
    @DisplayName("With an existing guest, an exception is thrown")
    void testConvertToGuest_ExistingGuest_Exception() {
        final ThrowingCallable execution;
        final Profile          profile;

        // GIVEN
        Guests.nameChange();
        profile = Profiles.valid();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.convertToGuest(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(GuestExistsException.class);
    }

    @Test
    @DisplayName("With a not existing profile, an exception is thrown")
    void testConvertToGuest_NotExistingContact_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        Guests.nameChange();

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.convertToGuest(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("When converting to guest, the change is persisted")
    void testConvertToGuest_PersistedData() {
        final Guest   guest;
        final Profile profile;

        // GIVEN
        guest = Guests.noGames();
        profile = Profiles.withType(GuestEntityConstants.CONTACT_TYPE);

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(false);

        // WHEN
        service.convertToGuest(ProfileConstants.NUMBER);

        // THEN
        verify(guestRepository).save(guest, ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When converting to guest, the change is returned")
    void testConvertToGuest_ReturnedData() {
        final Guest   guest;
        final Profile profile;
        final Guest   updated;

        // GIVEN
        guest = Guests.noGames();
        profile = Profiles.withType(GuestEntityConstants.CONTACT_TYPE);

        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(profile));
        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(false);
        given(guestRepository.save(guest, ProfileConstants.NUMBER)).willReturn(guest);

        // WHEN
        updated = service.convertToGuest(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(updated)
            .as("guest")
            .isEqualTo(guest);
    }

}
