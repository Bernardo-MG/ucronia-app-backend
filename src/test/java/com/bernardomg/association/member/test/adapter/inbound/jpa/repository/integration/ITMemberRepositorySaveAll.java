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

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.QueryMemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.test.configuration.factory.QueryMemberEntities;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - save all")
class ITMemberRepositorySaveAll {

    @Autowired
    private ProfileSpringRepository     profileSpringRepository;

    @Autowired
    private MemberRepository            repository;

    @Autowired
    private QueryMemberSpringRepository springRepository;

    public ITMemberRepositorySaveAll() {
        super();
    }

    @Test
    @DisplayName("With an existing member, the member is persisted")
    @ActiveMember
    void testSave_Existing_PersistedData() {
        final Member                      member;
        final Iterable<QueryMemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(QueryMemberEntities.created());
    }

    @Test
    @DisplayName("With a valid member, the member is persisted")
    void testSave_PersistedData() {
        final Member                      member;
        final Iterable<QueryMemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "profile.number")
            .containsExactly(QueryMemberEntities.created());
    }

    @Test
    @DisplayName("With a valid member, the created member is returned")
    void testSave_ReturnedData() {
        final Member             member;
        final Collection<Member> saved;

        // GIVEN
        member = Members.active();

        // WHEN
        saved = repository.saveAll(List.of(member));

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .containsExactly(Members.created());
    }

    @Test
    @DisplayName("When the member is persisted, the profile types includes the member type")
    void testSave_SetsType() {
        final Member        member;
        final ProfileEntity profile;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        profile = profileSpringRepository.findByNumber(1L)
            .get();

        Assertions.assertThat(profile)
            .as("profile")
            .extracting(ProfileEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(MemberEntityConstants.PROFILE_TYPE);
    }

}
