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

package com.bernardomg.association.security.user.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.test.configuration.data.annotation.AlternativeContact;
import com.bernardomg.association.contact.test.configuration.data.annotation.NoMembershipContact;
import com.bernardomg.association.contact.test.configuration.factory.Contacts;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithContact;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("UserContactRepository - find all not assigned")
class ITUserContactRepositoryFindAllNotAssigned {

    @Autowired
    private UserContactRepository repository;

    @Test
    @DisplayName("When the member is assigned, it is not returned")
    @ValidUserWithContact
    void testFindAllNotAssigned_Assigned() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        contacts = repository.findAllNotAssigned(pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When the member is assigned and there is another not assigned, the one not assigned is returned")
    @ValidUserWithContact
    @AlternativeContact
    void testFindAllNotAssigned_AssignedAndNotAssigned() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        contacts = repository.findAllNotAssigned(pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.alternative());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindAllNotAssigned_NoData() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        contacts = repository.findAllNotAssigned(pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When there an active member available, it is returned")
    @ValidUser
    @NoMembershipContact
    void testFindAllNotAssigned_NotAssigned() {
        final Page<Contact> contacts;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        contacts = repository.findAllNotAssigned(pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.noMembership());
    }

}
