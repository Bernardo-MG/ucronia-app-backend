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

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.contact.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberContactEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.QueryMemberContactSpringRepository;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.association.member.test.configuration.factory.QueryMemberContactEntities;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberContactRepository - save all")
class ITMemberContactRepositorySaveAll {

    @Autowired
    private ContactSpringRepository            contactSpringRepository;

    @Autowired
    private MemberContactRepository            repository;

    @Autowired
    private QueryMemberContactSpringRepository springRepository;

    public ITMemberContactRepositorySaveAll() {
        super();
    }

    @Test
    @DisplayName("With an existing member, the member is persisted")
    @ActiveMember
    void testSaveAll_Existing_PersistedData() {
        final MemberContact                      member;
        final Iterable<QueryMemberContactEntity> entities;

        // GIVEN
        member = MemberContacts.active();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id",
                "contactChannels.contactId", "contactChannels.contact")
            .containsExactly(QueryMemberContactEntities.withEmail());
    }

    @Test
    @DisplayName("With a valid member, the member is persisted")
    @EmailContactMethod
    void testSaveAll_PersistedData() {
        final MemberContact                      member;
        final Iterable<QueryMemberContactEntity> entities;

        // GIVEN
        member = MemberContacts.active();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id",
                "contactChannels.contactId", "contactChannels.contact")
            .containsExactly(QueryMemberContactEntities.withEmail());
    }

    @Test
    @DisplayName("When the type is removed, the member is not changed")
    @ActiveMember
    void testSaveAll_RemoveType_NoChange() {
        final MemberContact                      member;
        final Iterable<QueryMemberContactEntity> entities;

        // GIVEN
        member = MemberContacts.withoutType();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.id",
                "contactChannels.contactId", "contactChannels.contact")
            .containsExactly(QueryMemberContactEntities.withEmail());
    }

    @Test
    @DisplayName("With a valid member, the created member is returned")
    @EmailContactMethod
    void testSaveAll_ReturnedData() {
        final MemberContact             member;
        final Collection<MemberContact> saved;

        // GIVEN
        member = MemberContacts.active();

        // WHEN
        saved = repository.saveAll(List.of(member));

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .containsExactly(MemberContacts.created());
    }

    @Test
    @DisplayName("When the member is persisted, the contact types includes the member type")
    @EmailContactMethod
    void testSaveAll_SetsType() {
        final MemberContact member;
        final ContactEntity contact;

        // GIVEN
        member = MemberContacts.active();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        contact = contactSpringRepository.findByNumber(1L)
            .get();

        Assertions.assertThat(contact)
            .as("contact")
            .extracting(ContactEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(MemberEntityConstants.CONTACT_TYPE);
    }

}
