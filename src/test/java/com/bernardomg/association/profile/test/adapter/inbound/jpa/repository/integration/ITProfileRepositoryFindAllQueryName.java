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

package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.profile.domain.filter.ProfileQuery;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ProfileRepository - find all - filter by name")
class ITProfileRepositoryFindAllQueryName {

    @Autowired
    private ProfileRepository repository;

    @Test
    @DisplayName("With a valid profile and matching first name, it is is returned")
    @ValidProfile
    void testFindAll_FirstName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ProfileQuery(ProfileConstants.FIRST_NAME);

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Profiles.valid());
    }

    @Test
    @DisplayName("With a valid profile and matching full name, it is is returned")
    @ValidProfile
    void testFindAll_FullName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ProfileQuery(ProfileConstants.FULL_NAME);

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Profiles.valid());
    }

    @Test
    @DisplayName("With a valid profile and matching last name, it is is returned")
    @ValidProfile
    void testFindAll_LastName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ProfileQuery(ProfileConstants.LAST_NAME);

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Profiles.valid());
    }

    @Test
    @DisplayName("With no profile, nothing is returned")
    void testFindAll_NoData() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ProfileQuery(ProfileConstants.FIRST_NAME);

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a valid profile and partial matching name, it is is returned")
    @ValidProfile
    void testFindAll_PartialName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ProfileQuery(ProfileConstants.FIRST_NAME.substring(0, ProfileConstants.FIRST_NAME.length() - 2));

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Profiles.valid());
    }

    @Test
    @DisplayName("With a valid profile and wrong name, nothing is returned")
    @ValidProfile
    void testFindAll_WrongName() {
        final Page<Profile> profiles;
        final Pagination    pagination;
        final Sorting       sorting;
        final ProfileQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ProfileQuery(ProfileConstants.ALTERNATIVE_FIRST_NAME);

        // WHEN
        profiles = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(profiles)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
