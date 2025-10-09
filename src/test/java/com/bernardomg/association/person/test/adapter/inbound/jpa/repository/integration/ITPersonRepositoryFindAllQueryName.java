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

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.person.domain.filter.PersonFilter;
import com.bernardomg.association.person.domain.filter.PersonFilter.PersonStatus;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersonRepository - find all - filter by name")
class ITPersonRepositoryFindAllQueryName {

    @Autowired
    private PersonRepository repository;

    @Test
    @DisplayName("With no person, nothing is returned")
    void testFindAll_NoData() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL_MEMBER, PersonConstants.FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a person having an active membership and matching first name, it is returned")
    @MembershipActivePerson
    void testFindAll_WithMembership_Active_FirstName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.membershipActive());
    }

    @Test
    @DisplayName("With a person having an active membership and matching full name, it is returned")
    @MembershipActivePerson
    void testFindAll_WithMembership_Active_FullName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.FULL_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.membershipActive());
    }

    @Test
    @DisplayName("With a person having an active membership and matching last name, it is returned")
    @MembershipActivePerson
    void testFindAll_WithMembership_Active_LastName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.LAST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.membershipActive());
    }

    @Test
    @DisplayName("With a person having an active membership and partial matching name, it is returned")
    @MembershipActivePerson
    void testFindAll_WithMembership_Active_PartialName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL,
            PersonConstants.FIRST_NAME.substring(0, PersonConstants.FIRST_NAME.length() - 2));

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.membershipActive());
    }

    @Test
    @DisplayName("With a person having an active membership and wrong name, nothing is returned")
    @MembershipActivePerson
    void testFindAll_WithMembership_Active_WrongName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.ALTERNATIVE_FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a person having an inactive membership and matching first name, it is is returned")
    @MembershipInactivePerson
    void testFindAll_WithMembership_Inactive_FirstName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.membershipInactive());
    }

    @Test
    @DisplayName("With a person having an inactive membership and matching full name, it is is returned")
    @MembershipInactivePerson
    void testFindAll_WithMembership_Inactive_FullName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.FULL_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.membershipInactive());
    }

    @Test
    @DisplayName("With a person having an inactive membership and matching last name, it is is returned")
    @MembershipInactivePerson
    void testFindAll_WithMembership_Inactive_LastName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.LAST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.membershipInactive());
    }

    @Test
    @DisplayName("With a person having an inactive membership and partial matching name, it is is returned")
    @MembershipInactivePerson
    void testFindAll_WithMembership_Inactive_PartialName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL,
            PersonConstants.FIRST_NAME.substring(0, PersonConstants.FIRST_NAME.length() - 2));

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.membershipInactive());
    }

    @Test
    @DisplayName("With a person having an inactive membership and wrong name, nothing is returned")
    @MembershipInactivePerson
    void testFindAll_WithMembership_Inactive_WrongName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.ALTERNATIVE_FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a person without membership and matching first name, it is is returned")
    @NoMembershipPerson
    void testFindAll_WithoutMembership_FirstName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.noMembership());
    }

    @Test
    @DisplayName("With a person without membership and matching full name, it is is returned")
    @NoMembershipPerson
    void testFindAll_WithoutMembership_FullName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.FULL_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.noMembership());
    }

    @Test
    @DisplayName("With a person without membership and matching last name, it is is returned")
    @NoMembershipPerson
    void testFindAll_WithoutMembership_LastName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.LAST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.noMembership());
    }

    @Test
    @DisplayName("With a person without membership and partial matching name, it is is returned")
    @NoMembershipPerson
    void testFindAll_WithoutMembership_PartialName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL,
            PersonConstants.FIRST_NAME.substring(0, PersonConstants.FIRST_NAME.length() - 2));

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Persons.noMembership());
    }

    @Test
    @DisplayName("With a person without membership and wrong name, nothing is returned")
    @MembershipInactivePerson
    void testFindAll_WithoutMembership_WrongName() {
        final Page<Person> people;
        final Pagination   pagination;
        final Sorting      sorting;
        final PersonFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new PersonFilter(PersonStatus.ALL, PersonConstants.ALTERNATIVE_FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
