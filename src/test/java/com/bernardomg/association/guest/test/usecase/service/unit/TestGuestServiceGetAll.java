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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.guest.domain.filter.GuestFilter;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.usecase.service.DefaultGuestService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGuestService - get all")
class TestGuestServiceGetAll {

    @Mock
    private GuestRepository     guestRepository;

    @InjectMocks
    private DefaultGuestService service;

    @Test
    @DisplayName("When there is no data, it returns nothing")
    void testGetAll_NoData() {
        final Page<Guest> guests;
        final Page<Guest> existing;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = new GuestFilter("");

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(guestRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        guests = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("guests")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is data, it returns all the guests")
    void testGetAll_ReturnsData() {
        final Page<Guest> guests;
        final Page<Guest> existing;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = new GuestFilter("");

        existing = new Page<>(List.of(Guests.valid()), 0, 0, 0, 0, 0, false, false, sorting);
        given(guestRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        guests = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("guests")
            .containsExactly(Guests.valid());
    }

}
