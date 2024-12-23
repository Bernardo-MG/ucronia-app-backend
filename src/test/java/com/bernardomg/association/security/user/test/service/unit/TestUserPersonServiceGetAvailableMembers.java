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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.usecase.service.DefaultUserPersonService;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.user.data.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("User person service - get person")
class TestUserPersonServiceGetAvailablePersons {

    @Mock
    private PersonRepository         personRepository;

    @InjectMocks
    private DefaultUserPersonService service;

    @Mock
    private UserPersonRepository     userPersonRepository;

    @Mock
    private UserRepository           userRepository;

    @Test
    @DisplayName("When there are not assigned persons, these are returned")
    void testGetPerson() {
        final Iterable<Person> persons;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = Sorting.unsorted();

        given(userPersonRepository.findAllNotAssigned(pagination, sorting)).willReturn(List.of(Persons.noMembership()));

        // WHEN
        persons = service.getAvailablePerson(pagination, sorting);

        // THEN
        Assertions.assertThat(persons)
            .containsExactly(Persons.noMembership());
    }

    @Test
    @DisplayName("When there are no not assigned persons, nothing is returned")
    void testGetPerson_NoPerson() {
        final Iterable<Person> persons;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = Sorting.unsorted();

        given(userPersonRepository.findAllNotAssigned(pagination, sorting)).willReturn(List.of());

        // WHEN
        persons = service.getAvailablePerson(pagination, sorting);

        // THEN
        Assertions.assertThat(persons)
            .isEmpty();
    }

}
