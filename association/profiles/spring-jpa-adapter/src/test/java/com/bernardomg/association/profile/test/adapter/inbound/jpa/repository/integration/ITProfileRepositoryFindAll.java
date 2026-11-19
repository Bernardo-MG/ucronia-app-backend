
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
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ProfileWithEmail;
import com.bernardomg.association.profile.test.configuration.data.annotation.ProfileWithType;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.ProfileQueries;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ProfileRepository - findAll")
class ITProfileRepositoryFindAll {

    @Autowired
    private ProfileRepository repository;

    @Test
    @DisplayName("When there is a profile, then it is returned")
    @ValidProfile
    void testFindAll() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = ProfileQueries.empty();

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .as("When there is a profile, then it is returned")
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
        filter = ProfileQueries.empty();

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
    @DisplayName("When there is a profile with a contact method, then it is returned")
    @EmailContactMethod
    @ProfileWithEmail
    void testFindAll_WithContactMethods() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = ProfileQueries.empty();

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .as("When there is a profile with a contact method, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Profiles.withEmail());
    }

    @Test
    @DisplayName("When there is a profile with type, then it is returned")
    @ProfileWithType
    void testFindAll_WithType() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = ProfileQueries.empty();

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .as("When there is a profile with type, then it is returned")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Profiles.withType(ProfileConstants.TYPE_MEMBER));
    }

}
