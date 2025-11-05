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

package com.bernardomg.association.contact.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.contact.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.contact.test.configuration.data.annotation.ValidContact;
import com.bernardomg.association.contact.test.configuration.data.annotation.WithContactChannel;
import com.bernardomg.association.contact.test.configuration.factory.ContactEntities;
import com.bernardomg.association.contact.test.configuration.factory.Contacts;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ContactRepository - save")
class ITContactRepositorySave {

    @Autowired
    private ContactRepository       repository;

    @Autowired
    private ContactSpringRepository springRepository;

    public ITContactRepositorySave() {
        super();
    }

    @Test
    @DisplayName("When a contact exists and a contact is added, the contact is persisted")
    @EmailContactMethod
    @ValidContact
    void testSave_Existing_AddContact_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.withEmail();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.withEmail());
    }

    @Test
    @DisplayName("When a contact exists, the contact is persisted")
    @ValidContact
    void testSave_Existing_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.valid();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.minimal());
    }

    @Test
    @DisplayName("When a contact exists and a contact is remove, the contact is persisted")
    @EmailContactMethod
    @WithContactChannel
    void testSave_Existing_RemoveContact_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.valid();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.minimal());
    }

    @Test
    @DisplayName("When a contact exists, the created contact is returned")
    @ValidContact
    void testSave_Existing_ReturnedData() {
        final Contact contact;
        final Contact saved;

        // GIVEN
        contact = Contacts.valid();

        // WHEN
        saved = repository.save(contact);

        // THEN
        Assertions.assertThat(saved)
            .as("contact")
            .isEqualTo(Contacts.valid());
    }

    @Test
    @DisplayName("With a valid contact, the contact is persisted")
    void testSave_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.valid();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.minimal());
    }

    @Test
    @DisplayName("With a valid contact, the created contact is returned")
    void testSave_ReturnedData() {
        final Contact contact;
        final Contact saved;

        // GIVEN
        contact = Contacts.valid();

        // WHEN
        saved = repository.save(contact);

        // THEN
        Assertions.assertThat(saved)
            .as("contact")
            .isEqualTo(Contacts.valid());
    }

    @Test
    @DisplayName("With a contact with a contact method, the contact is returned")
    @EmailContactMethod
    void testSave_WithContactChannel_ReturnedData() {
        final Contact contact;
        final Contact saved;

        // GIVEN
        contact = Contacts.withEmail();

        // WHEN
        saved = repository.save(contact);

        // THEN
        Assertions.assertThat(saved)
            .as("contact")
            .isEqualTo(Contacts.withEmail());
    }

    @Test
    @DisplayName("With a contact with a contact method, the contact is persisted")
    @EmailContactMethod
    void testSave_WithContactChannelChannel_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.withEmail();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.contact",
                "contactChannels.contactMethod")
            .containsExactly(ContactEntities.withEmail());
    }

}
