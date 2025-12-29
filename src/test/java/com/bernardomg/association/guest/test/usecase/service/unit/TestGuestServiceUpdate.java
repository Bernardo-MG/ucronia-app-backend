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
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGuestService - update")
class TestGuestServiceUpdate {

    @Mock
    private GuestRepository     guestRepository;

    @InjectMocks
    private DefaultGuestService service;

    public TestGuestServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With a not existing guest, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Guest            guest;
        final ThrowingCallable execution;

        // GIVEN
        guest = Guests.nameChange();

        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(guest);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingGuestException.class);
    }

    @Test
    @DisplayName("With a guest having padding whitespaces in first and last name, these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.padded();

        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(guest);

        // THEN
        verify(guestRepository).save(Guests.valid());
    }

    @Test
    @DisplayName("When updating a guest, the change is persisted")
    void testUpdate_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.nameChange();

        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(guestRepository.save(Guests.nameChange())).willReturn(Guests.nameChange());

        // WHEN
        service.update(guest);

        // THEN
        verify(guestRepository).save(Guests.nameChange());
    }

    @Test
    @DisplayName("When updating a guest, the change is returned")
    void testUpdate_ReturnedData() {
        final Guest guest;
        final Guest updated;

        // GIVEN
        guest = Guests.nameChange();

        given(guestRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(guestRepository.save(Guests.nameChange())).willReturn(Guests.nameChange());

        // WHEN
        updated = service.update(guest);

        // THEN
        Assertions.assertThat(updated)
            .as("guest")
            .isEqualTo(Guests.nameChange());
    }

}
