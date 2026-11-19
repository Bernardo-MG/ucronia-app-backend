
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.profile.domain.filter.ProfileFilter;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileQueries;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ProfileRepository - findAll")
class ITProfileRepositoryFindAllQueryName {

    @Autowired
    private ProfileRepository repository;

    @Test
    @DisplayName("When there is a valid profile and matching first name, then it is is returned")
    @ValidProfile
    void testFindAll_FirstName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = ProfileQueries.firstName();

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .as("When there is a valid profile and matching first name, then it is is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Profiles.valid());
    }

    @Test
    @DisplayName("When there is a valid profile and matching full name, then it is is returned")
    @ValidProfile
    void testFindAll_FullName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = ProfileQueries.fullName();

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .as("When there is a valid profile and matching full name, then it is is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Profiles.valid());
    }

    @Test
    @DisplayName("When there is a valid profile and matching last name, then it is is returned")
    @ValidProfile
    void testFindAll_LastName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = ProfileQueries.lastName();

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .as("When there is a valid profile and matching last name, then it is is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Profiles.valid());
    }

    @Test
    @DisplayName("When there is no profile, then nothing is returned")
    void testFindAll_NoData() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = ProfileQueries.firstName();

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .as("When there is no profile, then nothing is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a valid profile and partial matching name, then it is is returned")
    @ValidProfile
    void testFindAll_PartialName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = ProfileQueries.partialName();

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .as("When there is a valid profile and partial matching name, then it is is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Profiles.valid());
    }

    @Test
    @DisplayName("When there is a valid profile and wrong name, then nothing is returned")
    @ValidProfile
    void testFindAll_WrongName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = ProfileQueries.alternativeFirstName();

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .as("When there is a valid profile and wrong name, then nothing is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
