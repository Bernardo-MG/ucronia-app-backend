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

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGuestService - create")
class TestGuestServiceCreate {

    @Mock
    private GuestRepository     guestRepository;

    @InjectMocks
    private DefaultGuestService service;

    public TestGuestServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a guest having padding whitespaces, these whitespaces are removed and the profile is persisted")
    void testCreate_Padded_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.padded();

        // WHEN
        service.create(guest);

        // THEN
        verify(guestRepository).save(Guests.valid());
    }

    @Test
    @DisplayName("With a valid guest, the profile is persisted")
    void testCreate_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        service.create(guest);

        // THEN
        verify(guestRepository).save(Guests.valid());
    }

    @Test
    @DisplayName("With a valid guest, the created profile is returned")
    void testCreate_ReturnedData() {
        final Guest guest;
        final Guest created;

        // GIVEN
        guest = Guests.valid();

        given(guestRepository.save(guest)).willReturn(Guests.valid());

        // WHEN
        created = service.create(guest);

        // THEN
        Assertions.assertThat(created)
            .as("profile")
            .isEqualTo(Guests.valid());
    }

}
