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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActiveContact;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactiveContact;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipContact;
import com.bernardomg.association.person.test.configuration.data.annotation.WithContact;
import com.bernardomg.association.person.test.configuration.factory.ContactConstants;
import com.bernardomg.association.person.test.configuration.factory.Contacts;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ContactRepository - find one")
class ITContactRepositoryFindOne {

    @Autowired
    private ContactRepository repository;

    @Test
    @DisplayName("With a person, it is returned")
    @NoMembershipContact
    void testFindOne() {
        final Optional<Contact> contact;

        // WHEN
        contact = repository.findOne(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .contains(Contacts.noMembership());
    }

    @Test
    @DisplayName("With no person, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Contact> contact;

        // WHEN
        contact = repository.findOne(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .isEmpty();
    }

    @Test
    @DisplayName("With a person having a contact, it is returned")
    @EmailContactMethod
    @WithContact
    void testFindOne_WithContact() {
        final Optional<Contact> contact;

        // WHEN
        contact = repository.findOne(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .contains(Contacts.withEmail());
    }

    @Test
    @DisplayName("With a person having an active membership, it is returned")
    @MembershipActiveContact
    void testFindOne_WithMembership_Active() {
        final Optional<Contact> contact;

        // WHEN
        contact = repository.findOne(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .contains(Contacts.membershipActive());
    }

    @Test
    @DisplayName("With a person having an inactive membership, it is returned")
    @MembershipInactiveContact
    void testFindOne_WithMembership_Inactive() {
        final Optional<Contact> contact;

        // WHEN
        contact = repository.findOne(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .contains(Contacts.membershipInactive());
    }

    @Test
    @DisplayName("With a person having without membership, it is returned")
    @NoMembershipContact
    void testFindOne_WithoutMembership() {
        final Optional<Contact> contact;

        // WHEN
        contact = repository.findOne(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contact)
            .contains(Contacts.noMembership());
    }

}
