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

package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.test.configuration.data.annotation.ValidContact;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberContactRepository - find all - filter by name")
class ITMemberContactRepositoryFindAllQueryName {

    @Autowired
    private MemberContactRepository repository;

    @Test
    @DisplayName("With a guest matching first name, it is returned")
    @ValidContact
    void testFindAll_FirstName() {
        final Page<MemberContact> guests;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, ContactConstants.FIRST_NAME);

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(MemberContacts.active());
    }

    @Test
    @DisplayName("With a guest having a guest role and matching full name, it is returned")
    @ValidContact
    void testFindAll_FullName() {
        final Page<MemberContact> guests;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, ContactConstants.FULL_NAME);

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(MemberContacts.active());
    }

    @Test
    @DisplayName("With a guest having a guest role and matching last name, it is returned")
    @ValidContact
    void testFindAll_LastName() {
        final Page<MemberContact> guests;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, ContactConstants.LAST_NAME);

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(MemberContacts.active());
    }

    @Test
    @DisplayName("With no guest, nothing is returned")
    void testFindAll_NoData() {
        final Page<MemberContact> guests;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, ContactConstants.FIRST_NAME);

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a guest having a guest role and partial matching name, it is returned")
    @ValidContact
    void testFindAll_PartialName() {
        final Page<MemberContact> guests;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL,
            ContactConstants.FIRST_NAME.substring(0, ContactConstants.FIRST_NAME.length() - 2));

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(MemberContacts.active());
    }

    @Test
    @DisplayName("With a guest without guest role and matching first name, it is is returned")
    @ValidContact
    void testFindAll_WithoutMemberContactship_FirstName() {
        final Page<MemberContact> guests;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, ContactConstants.FIRST_NAME);

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With a guest having a guest role and wrong name, nothing is returned")
    @ValidContact
    void testFindAll_WrongName() {
        final Page<MemberContact> guests;
        final Pagination          pagination;
        final Sorting             sorting;
        final MemberFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();
        filter = new MemberFilter(MemberStatus.ALL, ContactConstants.ALTERNATIVE_FIRST_NAME);

        // WHEN
        guests = repository.findAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(guests)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
