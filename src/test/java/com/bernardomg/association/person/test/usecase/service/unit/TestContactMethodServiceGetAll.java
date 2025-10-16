/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any ContactMethod obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit ContactMethods to whom the Software is
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.association.person.test.configuration.factory.ContactMethods;
import com.bernardomg.association.person.usecase.service.DefaultContactMethodService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Property;

@ExtendWith(MockitoExtension.class)
@DisplayName("Contact method service - get all")
class TestContactMethodServiceGetAll {

    @Mock
    private ContactMethodRepository     ContactMethodRepository;

    @InjectMocks
    private DefaultContactMethodService service;

    public TestContactMethodServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When there is no data, it returns nothing")
    void testGetAll_NoData() {
        final Page<ContactMethod> contactMethods;
        final Page<ContactMethod> existing;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(ContactMethodRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        contactMethods = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(contactMethods)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("ContactMethods")
            .isEmpty();
    }

    @Test
    @DisplayName("When getting all the ContactMethods, it returns all the ContactMethods")
    void testGetAll_ReturnsData() {
        final Page<ContactMethod> contactMethods;
        final Page<ContactMethod> existing;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(ContactMethods.email()), 0, 0, 0, 0, 0, false, false, sorting);
        given(ContactMethodRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        contactMethods = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(contactMethods)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("ContactMethods")
            .containsExactly(ContactMethods.email());
    }

    @Test
    @DisplayName("When sorting ascending by first name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Asc_FirstName() {
        final Pagination          pagination;
        final Page<ContactMethod> existing;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.asc("firstName");

        existing = new Page<>(List.of(ContactMethods.email()), 0, 0, 0, 0, 0, false, false, sorting);
        given(ContactMethodRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        service.getAll(pagination, sorting);

        // THEN
        verify(ContactMethodRepository).findAll(eq(pagination), assertArg(s -> assertThat(s).as("sort")
            .extracting(Sorting::properties)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Property.asc("firstName"))));
    }

    @Test
    @DisplayName("When sorting descending by first name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Desc_FirstName() {
        final Pagination          pagination;
        final Sorting             sorting;
        final Page<ContactMethod> existing;

        // GIVEN
        pagination = new Pagination(1, 100);
        sorting = Sorting.desc("firstName");

        existing = new Page<>(List.of(ContactMethods.email()), 0, 0, 0, 0, 0, false, false, sorting);
        given(ContactMethodRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        service.getAll(pagination, sorting);

        // THEN
        verify(ContactMethodRepository).findAll(eq(pagination), assertArg(s -> assertThat(s).as("sort")
            .extracting(Sorting::properties)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Property.desc("firstName"))));
    }

}
