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

import java.util.Collection;
import java.util.List;

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
import com.bernardomg.association.guest.test.configuration.data.annotation.ValidGuest;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.test.configuration.factory.QueryGuestEntities;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("GuestRepository - save all")
class ITGuestRepositorySaveAll {

    @Autowired
    private ProfileSpringRepository    profileSpringRepository;

    @Autowired
    private GuestRepository            repository;

    @Autowired
    private QueryGuestSpringRepository springRepository;

    public ITGuestRepositorySaveAll() {
        super();
    }

    @Test
    @DisplayName("When a guest exists, the profile is persisted")
    @ValidGuest
    void testSaveAll_Existing_PersistedData() {
        final Guest                      guest;
        final Iterable<QueryGuestEntity> entities;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        repository.saveAll(List.of(guest));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id",
                "contactChannels.profileId", "contactChannels.profile")
            .containsExactly(QueryGuestEntities.withEmail());
    }

    @Test
    @DisplayName("With a valid guest, the guest is persisted")
    @EmailContactMethod
    void testSaveAll_PersistedData() {
        final Guest                      guest;
        final Iterable<QueryGuestEntity> entities;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        repository.saveAll(List.of(guest));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id",
                "contactChannels.profileId", "contactChannels.profile")
            .containsExactly(QueryGuestEntities.withEmail());
    }

    @Test
    @DisplayName("When the type is removed, the guest is not changed")
    @ValidGuest
    void testSaveAll_RemoveType_NoChange() {
        final Guest                      guest;
        final Iterable<QueryGuestEntity> entities;

        // GIVEN
        guest = Guests.withoutType();

        // WHEN
        repository.saveAll(List.of(guest));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id",
                "contactChannels.profileId", "contactChannels.profile")
            .containsExactly(QueryGuestEntities.withEmail());
    }

    @Test
    @DisplayName("With a valid guest, the created guest is returned")
    @EmailContactMethod
    void testSaveAll_ReturnedData() {
        final Guest             guest;
        final Collection<Guest> saved;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        saved = repository.saveAll(List.of(guest));

        // THEN
        Assertions.assertThat(saved)
            .as("guest")
            .containsExactly(Guests.created());
    }

    @Test
    @DisplayName("When the guest is persisted, the profile types includes the guest type")
    @EmailContactMethod
    void testSaveAll_SetsType() {
        final Guest         guest;
        final ProfileEntity profile;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        repository.saveAll(List.of(guest));

        // THEN
        profile = profileSpringRepository.findByNumber(1L)
            .get();

        Assertions.assertThat(profile)
            .as("profile")
            .extracting(ProfileEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(GuestEntityConstants.PROFILE_TYPE);
    }

}
