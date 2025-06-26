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

package com.bernardomg.association.person.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.person.test.configuration.factory.ContactMethods;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ContactMethodRepository - find all")
class ITContactMethodRepositoryFindAll {

    @Autowired
    private ContactMethodRepository repository;

    @Test
    @DisplayName("With no contact method, nothing is returned")
    void testFindAll_NoData() {
        final Iterable<ContactMethod> ContactMethods;
        final Pagination              pagination;
        final Sorting                 sorting;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();

        // WHEN
        ContactMethods = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(ContactMethods)
            .isEmpty();
    }

    @Test
    @DisplayName("With a single contact method, it is returned")
    @EmailContactMethod
    void testFindAll_Single() {
        final Iterable<ContactMethod> contactMethods;
        final Pagination              pagination;
        final Sorting                 sorting;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();

        // WHEN
        contactMethods = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(contactMethods)
            .containsExactly(ContactMethods.email());
    }

}
