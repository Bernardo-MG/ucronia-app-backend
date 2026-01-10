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

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.SponsorSpringRepository;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.ValidSponsor;
import com.bernardomg.association.sponsor.test.configuration.factory.SponsorEntities;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SponsorRepository - save all")
class ITSponsorRepositorySaveAll {

    @Autowired
    private ProfileSpringRepository profileSpringRepository;

    @Autowired
    private SponsorRepository       repository;

    @Autowired
    private SponsorSpringRepository springRepository;

    public ITSponsorRepositorySaveAll() {
        super();
    }

    @Test
    @DisplayName("When a sponsor exists, the contact is persisted")
    @ValidSponsor
    void testSaveAll_Existing_PersistedData() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        repository.saveAll(List.of(sponsor));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.contactChannels.id",
                "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(SponsorEntities.createdWithEmail());
    }

    @Test
    @DisplayName("With a valid sponsor, the sponsor is persisted")
    @EmailContactMethod
    void testSaveAll_PersistedData() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        repository.saveAll(List.of(sponsor));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(SponsorEntities.createdWithEmail());
    }

    @Test
    @DisplayName("When the type is removed, the sponsor is not changed")
    @ValidSponsor
    void testSaveAll_RemoveType_NoChange() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.withoutType();

        // WHEN
        repository.saveAll(List.of(sponsor));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.contactChannels.id",
                "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(SponsorEntities.createdWithEmail());
    }

    @Test
    @DisplayName("With a valid sponsor, the created sponsor is returned")
    @EmailContactMethod
    void testSaveAll_ReturnedData() {
        final Sponsor             sponsor;
        final Collection<Sponsor> saved;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        saved = repository.saveAll(List.of(sponsor));

        // THEN
        Assertions.assertThat(saved)
            .as("sponsor")
            .containsExactly(Sponsors.created());
    }

    @Test
    @DisplayName("When the sponsor is persisted, the profile types includes the sponsor type")
    @EmailContactMethod
    void testSaveAll_SetsType() {
        final Sponsor       sponsor;
        final ProfileEntity profile;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        repository.saveAll(List.of(sponsor));

        // THEN
        profile = profileSpringRepository.findByNumber(1L)
            .get();

        Assertions.assertThat(profile)
            .as("profile")
            .extracting(ProfileEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(SponsorEntityConstants.PROFILE_TYPE);
    }

}
