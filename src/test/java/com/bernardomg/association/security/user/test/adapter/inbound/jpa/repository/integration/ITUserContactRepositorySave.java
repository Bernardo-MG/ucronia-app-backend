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

package com.bernardomg.association.security.user.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.test.configuration.data.annotation.NoMembershipContact;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.Contacts;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserContactEntity;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserContactSpringRepository;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithContact;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("UserContactRepository - save")
class ITUserContactRepositorySave {

    @Autowired
    private UserContactRepository       repository;

    @Autowired
    private UserContactSpringRepository userContactSpringRepository;

    @Test
    @DisplayName("When the data already exists, the relationship is persisted")
    @ValidUserWithContact
    void testSave_Existing_PersistedData() {
        final Collection<UserContactEntity> contacts;

        // WHEN
        repository.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        contacts = userContactSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserContactEntity contact;

            softly.assertThat(contacts)
                .as("contacts")
                .hasSize(1);

            contact = contacts.iterator()
                .next();
            softly.assertThat(contact.getUserId())
                .as("user id")
                .isNotNull();
            softly.assertThat(contact.getContact()
                .getNumber())
                .as("contact number")
                .isEqualTo(ContactConstants.NUMBER);
            softly.assertThat(contact.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUserWithContact
    void testSave_Existing_ReturnedData() {
        final Contact contact;

        // WHEN
        contact = repository.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .isEqualTo(Contacts.noMembership());
    }

    @Test
    @DisplayName("When the contact is missing, nothing is returned")
    @ValidUser
    void testSave_MissingContact_ReturnedData() {
        final Contact contact;

        // WHEN
        contact = repository.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .isNull();
    }

    @Test
    @DisplayName("When the user is missing, nothing is returned")
    @NoMembershipContact
    void testSave_MissingUser_ReturnedData() {
        final Contact contact;

        // WHEN
        contact = repository.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .isNull();
    }

    @Test
    @DisplayName("With valid data, the relationship is persisted")
    @ValidUser
    @NoMembershipContact
    void testSave_PersistedData() {
        final Collection<UserContactEntity> contacts;

        // WHEN
        repository.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        contacts = userContactSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserContactEntity contact;

            softly.assertThat(contacts)
                .as("contacts")
                .hasSize(1);

            contact = contacts.iterator()
                .next();
            softly.assertThat(contact.getUserId())
                .as("user id")
                .isNotNull();
            softly.assertThat(contact.getContact()
                .getNumber())
                .as("contact number")
                .isEqualTo(ContactConstants.NUMBER);
            softly.assertThat(contact.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUser
    @NoMembershipContact
    void testSave_ReturnedData() {
        final Contact contact;

        // WHEN
        contact = repository.assignContact(UserConstants.USERNAME, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .isEqualTo(Contacts.noMembership());
    }

}
