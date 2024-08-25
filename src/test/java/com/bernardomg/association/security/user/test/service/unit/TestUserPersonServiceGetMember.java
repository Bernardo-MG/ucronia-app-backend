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

package com.bernardomg.association.security.user.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.config.factory.Persons;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.test.config.factory.UserConstants;
import com.bernardomg.association.security.user.test.config.factory.Users;
import com.bernardomg.association.security.user.usecase.service.DefaultUserPersonService;
import com.bernardomg.security.user.data.domain.exception.MissingUserException;
import com.bernardomg.security.user.data.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("User person service - get person")
class TestUserPersonServiceGetPerson {

    @Mock
    private PersonRepository         personRepository;

    @InjectMocks
    private DefaultUserPersonService service;

    @Mock
    private UserPersonRepository     userPersonRepository;

    @Mock
    private UserRepository           userRepository;

    @Test
    @DisplayName("With a person assigned to the user, it returns the user")
    void testGetPerson() {
        final Optional<Person> person;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(userPersonRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.of(Persons.valid()));

        // WHEN
        person = service.getPerson(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(person)
            .contains(Persons.valid());
    }

    @Test
    @DisplayName("With no person, it throws an exception")
    void testGetPerson_NoPerson() {
        final ThrowingCallable execution;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getPerson(UserConstants.USERNAME);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingUserException.class);
    }

}
