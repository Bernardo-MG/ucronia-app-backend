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

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActiveToNotRenewContact;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActiveToRenewContact;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactiveToNotRenewContact;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactiveToRenewContact;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipContact;
import com.bernardomg.association.person.test.configuration.factory.Contacts;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ContactRepository - find all to renew")
class ITContactRepositoryFindAllWithRenewalMismatch {

    @Autowired
    private ContactRepository repository;

    @Test
    @DisplayName("With no membership, nothing is returned")
    @NoMembershipContact
    void testFindAllWithRenewalMismatch_NoMembership() {
        final Collection<Contact> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .isEmpty();
    }

    @Test
    @DisplayName("With an active membership to not renew, it is returned")
    @MembershipActiveToNotRenewContact
    void testFindAllWithRenewalMismatch_ToNotRenewActive() {
        final Collection<Contact> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .containsExactly(Contacts.membershipActiveNoRenew());
    }

    @Test
    @DisplayName("With an inactive membership to not renew, nothing is returned")
    @MembershipInactiveToNotRenewContact
    void testFindAllWithRenewalMismatch_ToNotRenewInactive() {
        final Collection<Contact> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .isEmpty();
    }

    @Test
    @DisplayName("With an active membership to renew, nothing is returned")
    @MembershipActiveToRenewContact
    void testFindAllWithRenewalMismatch_ToRenewActive() {
        final Collection<Contact> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .isEmpty();
    }

    @Test
    @DisplayName("With an inactive membership to renew, it is returned")
    @MembershipInactiveToRenewContact
    void testFindAllWithRenewalMismatch_ToRenewInactive() {
        final Collection<Contact> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .containsExactly(Contacts.membershipInactive());
    }

}
