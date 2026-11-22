
package com.bernardomg.association.guest.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

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
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGuestService - getAll")
class TestGuestServiceGetAll {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private GuestRepository         guestRepository;

    @InjectMocks
    private DefaultGuestService     service;

    @Test
    @DisplayName("When there is no data, then it returns nothing")
    void testGetAll_NoData() {
        final Page<Guest> guests;
        final Page<Guest> existing;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.empty());

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
    @DisplayName("When there is data, then it returns all the guests")
    void testGetAll_ReturnsData() {
        final Page<Guest> guests;
        final Page<Guest> existing;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.empty());

        existing = new Page<>(List.of(Guests.noGames()), 0, 0, 0, 0, 0, false, false, sorting);
        given(guestRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        guests = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("guests")
            .containsExactly(Guests.noGames());
    }

}
