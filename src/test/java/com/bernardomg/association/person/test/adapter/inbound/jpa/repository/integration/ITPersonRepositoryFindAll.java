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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.SinglePerson;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersonRepository - find all")
class ITPersonRepositoryFindAll {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("With a person, it is returned")
    @SinglePerson
    void testFindAll() {
        final Iterable<Person> people;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        people = personRepository.findAll(pageable);

        // THEN
        Assertions.assertThat(people)
            .containsExactly(Persons.valid());
    }

    @Test
    @DisplayName("With no person, nothing is returned")
    void testFindAll_NoData() {
        final Iterable<Person> people;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        people = personRepository.findAll(pageable);

        // THEN
        Assertions.assertThat(people)
            .isEmpty();
    }

    @Test
    @DisplayName("With a person having an active membership, it is returned")
    @MembershipActivePerson
    void testFindAll_WithMembership_Active() {
        final Iterable<Person> people;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        people = personRepository.findAll(pageable);

        // THEN
        Assertions.assertThat(people)
            .containsExactly(Persons.membershipActive());
    }

    @Test
    @DisplayName("With a person having an inactive membership, it is returned")
    @MembershipInactivePerson
    void testFindAll_WithMembership_Inactive() {
        final Iterable<Person> people;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        people = personRepository.findAll(pageable);

        // THEN
        Assertions.assertThat(people)
            .containsExactly(Persons.membershipInactive());
    }

}
