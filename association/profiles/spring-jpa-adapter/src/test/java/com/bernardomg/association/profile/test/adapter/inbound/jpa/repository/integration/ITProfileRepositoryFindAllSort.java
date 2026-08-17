
package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import java.util.List;

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
import com.bernardomg.association.profile.test.configuration.data.annotation.MultipleProfilesAccents;
import com.bernardomg.association.profile.test.configuration.factory.ProfileQueries;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ProfileRepository - find all - sort - accents")
class ITProfileRepositoryFindAllSort {

    @Autowired
    private ProfileRepository repository;

    @Test
    @DisplayName("With ascending order by first name with accents it returns the ordered data")
    @MultipleProfilesAccents
    void testFindAll_FirstNameAccents_Asc() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileFilter filter;

        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));
        filter = ProfileQueries.empty();

        profiles = repository.findAll(filter, pagination, sorting);

        Assertions.assertThat(profiles)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.list(Profile.class))
            .extracting(profile -> profile.name()
                .fullName())
            .containsExactly("Name á Last name 1", "Name é Last name 2", "Name í Last name 3", "Name ó Last name 4",
                "Name ú Last name 5");
    }

}
