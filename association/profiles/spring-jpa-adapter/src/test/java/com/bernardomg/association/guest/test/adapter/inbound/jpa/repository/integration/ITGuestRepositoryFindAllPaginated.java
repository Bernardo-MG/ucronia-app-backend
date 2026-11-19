
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
import com.bernardomg.association.guest.test.configuration.data.annotation.MultipleGuests;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GuestRepository - findAll")
@MultipleGuests
class ITGuestRepositoryFindAllPaginated extends AbstractPaginationIT<Guest> {

    @Autowired
    private GuestRepository repository;

    public ITGuestRepositoryFindAllPaginated() {
        super(5);
    }

    @Override
    protected final Page<Guest> read(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(new GuestFilter(Optional.empty()), pagination, sorting);
    }

    @Test
    @DisplayName("When requesting pagination for the first page, then it returns the first page")
    void testFindAll_Page1() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.empty());

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When requesting pagination for the first page, then it returns the first page")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Guests.forNumber(1));
    }

    @Test
    @DisplayName("When requesting pagination for the second page, then it returns the second page")
    void testFindAll_Page2() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(2, 1);
        sorting = Sorting.unsorted();
        filter = new GuestFilter(Optional.empty());

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .as("When requesting pagination for the second page, then it returns the second page")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Guests.forNumber(2));
    }

}
