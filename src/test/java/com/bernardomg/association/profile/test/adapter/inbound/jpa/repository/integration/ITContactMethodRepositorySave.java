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

package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodEntities;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethods;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ContactMethodRepository - save")
class ITContactMethodRepositorySave {

    @Autowired
    private ContactMethodRepository       repository;

    @Autowired
    private ContactMethodSpringRepository springRepository;

    public ITContactMethodRepositorySave() {
        super();
    }

    @Test
    @DisplayName("With a valid contact method, the contact method is persisted")
    void testSave_PersistedData() {
        final ContactMethod                 contact;
        final Iterable<ContactMethodEntity> entities;

        // GIVEN
        contact = ContactMethods.email();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(ContactMethodEntities.email());
    }

    @Test
    @DisplayName("With a valid contact method, the created contact method is returned")
    void testSave_ReturnedData() {
        final ContactMethod contact;
        final ContactMethod saved;

        // GIVEN
        contact = ContactMethods.email();

        // WHEN
        saved = repository.save(contact);

        // THEN
        Assertions.assertThat(saved)
            .as("contact method")
            .isEqualTo(ContactMethods.email());
    }

}
