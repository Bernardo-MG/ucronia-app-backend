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

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.SinglePerson;
import com.bernardomg.association.person.test.configuration.factory.PersonEntities;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersonRepository - save")
class ITPersonRepositorySave {

    @Autowired
    private PersonRepository       personRepository;

    @Autowired
    private PersonSpringRepository repository;

    public ITPersonRepositorySave() {
        super();
    }

    @Test
    @DisplayName("When a person exists, the person is persisted")
    @SinglePerson
    void testSave_Existing_PersistedData() {
        final Person                 person;
        final Iterable<PersonEntity> entities;

        // GIVEN
        person = Persons.valid();

        // WHEN
        personRepository.save(person);

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(PersonEntities.valid());
    }

    @Test
    @DisplayName("When a person exists, the created person is returned")
    @SinglePerson
    void testSave_Existing_ReturnedData() {
        final Person person;
        final Person saved;

        // GIVEN
        person = Persons.valid();

        // WHEN
        saved = personRepository.save(person);

        // THEN
        Assertions.assertThat(saved)
            .as("person")
            .isEqualTo(Persons.valid());
    }

    @Test
    @DisplayName("With a valid person, the person is persisted")
    void testSave_PersistedData() {
        final Person                 person;
        final Iterable<PersonEntity> entities;

        // GIVEN
        person = Persons.valid();

        // WHEN
        personRepository.save(person);

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(PersonEntities.valid());
    }

    @Test
    @DisplayName("With a valid person, the created person is returned")
    void testSave_ReturnedData() {
        final Person person;
        final Person saved;

        // GIVEN
        person = Persons.valid();

        // WHEN
        saved = personRepository.save(person);

        // THEN
        Assertions.assertThat(saved)
            .as("person")
            .isEqualTo(Persons.valid());
    }

}
