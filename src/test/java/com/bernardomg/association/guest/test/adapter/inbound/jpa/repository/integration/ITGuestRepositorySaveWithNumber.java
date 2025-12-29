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

package com.bernardomg.association.guest.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.QueryGuestSpringRepository;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.test.configuration.factory.QueryGuestEntities;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("GuestRepository - save with number")
class ITGuestRepositorySaveWithNumber {

    @Autowired
    private ProfileSpringRepository    profileSpringRepository;

    @Autowired
    private GuestRepository            repository;

    @Autowired
    private QueryGuestSpringRepository springRepository;

    public ITGuestRepositorySaveWithNumber() {
        super();
    }

    @Test
    @DisplayName("With a guest, the guest is persisted")
    @EmailContactMethod
    @ValidProfile
    void testSaveWithNumber_PersistedData() {
        final Guest                      guest;
        final Iterable<QueryGuestEntity> entities;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        repository.save(guest, ProfileConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(QueryGuestEntities.valid());
    }

    @Test
    @DisplayName("With a guest, the created guest is returned")
    @EmailContactMethod
    @ValidProfile
    void testSaveWithNumber_ReturnedData() {
        final Guest guest;
        final Guest saved;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        saved = repository.save(guest, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(saved)
            .as("guest")
            .isEqualTo(Guests.noContactChannel());
    }

    @Test
    @DisplayName("When the guest is persisted, the contact types includes the guest type")
    @EmailContactMethod
    void testSaveWithNumber_SetsType() {
        final Guest         guest;
        final Guest         saved;
        final ProfileEntity profile;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        saved = repository.save(guest, ProfileConstants.NUMBER);

        // THEN
        profile = profileSpringRepository.findByNumber(saved.number())
            .get();

        Assertions.assertThat(profile)
            .as("profile")
            .extracting(ProfileEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(GuestEntityConstants.CONTACT_TYPE);
    }

}
