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

package com.bernardomg.association.person.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.association.person.usecase.service.DefaultPersonService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Person service - get all")
class TestPersonServiceGetAll {

    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;

    @Mock
    private PersonRepository         personRepository;

    @InjectMocks
    private DefaultPersonService     service;

    public TestPersonServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When there is no data, it returns nothing")
    void testGetAll_NoData() {
        final Iterable<Person> persons;
        final Pageable         pageable;
        final Page<Person>     readPersons;

        // GIVEN
        readPersons = new PageImpl<>(List.of());
        given(personRepository.findAll(ArgumentMatchers.any())).willReturn(readPersons);

        pageable = Pageable.unpaged();

        // WHEN
        persons = service.getAll(pageable);

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .isEmpty();
    }

    @Test
    @DisplayName("When getting all the persons, it returns all the persons")
    void testGetAll_ReturnsData() {
        final Iterable<Person> persons;
        final Pageable         pageable;

        // GIVEN
        given(personRepository.findAll(ArgumentMatchers.any())).willReturn(List.of(Persons.noMembership()));

        pageable = Pageable.unpaged();

        // WHEN
        persons = service.getAll(pageable);

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .containsExactly(Persons.noMembership());
    }

    @Test
    @DisplayName("When sorting ascending by first name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Asc_FirstName() {
        final Pageable     pageable;
        final Page<Person> readPersons;

        // GIVEN
        readPersons = new PageImpl<>(List.of(Persons.noMembership()));
        given(personRepository.findAll(pageableCaptor.capture())).willReturn(readPersons);

        pageable = PageRequest.of(0, 1, Sort.by("firstName"));

        // WHEN
        service.getAll(pageable);

        // THEN
        // TODO: avoid captors
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.asc("firstName"));
    }

    @Test
    @DisplayName("When sorting descending by first name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Desc_FirstName() {
        final Pageable     pageable;
        final Page<Person> readPersons;

        // GIVEN
        readPersons = new PageImpl<>(List.of(Persons.noMembership()));
        given(personRepository.findAll(pageableCaptor.capture())).willReturn(readPersons);

        pageable = PageRequest.of(0, 1, Sort.by(Direction.DESC, "firstName"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.desc("firstName"));
    }

    @Test
    @DisplayName("When sorting ascending by first name, and not applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Unpaged_Asc_FirstName() {
        final Pageable     pageable;
        final Page<Person> readPersons;

        // GIVEN
        readPersons = new PageImpl<>(List.of(Persons.noMembership()));
        given(personRepository.findAll(pageableCaptor.capture())).willReturn(readPersons);

        pageable = Pageable.unpaged(Sort.by("firstName"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.asc("firstName"));
    }

}
