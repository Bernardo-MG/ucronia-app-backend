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

import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.config.factory.PersonConstants;
import com.bernardomg.association.person.test.config.factory.Persons;
import com.bernardomg.association.person.usecase.service.DefaultPersonService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Person service - update")
class TestPersonServiceUpdate {

    @Mock
    private PersonRepository     personRepository;

    @InjectMocks
    private DefaultPersonService service;

    public TestPersonServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With a not existing person, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Person           person;
        final ThrowingCallable execution;

        // GIVEN
        person = Persons.nameChange();

        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(person);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingPersonException.class);
    }

    @Test
    @DisplayName("With a person having padding whitespaces in first and last name, these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final Person person;

        // GIVEN
        person = Persons.paddedWithWhitespaces();

        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(person);

        // THEN
        verify(personRepository).save(Persons.valid());
    }

    @Test
    @DisplayName("When updating a person, the change is persisted")
    void testUpdate_PersistedData() {
        final Person person;

        // GIVEN
        person = Persons.nameChange();

        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(person);

        // THEN
        verify(personRepository).save(Persons.nameChange());
    }

    @Test
    @DisplayName("When updating an active person, the change is returned")
    void testUpdate_ReturnedData() {
        final Person person;
        final Person updated;

        // GIVEN
        person = Persons.nameChange();

        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(personRepository.save(Persons.nameChange())).willReturn(Persons.nameChange());

        // WHEN
        updated = service.update(person);

        // THEN
        Assertions.assertThat(updated)
            .as("person")
            .isEqualTo(Persons.nameChange());
    }

}
