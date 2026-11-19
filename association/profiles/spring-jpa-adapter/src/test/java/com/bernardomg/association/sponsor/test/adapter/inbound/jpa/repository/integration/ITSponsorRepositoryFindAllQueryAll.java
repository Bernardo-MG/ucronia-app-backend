
package com.bernardomg.association.sponsor.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.sponsor.domain.filter.SponsorFilter;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.SponsorWithEmail;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.ValidSponsor;
import com.bernardomg.association.sponsor.test.configuration.factory.SponsorFilters;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("SponsorRepository - findAll")
class ITSponsorRepositoryFindAllQueryAll {

    @Autowired
    private SponsorRepository repository;

    @Test
    @DisplayName("When there is a sponsor, then it is returned")
    @ValidSponsor
    void testFindAll() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.empty();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is a sponsor, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Sponsors.valid());
    }

    @Test
    @DisplayName("When there is no sponsor, then nothing is returned")
    void testFindAll_NoData() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.empty();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is no sponsor, then nothing is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a sponsor with a contact methdo, then it is returned")
    @SponsorWithEmail
    void testFindAll_WithContactMethdo() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.empty();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is a sponsor with a contact methdo, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Sponsors.withEmail());
    }

    @Test
    @DisplayName("When there is a sponsor without sponsorship, then it is returned")
    @ValidProfile
    void testFindAll_WithoutSponsorship() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.empty();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is a sponsor without sponsorship, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
