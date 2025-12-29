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

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ProfileWithType;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.ProfileEntities;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ProfileRepository - save")
class ITProfileRepositorySaveAll {

    @Autowired
    private ProfileRepository       repository;

    @Autowired
    private ProfileSpringRepository springRepository;

    public ITProfileRepositorySaveAll() {
        super();
    }

    @Test
    @DisplayName("When a profile exists, the profile is persisted")
    @ValidProfile
    void testSave_Existing_PersistedData() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.valid();

        // WHEN
        repository.saveAll(List.of(profile));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ProfileEntities.valid());
    }

    @Test
    @DisplayName("When a profile exists and a type is removed, the profile is not changed")
    @ProfileWithType
    void testSave_Existing_RemoveContact_NoChange() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.valid();

        // WHEN
        repository.saveAll(List.of(profile));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ProfileEntities.withType(ProfileConstants.TYPE_MEMBER));
    }

    @Test
    @DisplayName("With a valid profile, the profile is persisted")
    void testSave_PersistedData() {
        final Profile                 profile;
        final Iterable<ProfileEntity> entities;

        // GIVEN
        profile = Profiles.valid();

        // WHEN
        repository.saveAll(List.of(profile));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ProfileEntities.valid());
    }

    @Test
    @DisplayName("With a valid profile, the created profile is returned")
    void testSave_ReturnedData() {
        final Profile             profile;
        final Collection<Profile> saved;

        // GIVEN
        profile = Profiles.valid();

        // WHEN
        saved = repository.saveAll(List.of(profile));

        // THEN
        Assertions.assertThat(saved)
            .as("profile")
            .containsExactly(Profiles.created());
    }

}
