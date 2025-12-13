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

import com.bernardomg.association.contact.domain.filter.ContactQuery;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
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
    @DisplayName("With a valid contact and matching first name, it is is returned")
    @ValidContact
    void testFindAll_FirstName() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactQuery(ContactConstants.FIRST_NAME);

        // WHEN
        contacts = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.valid());
    }

    @Test
    @DisplayName("With a valid contact and matching full name, it is is returned")
    @ValidContact
    void testFindAll_FullName() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactQuery(ContactConstants.FULL_NAME);

        // WHEN
        contacts = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.valid());
    }

    @Test
    @DisplayName("With a valid contact and matching last name, it is is returned")
    @ValidContact
    void testFindAll_LastName() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactQuery(ContactConstants.LAST_NAME);

        // WHEN
        contacts = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.valid());
    }

    @Test
    @DisplayName("With no contact, nothing is returned")
    void testFindAll_NoData() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactQuery(ContactConstants.FIRST_NAME);

        // WHEN
        contacts = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a valid contact and partial matching name, it is is returned")
    @ValidContact
    void testFindAll_PartialName() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactQuery(ContactConstants.FIRST_NAME.substring(0, ContactConstants.FIRST_NAME.length() - 2));

        // WHEN
        contacts = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.valid());
    }

    @Test
    @DisplayName("With a valid contact and wrong name, nothing is returned")
    @ValidContact
    void testFindAll_WrongName() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;
        final ContactQuery  filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new ContactQuery(ContactConstants.ALTERNATIVE_FIRST_NAME);

        // WHEN
        contacts = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
