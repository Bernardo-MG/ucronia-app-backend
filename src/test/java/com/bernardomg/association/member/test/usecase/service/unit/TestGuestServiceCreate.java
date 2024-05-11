/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.member.test.usecase.service.unit;

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

import com.bernardomg.association.member.domain.model.Guest;
import com.bernardomg.association.member.domain.repository.GuestRepository;
import com.bernardomg.association.member.test.config.factory.GuestConstants;
import com.bernardomg.association.member.test.config.factory.Guests;
import com.bernardomg.association.member.usecase.service.DefaultGuestService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@ExtendWith(MockitoExtension.class)
@DisplayName("Guest service - create")
class TestGuestServiceCreate {

    @Mock
    private GuestRepository     guestRepository;

    @InjectMocks
    private DefaultGuestService service;

    public TestGuestServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a guest with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final Guest            guest;

        // GIVEN
        guest = Guests.emptyName();

        // WHEN
        execution = () -> service.create(guest);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("name", "empty", ""));
    }

    @Test
    @DisplayName("With a guest having padding whitespaces in name and surname, these whitespaces are removed and the guest is persisted")
    void testCreate_Padded_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.paddedWithWhitespaces();

        given(guestRepository.findNextNumber()).willReturn(GuestConstants.NUMBER);

        // WHEN
        service.create(guest);

        // THEN
        verify(guestRepository).save(Guests.valid());
    }

    @Test
    @DisplayName("With a valid guest, the guest is persisted")
    void testCreate_PersistedData() {
        final Guest guest;

        // GIVEN
        guest = Guests.valid();

        given(guestRepository.findNextNumber()).willReturn(GuestConstants.NUMBER);

        // WHEN
        service.create(guest);

        // THEN
        verify(guestRepository).save(Guests.valid());
    }

    @Test
    @DisplayName("With a valid guest, the created guest is returned")
    void testCreate_ReturnedData() {
        final Guest guest;
        final Guest created;

        // GIVEN
        guest = Guests.valid();

        given(guestRepository.save(Guests.valid())).willReturn(Guests.valid());
        given(guestRepository.findNextNumber()).willReturn(GuestConstants.NUMBER);

        // WHEN
        created = service.create(guest);

        // THEN
        Assertions.assertThat(created)
            .as("guest")
            .isEqualTo(Guests.valid());
    }

}
