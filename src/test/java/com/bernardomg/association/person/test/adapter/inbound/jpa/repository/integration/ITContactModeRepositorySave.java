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

import com.bernardomg.association.person.adapter.inbound.jpa.model.ContactModeEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.ContactModeSpringRepository;
import com.bernardomg.association.person.domain.model.ContactMode;
import com.bernardomg.association.person.domain.repository.ContactModeRepository;
import com.bernardomg.association.person.test.configuration.factory.ContactModeEntities;
import com.bernardomg.association.person.test.configuration.factory.ContactModes;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ContactModeRepository - save")
class ITContactModeRepositorySave {

    @Autowired
    private ContactModeRepository       repository;

    @Autowired
    private ContactModeSpringRepository springRepository;

    public ITContactModeRepositorySave() {
        super();
    }

    @Test
    @DisplayName("With a valid contact mode, the contact mode is persisted")
    void testSave_PersistedData() {
        final ContactMode                 person;
        final Iterable<ContactModeEntity> entities;

        // GIVEN
        person = ContactModes.valid();

        // WHEN
        repository.save(person);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "membership.person")
            .containsExactly(ContactModeEntities.valid());
    }

    @Test
    @DisplayName("With a valid contact mode, the created contact mode is returned")
    void testSave_ReturnedData() {
        final ContactMode person;
        final ContactMode saved;

        // GIVEN
        person = ContactModes.valid();

        // WHEN
        saved = repository.save(person);

        // THEN
        Assertions.assertThat(saved)
            .as("contact mode")
            .isEqualTo(ContactModes.valid());
    }

}
