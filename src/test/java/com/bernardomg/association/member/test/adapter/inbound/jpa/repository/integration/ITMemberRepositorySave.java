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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberEntities;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - save")
class ITMemberRepositorySave {

    @Autowired
    private MemberRepository       repository;

    @Autowired
    private MemberSpringRepository springRepository;

    public ITMemberRepositorySave() {
        super();
    }

    @Test
    @DisplayName("With a contact with an active membership, the contact is persisted")
    void testSave_ActiveMembership_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.inactive();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.inactive());
    }

    @Test
    @DisplayName("When a contact exists with an active membership, and an inactive membership is set, the contact is persisted")
    @ActiveMember
    void testSave_Existing_Active_SetInactiveMembership_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.inactive();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "membership.contact")
            .containsExactly(MemberEntities.inactive());
    }

    @Test
    @DisplayName("When a contact exists, and an active membership is added, the contact is persisted")
    @ActiveMember
    void testSave_Existing_AddActiveMembership_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.active();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("When a contact exists, and an inactive membership is added, the contact is persisted")
    @ActiveMember
    void testSave_Existing_AddInactiveMembership_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.active();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("When a contact exists and a contact is added, the contact is persisted")
    @EmailContactMethod
    @ActiveMember
    void testSave_Existing_AddMember_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.withEmail();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.withEmail());
    }

    @Test
    @DisplayName("When a contact exists, the contact is persisted")
    @ActiveMember
    void testSave_Existing_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.active();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("When a contact exists and a contact is remove, the contact is persisted")
    @EmailContactMethod
    @ActiveMember
    void testSave_Existing_RemoveMember_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.active();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("When a contact exists, the created contact is returned")
    @ActiveMember
    void testSave_Existing_ReturnedData() {
        final Member contact;
        final Member saved;

        // GIVEN
        contact = Members.active();

        // WHEN
        saved = repository.save(contact);

        // THEN
        Assertions.assertThat(saved)
            .as("contact")
            .isEqualTo(Members.active());
    }

    @Test
    @DisplayName("With a contact with an inactive membership, the contact is persisted")
    void testSave_InactiveMembership_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.inactive();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.inactive());
    }

    @Test
    @DisplayName("With a valid contact, the contact is persisted")
    void testSave_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.active();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("With a valid contact, the created contact is returned")
    void testSave_ReturnedData() {
        final Member contact;
        final Member saved;

        // GIVEN
        contact = Members.active();

        // WHEN
        saved = repository.save(contact);

        // THEN
        Assertions.assertThat(saved)
            .as("contact")
            .isEqualTo(Members.active());
    }

    @Test
    @DisplayName("With a contact with a contact method, the contact is returned")
    @EmailContactMethod
    void testSave_WithMember_ReturnedData() {
        final Member contact;
        final Member saved;

        // GIVEN
        contact = Members.withEmail();

        // WHEN
        saved = repository.save(contact);

        // THEN
        Assertions.assertThat(saved)
            .as("contact")
            .isEqualTo(Members.withEmail());
    }

    @Test
    @DisplayName("With a contact with a contact method, the contact is persisted")
    @EmailContactMethod
    void testSave_WithMemberChannel_PersistedData() {
        final Member                 contact;
        final Iterable<MemberEntity> entities;

        // GIVEN
        contact = Members.withEmail();

        // WHEN
        repository.save(contact);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "contactChannels.contact",
                "contactChannels.contactMethod")
            .containsExactly(MemberEntities.withEmail());
    }

}
