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

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactivePerson;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.person.test.configuration.factory.PersonEntities;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersonRepository - activate single")
class ITPersonRepositoryActivate {

    @Autowired
    private PersonSpringRepository personRepository;

    @Autowired
    private PersonRepository       repository;

    @Test
    @DisplayName("With an existing active member, nothing changes")
    @MembershipActivePerson
    void testActivate_Active() {
        final Iterable<PersonEntity> entities;

        // WHEN
        repository.activate(PersonConstants.NUMBER);

        // THEN
        entities = personRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("membership.person")
            .containsExactly(PersonEntities.membershipActive());
    }

    @Test
    @DisplayName("With an existing inactive member, it is activated")
    @MembershipInactivePerson
    void testActivate_Inactive() {
        final Iterable<PersonEntity> entities;

        // WHEN
        repository.activate(PersonConstants.NUMBER);

        // THEN
        entities = personRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("membership.person")
            .containsExactly(PersonEntities.membershipActive());
    }

    @Test
    @DisplayName("With no member, nothing changes")
    void testActivate_NoData() {
        final Iterable<PersonEntity> entities;

        // WHEN
        repository.activate(PersonConstants.NUMBER);

        // THEN
        entities = personRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .isEmpty();
    }

}
