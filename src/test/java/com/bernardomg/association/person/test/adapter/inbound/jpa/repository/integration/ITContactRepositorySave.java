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

package com.bernardomg.association.person.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActiveContact;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipContact;
import com.bernardomg.association.person.test.configuration.data.annotation.WithContact;
import com.bernardomg.association.person.test.configuration.factory.ContactEntities;
import com.bernardomg.association.person.test.configuration.factory.Contacts;
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
    @DisplayName("When a person exists with an active membership, and the membership is removed, the person is persisted")
    @MembershipActiveContact
    void testSave_Existing_Active_RemoveMembership_PersistedData() {
        final Contact                 person;
        final Iterable<ContactEntity> entities;

        // GIVEN
        person = Contacts.noMembership();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "membership.person")
            .containsExactly(ContactEntities.noMembership());
    }

    @Test
    @DisplayName("When a person exists with an active membership, and an inactive membership is set, the person is persisted")
    @MembershipActiveContact
    void testSave_Existing_Active_SetInactiveMembership_PersistedData() {
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "membership.person")
            .containsExactly(ContactEntities.membershipInactive());
    }

    @Test
    @DisplayName("When a person exists, and an active membership is added, the person is persisted")
    @NoMembershipContact
    void testSave_Existing_AddActiveMembership_PersistedData() {
        final Contact                 person;
        final Iterable<ContactEntity> entities;

        // GIVEN
        person = Contacts.membershipActive();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.membershipActive());
    }

    @Test
    @DisplayName("When a person exists and a contact is added, the person is persisted")
    @EmailContactMethod
    @NoMembershipContact
    void testSave_Existing_AddContact_PersistedData() {
        final Contact                 person;
        final Iterable<ContactEntity> entities;

        // GIVEN
        person = Contacts.withEmail();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.withEmail());
    }

    @Test
    @DisplayName("When a person exists, and an inactive membership is added, the person is persisted")
    @NoMembershipContact
    void testSave_Existing_AddInactiveMembership_PersistedData() {
        final Contact                 person;
        final Iterable<ContactEntity> entities;

        // GIVEN
        person = Contacts.membershipActive();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.membershipActive());
    }

    @Test
    @DisplayName("When a person exists, the person is persisted")
    @NoMembershipContact
    void testSave_Existing_PersistedData() {
        final Contact                 person;
        final Iterable<ContactEntity> entities;

        // GIVEN
        person = Contacts.noMembership();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.noMembership());
    }

    @Test
    @DisplayName("When a person exists and a contact is remove, the person is persisted")
    @EmailContactMethod
    @WithContact
    void testSave_Existing_RemoveContact_PersistedData() {
        final Contact                 person;
        final Iterable<ContactEntity> entities;

        // GIVEN
        person = Contacts.noMembership();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.noMembership());
    }

    @Test
    @DisplayName("When a person exists, the created person is returned")
    @NoMembershipContact
    void testSave_Existing_ReturnedData() {
        final Contact person;
        final Contact saved;

        // GIVEN
        person = Contacts.noMembership();

        // WHEN
        saved = repository.save(person);

        // THEN
        Assertions.assertThat(saved)
            .as("person")
            .isEqualTo(Contacts.noMembership());
    }

    @Test
    @DisplayName("With a person with an inactive membership, the person is persisted")
    void testSave_InactiveMembership_PersistedData() {
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
    @DisplayName("With a valid person, the person is persisted")
    void testSave_PersistedData() {
        final Contact                 person;
        final Iterable<ContactEntity> entities;

        // GIVEN
        person = Contacts.noMembership();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactEntities.noMembership());
    }

    @Test
    @DisplayName("With a valid person, the created person is returned")
    void testSave_ReturnedData() {
        final Contact person;
        final Contact saved;

        // GIVEN
        person = Contacts.noMembership();

        // WHEN
        saved = repository.save(person);

        // THEN
        Assertions.assertThat(saved)
            .as("person")
            .isEqualTo(Contacts.noMembership());
    }

    @Test
    @DisplayName("With a person with a contact method, the person is persisted")
    @EmailContactMethod
    void testSave_WithContact_PersistedData() {
        final Contact                 person;
        final Iterable<ContactEntity> entities;

        // GIVEN
        person = Contacts.withEmail();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contacts.person")
            .containsExactly(ContactEntities.withEmail());
    }

    @Test
    @DisplayName("With a person with a contact method, the person is returned")
    @EmailContactMethod
    void testSave_WithContact_ReturnedData() {
        final Contact person;
        final Contact saved;

        // GIVEN
        person = Contacts.withEmail();

        // WHEN
        saved = repository.save(person);

        // THEN
        Assertions.assertThat(saved)
            .as("person")
            .isEqualTo(Contacts.withEmail());
    }

}
