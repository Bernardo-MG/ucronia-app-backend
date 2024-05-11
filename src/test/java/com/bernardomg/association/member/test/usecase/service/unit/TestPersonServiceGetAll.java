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

package com.bernardomg.association.member.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Person;
import com.bernardomg.association.member.domain.repository.PersonRepository;
import com.bernardomg.association.member.test.config.factory.Persons;
import com.bernardomg.association.member.usecase.service.DefaultPersonService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Person service - get all")
class TestPersonServiceGetAll {

    @Mock
    private PersonRepository     personRepository;

    @InjectMocks
    private DefaultPersonService service;

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
        given(personRepository.findAll(ArgumentMatchers.any())).willReturn(List.of(Persons.valid()));

        pageable = Pageable.unpaged();

        // WHEN
        persons = service.getAll(pageable);

        // THEN
        Assertions.assertThat(persons)
            .as("persons")
            .isEqualTo(List.of(Persons.valid()));
    }

}
