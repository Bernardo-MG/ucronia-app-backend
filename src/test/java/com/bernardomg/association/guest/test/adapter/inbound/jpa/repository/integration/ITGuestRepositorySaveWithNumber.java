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

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.contact.test.configuration.data.annotation.ValidContact;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntityConstants;
import com.bernardomg.association.guest.adapter.inbound.jpa.model.QueryGuestEntity;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.QueryGuestSpringRepository;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.factory.GuestEntities;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("GuestRepository - save with number")
class ITGuestRepositorySaveWithNumber {

    @Autowired
    private ContactSpringRepository    contactSpringRepository;

    @Autowired
    private GuestRepository            repository;

    @Autowired
    private QueryGuestSpringRepository springRepository;

    public ITGuestRepositorySaveWithNumber() {
        super();
    }

    @Test
    @DisplayName("With an active guest, the guest is persisted")
    @ValidContact
    void testSaveWithNumber_PersistedData() {
        final Guest                      guest;
        final Iterable<QueryGuestEntity> entities;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        repository.save(guest, ContactConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(GuestEntities.valid());
    }

    @Test
    @DisplayName("With an active guest, the created guest is returned")
    @ValidContact
    void testSaveWithNumber_ReturnedData() {
        final Guest guest;
        final Guest saved;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        saved = repository.save(guest, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(saved)
            .as("guest")
            .isEqualTo(Guests.valid());
    }

    @Test
    @DisplayName("When the guest is persisted, the contact types includes the guest type")
    void testSaveWithNumber_SetsType() {
        final Guest         guest;
        final Guest         saved;
        final ContactEntity contact;

        // GIVEN
        guest = Guests.valid();

        // WHEN
        saved = repository.save(guest, ContactConstants.NUMBER);

        // THEN
        contact = contactSpringRepository.findByNumber(saved.number())
            .get();

        Assertions.assertThat(contact)
            .as("contact")
            .extracting(ContactEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(GuestEntityConstants.CONTACT_TYPE);
    }

}
