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

package com.bernardomg.association.person.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.association.person.usecase.service.DefaultPersonService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Person service - create")
class TestPersonServiceCreate {

    @Mock
    private PersonRepository     personRepository;

    @InjectMocks
    private DefaultPersonService service;

    public TestPersonServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a person with an active membership, the person is persisted")
    void testCreate_ActiveMembership_PersistedData() {
        final Person person;

        // GIVEN
        person = Persons.membershipActive();

        given(personRepository.findNextNumber()).willReturn(PersonConstants.NUMBER);

        // WHEN
        service.create(person);

        // THEN
        verify(personRepository).save(Persons.membershipActive());
    }

    @Test
    @DisplayName("With a person with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final Person           person;

        // GIVEN
        person = Persons.emptyName();

        // WHEN
        execution = () -> service.create(person);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            FieldFailure.of("name.firstName", "empty", new PersonName("", "")));
    }

    @Test
    @DisplayName("With a person having padding whitespaces in first and last name, these whitespaces are removed and the person is persisted")
    void testCreate_Padded_PersistedData() {
        final Person person;

        // GIVEN
        person = Persons.paddedWithWhitespaces();

        given(personRepository.findNextNumber()).willReturn(PersonConstants.NUMBER);

        // WHEN
        service.create(person);

        // THEN
        verify(personRepository).save(Persons.noMembership());
    }

    @Test
    @DisplayName("With a valid person, the person is persisted")
    void testCreate_PersistedData() {
        final Person person;

        // GIVEN
        person = Persons.toCreate();

        given(personRepository.findNextNumber()).willReturn(PersonConstants.NUMBER);

        // WHEN
        service.create(person);

        // THEN
        verify(personRepository).save(Persons.noMembership());
    }

    @Test
    @DisplayName("With a valid person, the created person is returned")
    void testCreate_ReturnedData() {
        final Person person;
        final Person created;

        // GIVEN
        person = Persons.toCreate();

        given(personRepository.save(Persons.noMembership())).willReturn(Persons.noMembership());
        given(personRepository.findNextNumber()).willReturn(PersonConstants.NUMBER);

        // WHEN
        created = service.create(person);

        // THEN
        Assertions.assertThat(created)
            .as("person")
            .isEqualTo(Persons.noMembership());
    }

}
