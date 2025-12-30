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

package com.bernardomg.association.sponsor.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.domain.filter.SponsorFilter;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.ValidSponsor;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SponsorRepository - find all - filter by name")
class ITSponsorRepositoryFindAllQueryName {

    @Autowired
    private SponsorRepository repository;

    @Test
    @DisplayName("With a sponsor matching first name, it is returned")
    @ValidSponsor
    void testFindAll_FirstName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new SponsorFilter(ProfileConstants.FIRST_NAME);

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Sponsors.valid());
    }

    @Test
    @DisplayName("With a sponsor having an active sponsorship and matching full name, it is returned")
    @ValidSponsor
    void testFindAll_FullName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new SponsorFilter(ProfileConstants.FULL_NAME);

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Sponsors.valid());
    }

    @Test
    @DisplayName("With a sponsor having an active sponsorship and matching last name, it is returned")
    @ValidSponsor
    void testFindAll_LastName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new SponsorFilter(ProfileConstants.LAST_NAME);

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Sponsors.valid());
    }

    @Test
    @DisplayName("With no sponsor, nothing is returned")
    void testFindAll_NoData() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new SponsorFilter(ProfileConstants.FIRST_NAME);

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a sponsor having an active sponsorship and partial matching name, it is returned")
    @ValidSponsor
    void testFindAll_PartialName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new SponsorFilter(ProfileConstants.FIRST_NAME.substring(0, ProfileConstants.FIRST_NAME.length() - 2));

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Sponsors.valid());
    }

    @Test
    @DisplayName("With a sponsor without sponsorship and matching first name, it is is returned")
    @ValidProfile
    void testFindAll_WithoutSponsorship_FirstName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new SponsorFilter(ProfileConstants.FIRST_NAME);

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a sponsor having an active sponsorship and wrong name, nothing is returned")
    @ValidSponsor
    void testFindAll_WrongName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new SponsorFilter(ProfileConstants.ALTERNATIVE_FIRST_NAME);

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
