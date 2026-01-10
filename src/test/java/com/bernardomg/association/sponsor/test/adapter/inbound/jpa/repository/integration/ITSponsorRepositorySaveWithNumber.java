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

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.SponsorSpringRepository;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.factory.SponsorEntities;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SponsorRepository - save with number")
class ITSponsorRepositorySaveWithNumber {

    @Autowired
    private ProfileSpringRepository profileSpringRepository;

    @Autowired
    private SponsorRepository       repository;

    @Autowired
    private SponsorSpringRepository springRepository;

    public ITSponsorRepositorySaveWithNumber() {
        super();
    }

    @Test
    @DisplayName("With an active sponsor, the sponsor is persisted")
    @ValidProfile
    @EmailContactMethod
    void testSaveWithNumber_PersistedData() {
        final Sponsor                 sponsor;
        final Iterable<SponsorEntity> entities;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        repository.save(sponsor, ProfileConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(SponsorEntities.created());
    }

    @Test
    @DisplayName("With an active sponsor, the created sponsor is returned")
    @ValidProfile
    @EmailContactMethod
    void testSaveWithNumber_ReturnedData() {
        final Sponsor sponsor;
        final Sponsor saved;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        saved = repository.save(sponsor, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(saved)
            .as("sponsor")
            .isEqualTo(Sponsors.noContactChannel());
    }

    @Test
    @DisplayName("When the sponsor is persisted, the profile types includes the sponsor type")
    @EmailContactMethod
    void testSaveWithNumber_SetsType() {
        final Sponsor       sponsor;
        final Sponsor       saved;
        final ProfileEntity profile;

        // GIVEN
        sponsor = Sponsors.valid();

        // WHEN
        saved = repository.save(sponsor, ProfileConstants.NUMBER);

        // THEN
        profile = profileSpringRepository.findByNumber(saved.number())
            .get();

        Assertions.assertThat(profile)
            .as("profile")
            .extracting(ProfileEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(SponsorEntityConstants.PROFILE_TYPE);
    }

}
