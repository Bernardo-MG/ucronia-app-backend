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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.test.configuration.data.annotation.AlternativePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithPerson;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("UserPersonRepository - find all not assigned")
class ITUserPersonRepositoryFindAllNotAssigned {

    @Autowired
    private UserPersonRepository repository;

    @Test
    @DisplayName("When the member is assigned, it is not returned")
    @ValidUserWithPerson
    void testFindAllNotAssigned_Assigned() {
        final Iterable<Person> persons;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        persons = repository.findAllNotAssigned(pagination, sorting);

        // THEN
        Assertions.assertThat(persons)
            .isEmpty();
    }

    @Test
    @DisplayName("When the member is assigned and there is another not assigned, the one not assigned is returned")
    @ValidUserWithPerson
    @AlternativePerson
    void testFindAllNotAssigned_AssignedAndNotAssigned() {
        final Iterable<Person> persons;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        persons = repository.findAllNotAssigned(pagination, sorting);

        // THEN
        Assertions.assertThat(persons)
            .containsExactly(Persons.alternative());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindAllNotAssigned_NoData() {
        final Iterable<Person> persons;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        persons = repository.findAllNotAssigned(pagination, sorting);

        // THEN
        Assertions.assertThat(persons)
            .isEmpty();
    }

    @Test
    @DisplayName("When there an active member available, it is returned")
    @ValidUser
    @NoMembershipPerson
    void testFindAllNotAssigned_NotAssigned() {
        final Iterable<Person> persons;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 20);
        sorting = new Sorting(List.of());

        // WHEN
        persons = repository.findAllNotAssigned(pagination, sorting);

        // THEN
        Assertions.assertThat(persons)
            .containsExactly(Persons.noMembership());
    }

}
