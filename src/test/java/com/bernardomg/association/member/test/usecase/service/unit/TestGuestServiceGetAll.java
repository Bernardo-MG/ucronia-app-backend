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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Guest;
import com.bernardomg.association.member.domain.repository.GuestRepository;
import com.bernardomg.association.member.test.config.factory.Guests;
import com.bernardomg.association.member.usecase.service.DefaultGuestService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Guest service - get all")
class TestGuestServiceGetAll {

    @Mock
    private GuestRepository     guestRepository;

    @InjectMocks
    private DefaultGuestService service;

    public TestGuestServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When there is no data, it returns nothing")
    void testGetAll_NoData() {
        final Iterable<Guest> guests;
        final Pageable        pageable;
        final Page<Guest>     readGuests;

        // GIVEN
        readGuests = new PageImpl<>(List.of());
        given(guestRepository.findAll(ArgumentMatchers.any())).willReturn(readGuests);

        pageable = Pageable.unpaged();

        // WHEN
        guests = service.getAll(pageable);

        // THEN
        Assertions.assertThat(guests)
            .as("guests")
            .isEmpty();
    }

    @Test
    @DisplayName("When getting all the guests, it returns all the guests")
    void testGetAll_ReturnsData() {
        final Iterable<Guest> guests;
        final Pageable        pageable;
        final Page<Guest>     readGuests;

        // GIVEN
        given(guestRepository.findAll(ArgumentMatchers.any())).willReturn(List.of(Guests.valid()));

        pageable = Pageable.unpaged();

        // WHEN
        guests = service.getAll(pageable);

        // THEN
        Assertions.assertThat(guests)
            .as("guests")
            .isEqualTo(List.of(Guests.valid()));
    }

}
