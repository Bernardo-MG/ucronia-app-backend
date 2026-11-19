
package com.bernardomg.association.guest.test.adapter.inbound.jpa.repository.integration;

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
import com.bernardomg.association.guest.test.configuration.data.annotation.ValidGuest;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GuestRepository - findAll")
class ITGuestRepositoryFindAllQueryName {

    @Autowired
    private GuestRepository repository;

    @Test
    @DisplayName("When there is a guest matching first name, then it is returned")
    @ValidGuest
    void testFindAll_FirstName() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.of(ProfileConstants.FIRST_NAME));

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is a guest matching first name, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Guests.withGames());
    }

    @Test
    @DisplayName("When there is a guest having a guest role and matching full name, then it is returned")
    @ValidGuest
    void testFindAll_FullName() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.of(ProfileConstants.FULL_NAME));

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is a guest having a guest role and matching full name, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Guests.withGames());
    }

    @Test
    @DisplayName("When there is a guest having a guest role and matching last name, then it is returned")
    @ValidGuest
    void testFindAll_LastName() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.of(ProfileConstants.LAST_NAME));

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is a guest having a guest role and matching last name, then it is returned")
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
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.of(ProfileConstants.FIRST_NAME));

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
    @DisplayName("When there is a guest having a guest role and partial matching name, then it is returned")
    @ValidGuest
    void testFindAll_PartialName() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(
            Optional.of(ProfileConstants.FIRST_NAME.substring(0, ProfileConstants.FIRST_NAME.length() - 2)));

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is a guest having a guest role and partial matching name, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Guests.withGames());
    }

    @Test
    @DisplayName("When there is a guest without guest role and matching first name, then it is is returned")
    @ValidProfile
    void testFindAll_WithoutGuestship_FirstName() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.of(ProfileConstants.FIRST_NAME));

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is a guest without guest role and matching first name, then it is is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a guest having a guest role and wrong name, then nothing is returned")
    @ValidGuest
    void testFindAll_WrongName() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.of(ProfileConstants.ALTERNATIVE_FIRST_NAME));

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When there is a guest having a guest role and wrong name, then nothing is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
