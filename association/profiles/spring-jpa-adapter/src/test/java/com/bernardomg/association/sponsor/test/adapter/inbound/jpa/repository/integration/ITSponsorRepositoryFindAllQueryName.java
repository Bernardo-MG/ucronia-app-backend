
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
class ITSponsorRepositoryFindAllQueryName {

    @Autowired
    private SponsorRepository repository;

    @Test
    @DisplayName("When there is a sponsor matching first name, then it is returned")
    @ValidSponsor
    void testFindAll_FirstName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.firstName();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is a sponsor matching first name, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Sponsors.valid());
    }

    @Test
    @DisplayName("When there is a sponsor having an active sponsorship and matching full name, then it is returned")
    @ValidSponsor
    void testFindAll_FullName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.fullName();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is a sponsor having an active sponsorship and matching full name, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Sponsors.valid());
    }

    @Test
    @DisplayName("When there is a sponsor having an active sponsorship and matching last name, then it is returned")
    @ValidSponsor
    void testFindAll_LastName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.lastName();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is a sponsor having an active sponsorship and matching last name, then it is returned")
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
        filter = SponsorFilters.firstName();

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
    @DisplayName("When there is a sponsor having an active sponsorship and partial matching name, then it is returned")
    @ValidSponsor
    void testFindAll_PartialName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.partialName();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is a sponsor having an active sponsorship and partial matching name, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Sponsors.valid());
    }

    @Test
    @DisplayName("When there is a sponsor without sponsorship and matching first name, then it is is returned")
    @ValidProfile
    void testFindAll_WithoutSponsorship_FirstName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.firstName();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is a sponsor without sponsorship and matching first name, then it is is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a sponsor having an active sponsorship and wrong name, then nothing is returned")
    @SponsorWithEmail
    void testFindAll_WrongName() {
        final Page<Sponsor> sponsors;
        final Pagination    pagination;
        final Sorting       sorting;
        final SponsorFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = SponsorFilters.alternativeFirstName();

        // WHEN
        sponsors = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(sponsors)
            .as("When there is a sponsor having an active sponsorship and wrong name, then nothing is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
