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
import com.bernardomg.association.contact.test.configuration.data.annotation.MembershipActiveContact;
import com.bernardomg.association.contact.test.configuration.data.annotation.NoMembershipContact;
import com.bernardomg.association.contact.test.configuration.data.annotation.WithContact;
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
    @DisplayName("With a person with an active membership, the person is persisted")
    void testSave_ActiveMembership_PersistedData() {
        final Contact                 person;
        final Iterable<ContactEntity> entities;

        // GIVEN
        person = Contacts.membershipInactive();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.membershipInactive());
    }

    @Test
    @DisplayName("When a contact exists with an active membership, and the membership is removed, the contact is persisted")
    @MembershipActiveContact
    void testSave_Existing_Active_RemoveMembership_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.noMembership();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "membership.contact")
            .containsExactly(ContactEntities.noMembership());
    }

    @Test
    @DisplayName("When a contact exists with an active membership, and an inactive membership is set, the contact is persisted")
    @MembershipActiveContact
    void testSave_Existing_Active_SetInactiveMembership_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.membershipInactive();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "membership.contact")
            .containsExactly(ContactEntities.membershipInactive());
    }

    @Test
    @DisplayName("When a contact exists, and an active membership is added, the contact is persisted")
    @NoMembershipContact
    void testSave_Existing_AddActiveMembership_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.membershipActive();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.membershipActive());
    }

    @Test
    @DisplayName("When a contact exists and a contact is added, the contact is persisted")
    @EmailContactMethod
    @NoMembershipContact
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
    @DisplayName("When a contact exists, and an inactive membership is added, the contact is persisted")
    @NoMembershipContact
    void testSave_Existing_AddInactiveMembership_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.membershipActive();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.membershipActive());
    }

    @Test
    @DisplayName("When a contact exists, the contact is persisted")
    @NoMembershipContact
    void testSave_Existing_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.noMembership();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.noMembership());
    }

    @Test
    @DisplayName("When a contact exists and a contact is remove, the contact is persisted")
    @EmailContactMethod
    @WithContact
    void testSave_Existing_RemoveContact_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.noMembership();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.noMembership());
    }

    @Test
    @DisplayName("When a contact exists, the created contact is returned")
    @NoMembershipContact
    void testSave_Existing_ReturnedData() {
        final Contact contact;
        final Contact saved;

        // GIVEN
        contact = Contacts.noMembership();

        // WHEN
        saved = repository.save(contact);

        // THEN
        Assertions.assertThat(saved)
            .as("contact")
            .isEqualTo(Contacts.noMembership());
    }

    @Test
    @DisplayName("With a contact with an inactive membership, the contact is persisted")
    void testSave_InactiveMembership_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.membershipInactive();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.membershipInactive());
    }

    @Test
    @DisplayName("With a valid contact, the contact is persisted")
    void testSave_PersistedData() {
        final Contact                 contact;
        final Iterable<ContactEntity> entities;

        // GIVEN
        contact = Contacts.noMembership();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.noMembership());
    }

    @Test
    @DisplayName("With a valid contact, the created contact is returned")
    void testSave_ReturnedData() {
        final Contact contact;
        final Contact saved;

        // GIVEN
        contact = Contacts.noMembership();

        // WHEN
        saved = repository.save(contact);

        // THEN
        Assertions.assertThat(saved)
            .as("contact")
            .isEqualTo(Contacts.noMembership());
    }

    @Test
    @DisplayName("With a contact with a contact method, the contact is returned")
    @EmailContactMethod
    void testSave_WithContact_ReturnedData() {
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
    void testSave_WithContactChannel_PersistedData() {
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id")
            .containsExactly(ContactEntities.withEmail());
    }

}
