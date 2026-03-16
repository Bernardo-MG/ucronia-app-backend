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

package com.bernardomg.association.guest.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.guest.domain.filter.GuestFilter;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.data.annotation.MultipleGuests;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@DisplayName("GuestRepository - find all public - pagination")
@MultipleGuests
class ITGuestRepositoryFindAllPaginated extends AbstractPaginationIT<Guest> {

    @Autowired
    private GuestRepository repository;

    public ITGuestRepositoryFindAllPaginated() {
        super(5);
    }

    @Override
    protected final Page<Guest> read(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(new GuestFilter(""), pagination, sorting);
    }

    @Test
    @DisplayName("With pagination for the first page, it returns the first page")
    void testFindAll_Page1() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = Sorting.unsorted();
        filter = new GuestFilter("");

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Guests.forNumber(1));
    }

    @Test
    @DisplayName("With pagination for the second page, it returns the second page")
    void testFindAll_Page2() {
        final Page<Guest> guests;
        final Pagination  pagination;
        final Sorting     sorting;
        final GuestFilter filter;

        // GIVEN
        pagination = new Pagination(2, 1);
        sorting = Sorting.unsorted();
        filter = new GuestFilter("");

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Guests.forNumber(2));
    }

}
