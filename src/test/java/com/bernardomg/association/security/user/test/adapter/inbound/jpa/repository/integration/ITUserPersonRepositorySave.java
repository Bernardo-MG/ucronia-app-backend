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

package com.bernardomg.association.security.user.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.test.configuration.data.annotation.ValidPerson;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserPersonEntity;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserPersonSpringRepository;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithPerson;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("UserPersonRepository - save")
class ITUserPersonRepositorySave {

    @Autowired
    private UserPersonRepository       repository;

    @Autowired
    private UserPersonSpringRepository userPersonSpringRepository;

    @Test
    @DisplayName("When the data already exists, the relationship is persisted")
    @ValidUserWithPerson
    void testSave_Existing_PersistedData() {
        final Collection<UserPersonEntity> persons;

        // WHEN
        repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        persons = userPersonSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserPersonEntity person;

            softly.assertThat(persons)
                .as("persons")
                .hasSize(1);

            person = persons.iterator()
                .next();
            softly.assertThat(person.getUserId())
                .as("user id")
                .isNotNull();
            softly.assertThat(person.getPerson()
                .getNumber())
                .as("person number")
                .isEqualTo(PersonConstants.NUMBER);
            softly.assertThat(person.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUserWithPerson
    void testSave_Existing_ReturnedData() {
        final Person person;

        // WHEN
        person = repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(person)
            .isEqualTo(Persons.valid());
    }

    @Test
    @DisplayName("When the person is missing, nothing is returned")
    @ValidUser
    void testSave_MissingPerson_ReturnedData() {
        final Person person;

        // WHEN
        person = repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(person)
            .isNull();
    }

    @Test
    @DisplayName("When the user is missing, nothing is returned")
    @ValidPerson
    void testSave_MissingUser_ReturnedData() {
        final Person person;

        // WHEN
        person = repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(person)
            .isNull();
    }

    @Test
    @DisplayName("With valid data, the relationship is persisted")
    @ValidUser
    @ValidPerson
    void testSave_PersistedData() {
        final Collection<UserPersonEntity> persons;

        // WHEN
        repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        persons = userPersonSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserPersonEntity person;

            softly.assertThat(persons)
                .as("persons")
                .hasSize(1);

            person = persons.iterator()
                .next();
            softly.assertThat(person.getUserId())
                .as("user id")
                .isNotNull();
            softly.assertThat(person.getPerson()
                .getNumber())
                .as("person number")
                .isEqualTo(PersonConstants.NUMBER);
            softly.assertThat(person.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUser
    @ValidPerson
    void testSave_ReturnedData() {
        final Person person;

        // WHEN
        person = repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(person)
            .isEqualTo(Persons.valid());
    }

}
