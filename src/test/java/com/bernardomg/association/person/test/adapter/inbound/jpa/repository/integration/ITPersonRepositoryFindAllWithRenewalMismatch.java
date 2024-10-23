/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActiveToNotRenewPerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActiveToRenewPerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactiveToNotRenewPerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactiveToRenewPerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersonRepository - find all to renew")
class ITPersonRepositoryFindAllWithRenewalMismatch {

    @Autowired
    private PersonRepository repository;

    @Test
    @DisplayName("With no membership, nothing is returned")
    @NoMembershipPerson
    void testFindAllWithRenewalMismatch_NoMembership() {
        final Collection<Person> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .isEmpty();
    }

    @Test
    @DisplayName("With an active membership to not renew, it is returned")
    @MembershipActiveToNotRenewPerson
    void testFindAllWithRenewalMismatch_ToNotRenewActive() {
        final Collection<Person> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .containsExactly(Persons.membershipActiveNoRenew());
    }

    @Test
    @DisplayName("With an inactive membership to not renew, nothing is returned")
    @MembershipInactiveToNotRenewPerson
    void testFindAllWithRenewalMismatch_ToNotRenewInactive() {
        final Collection<Person> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .isEmpty();
    }

    @Test
    @DisplayName("With an active membership to renew, nothing is returned")
    @MembershipActiveToRenewPerson
    void testFindAllWithRenewalMismatch_ToRenewActive() {
        final Collection<Person> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .isEmpty();
    }

    @Test
    @DisplayName("With an inactive membership to renew, it is returned")
    @MembershipInactiveToRenewPerson
    void testFindAllWithRenewalMismatch_ToRenewInactive() {
        final Collection<Person> persons;

        // WHEN
        persons = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .containsExactly(Persons.membershipInactive());
    }

}
