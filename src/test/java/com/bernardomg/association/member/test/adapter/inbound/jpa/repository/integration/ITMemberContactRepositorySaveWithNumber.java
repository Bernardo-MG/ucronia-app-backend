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

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.contact.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.contact.test.configuration.data.annotation.ValidContact;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberContactEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.QueryMemberContactSpringRepository;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.association.member.test.configuration.factory.QueryMemberContactEntities;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberContactRepository - save with number")
class ITMemberContactRepositorySaveWithNumber {

    @Autowired
    private ContactSpringRepository            contactSpringRepository;

    @Autowired
    private MemberContactRepository            repository;

    @Autowired
    private QueryMemberContactSpringRepository springRepository;

    public ITMemberContactRepositorySaveWithNumber() {
        super();
    }

    @Test
    @DisplayName("With a guest, the guest is persisted")
    @EmailContactMethod
    @ValidContact
    void testSaveWithNumber_PersistedData() {
        final MemberContact                      guest;
        final Iterable<QueryMemberContactEntity> entities;

        // GIVEN
        guest = MemberContacts.active();

        // WHEN
        repository.save(guest, ContactConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(QueryMemberContactEntities.valid());
    }

    @Test
    @DisplayName("With a guest, the created guest is returned")
    @EmailContactMethod
    @ValidContact
    void testSaveWithNumber_ReturnedData() {
        final MemberContact guest;
        final MemberContact saved;

        // GIVEN
        guest = MemberContacts.active();

        // WHEN
        saved = repository.save(guest, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(saved)
            .as("guest")
            .isEqualTo(MemberContacts.noContactChannel());
    }

    @Test
    @DisplayName("When the guest is persisted, the contact types includes the guest type")
    @EmailContactMethod
    void testSaveWithNumber_SetsType() {
        final MemberContact guest;
        final MemberContact saved;
        final ContactEntity contact;

        // GIVEN
        guest = MemberContacts.active();

        // WHEN
        saved = repository.save(guest, ContactConstants.NUMBER);

        // THEN
        contact = contactSpringRepository.findByNumber(saved.number())
            .get();

        Assertions.assertThat(contact)
            .as("contact")
            .extracting(ContactEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(MemberEntityConstants.CONTACT_TYPE);
    }

}
