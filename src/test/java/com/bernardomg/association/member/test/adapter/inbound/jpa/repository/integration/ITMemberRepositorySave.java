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
    @DisplayName("With an active member, the member is persisted")
    void testSave_Active_PersistedData() {
        final Member                 member;
        final Iterable<MemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("With an active member, the created member is returned")
    void testSave_Active_ReturnedData() {
        final Member member;
        final Member saved;

        // GIVEN
        member = Members.active();

        // WHEN
        saved = repository.save(member);

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .isEqualTo(Members.active());
    }

    @Test
    @DisplayName("When a member exists with an active membership, and an inactive membership is set, the member is persisted")
    @ActiveMember
    void testSave_Existing_Active_SetInactive_PersistedData() {
        final Member                 member;
        final Iterable<MemberEntity> entities;

        // GIVEN
        member = Members.inactive();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "membership.member")
            .containsExactly(MemberEntities.inactive());
    }

    @Test
    @DisplayName("When an active member exists, and an active membership is added, the member is persisted")
    @ActiveMember
    void testSave_Existing_ActiveMembershipToActive_PersistedData() {
        final Member                 member;
        final Iterable<MemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("When an active member exists, and an inactive membership is added, the member is persisted")
    @ActiveMember
    void testSave_Existing_ActiveToInactive_PersistedData() {
        final Member                 member;
        final Iterable<MemberEntity> entities;

        // GIVEN
        member = Members.inactive();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.inactive());
    }

    @Test
    @DisplayName("When a member exists, the member is persisted")
    @ActiveMember
    void testSave_Existing_PersistedData() {
        final Member                 member;
        final Iterable<MemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("When a member exists and a member is remove, the member is persisted")
    @EmailContactMethod
    @ActiveMember
    void testSave_Existing_RemoveMember_PersistedData() {
        final Member                 member;
        final Iterable<MemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("When a member exists, the created member is returned")
    @ActiveMember
    void testSave_Existing_ReturnedData() {
        final Member member;
        final Member saved;

        // GIVEN
        member = Members.active();

        // WHEN
        saved = repository.save(member);

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .isEqualTo(Members.active());
    }

    @Test
    @DisplayName("With an inactive member, the member is persisted")
    void testSave_Inactive_PersistedData() {
        final Member                 member;
        final Iterable<MemberEntity> entities;

        // GIVEN
        member = Members.inactive();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.inactive());
    }

}
