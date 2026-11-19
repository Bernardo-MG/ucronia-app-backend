
package com.bernardomg.association.guest.test.adapter.inbound.jpa.repository.integration;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.guest.domain.filter.GuestFilter;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.data.annotation.GuestWithEmail;
import com.bernardomg.association.guest.test.configuration.data.annotation.ValidGuest;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GuestRepository - findAll")
class ITGuestRepositoryFindAll {

    @Autowired
    private GuestRepository repository;

    @Test
    @DisplayName("When there is a guest, then it is returned")
    @ValidGuest
    void testFindAll() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of());
        filter = new GuestFilter(Optional.empty());

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is a guest, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Guests.withGames());
    }

    @Test
    @DisplayName("When there is no guest, then nothing is returned")
    void testFindAll_NoData() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of());
        filter = new GuestFilter(Optional.empty());

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is no guest, then nothing is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a guest with no guestship, then it returns nothing")
    @ValidProfile
    void testFindAll_NoGuestship() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of());
        filter = new GuestFilter(Optional.empty());

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is a guest with no guestship, then it returns nothing")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a guest with a contact method, then it is returned")
    @GuestWithEmail
    void testFindAll_WithContactMethod() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of());
        filter = new GuestFilter(Optional.empty());

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is a guest with a contact method, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Guests.withEmail());
    }

}
