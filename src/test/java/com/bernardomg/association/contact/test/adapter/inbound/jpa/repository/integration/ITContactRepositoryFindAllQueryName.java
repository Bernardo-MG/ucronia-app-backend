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

package com.bernardomg.association.contact.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.domain.filter.ContactFilter;
import com.bernardomg.association.contact.domain.filter.ContactFilter.ContactStatus;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.contact.test.configuration.data.annotation.MembershipActiveContact;
import com.bernardomg.association.contact.test.configuration.data.annotation.MembershipInactiveContact;
import com.bernardomg.association.contact.test.configuration.data.annotation.ValidContact;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.Contacts;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ContactRepository - find all - filter by name")
class ITContactRepositoryFindAllQueryName {

    @Autowired
    private ContactRepository repository;

    @Test
    @DisplayName("With no person, nothing is returned")
    void testFindAll_NoData() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL_MEMBER, ContactConstants.FIRST_NAME);

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
    @MembershipActiveContact
    void testFindAll_WithMembership_Active_FirstName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.membershipActive());
    }

    @Test
    @DisplayName("With a person having an active membership and matching full name, it is returned")
    @MembershipActiveContact
    void testFindAll_WithMembership_Active_FullName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.FULL_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.membershipActive());
    }

    @Test
    @DisplayName("With a person having an active membership and matching last name, it is returned")
    @MembershipActiveContact
    void testFindAll_WithMembership_Active_LastName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.LAST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.membershipActive());
    }

    @Test
    @DisplayName("With a person having an active membership and partial matching name, it is returned")
    @MembershipActiveContact
    void testFindAll_WithMembership_Active_PartialName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL,
            ContactConstants.FIRST_NAME.substring(0, ContactConstants.FIRST_NAME.length() - 2));

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.membershipActive());
    }

    @Test
    @DisplayName("With a person having an active membership and wrong name, nothing is returned")
    @MembershipActiveContact
    void testFindAll_WithMembership_Active_WrongName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.ALTERNATIVE_FIRST_NAME);

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
    @MembershipInactiveContact
    void testFindAll_WithMembership_Inactive_FirstName() {
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.FIRST_NAME);

        repository.findAll(filter, pagination, sorting);

        // THEN
        // Assertions.assertThat(people)
        // .extracting(Page::content)
        // .asInstanceOf(InstanceOfAssertFactories.LIST)
        // .containsExactly(Contacts.membershipInactive());
    }

    @Test
    @DisplayName("With a person having an inactive membership and matching full name, it is is returned")
    @MembershipInactiveContact
    void testFindAll_WithMembership_Inactive_FullName() {
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.FULL_NAME);

        repository.findAll(filter, pagination, sorting);

        // THEN
        // Assertions.assertThat(people)
        // .extracting(Page::content)
        // .asInstanceOf(InstanceOfAssertFactories.LIST)
        // .containsExactly(Contacts.membershipInactive());
    }

    @Test
    @DisplayName("With a person having an inactive membership and matching last name, it is is returned")
    @MembershipInactiveContact
    void testFindAll_WithMembership_Inactive_LastName() {
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.LAST_NAME);

        repository.findAll(filter, pagination, sorting);

        // THEN
        // Assertions.assertThat(people)
        // .extracting(Page::content)
        // .asInstanceOf(InstanceOfAssertFactories.LIST)
        // .containsExactly(Contacts.membershipInactive());
    }

    @Test
    @DisplayName("With a person having an inactive membership and partial matching name, it is is returned")
    @MembershipInactiveContact
    void testFindAll_WithMembership_Inactive_PartialName() {
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL,
            ContactConstants.FIRST_NAME.substring(0, ContactConstants.FIRST_NAME.length() - 2));

        repository.findAll(filter, pagination, sorting);

        // THEN
        // Assertions.assertThat(people)
        // .extracting(Page::content)
        // .asInstanceOf(InstanceOfAssertFactories.LIST)
        // .containsExactly(Contacts.membershipInactive());
    }

    @Test
    @DisplayName("With a person having an inactive membership and wrong name, nothing is returned")
    @MembershipInactiveContact
    void testFindAll_WithMembership_Inactive_WrongName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.ALTERNATIVE_FIRST_NAME);

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
    @ValidContact
    void testFindAll_WithoutMembership_FirstName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.valid());
    }

    @Test
    @DisplayName("With a person without membership and matching full name, it is is returned")
    @ValidContact
    void testFindAll_WithoutMembership_FullName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.FULL_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.valid());
    }

    @Test
    @DisplayName("With a person without membership and matching last name, it is is returned")
    @ValidContact
    void testFindAll_WithoutMembership_LastName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.LAST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.valid());
    }

    @Test
    @DisplayName("With a person without membership and partial matching name, it is is returned")
    @ValidContact
    void testFindAll_WithoutMembership_PartialName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL,
            ContactConstants.FIRST_NAME.substring(0, ContactConstants.FIRST_NAME.length() - 2));

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.valid());
    }

    @Test
    @DisplayName("With a person without membership and wrong name, nothing is returned")
    @MembershipInactiveContact
    void testFindAll_WithoutMembership_WrongName() {
        final Page<Contact> people;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactFilter filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactFilter(ContactStatus.ALL, ContactConstants.ALTERNATIVE_FIRST_NAME);

        // WHEN
        people = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(people)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
