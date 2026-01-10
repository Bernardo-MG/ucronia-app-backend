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

import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberProfileSpringRepository;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberProfiles;
import com.bernardomg.association.member.test.configuration.factory.QueryMemberProfileEntities;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberProfileRepository - save all")
class ITMemberProfileRepositorySaveAll {

    @Autowired
    private ProfileSpringRepository       profileSpringRepository;

    @Autowired
    private MemberProfileRepository       repository;

    @Autowired
    private MemberProfileSpringRepository springRepository;

    public ITMemberProfileRepositorySaveAll() {
        super();
    }

    @Test
    @DisplayName("With an existing member, the member is persisted")
    @PositiveFeeType
    @ActiveMember
    void testSaveAll_Existing_PersistedData() {
        final MemberProfile                 member;
        final Iterable<MemberProfileEntity> entities;

        // GIVEN
        member = MemberProfiles.active();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(QueryMemberProfileEntities.withEmail());
    }

    @Test
    @DisplayName("With a valid member, the member is persisted")
    @PositiveFeeType
    @EmailContactMethod
    void testSaveAll_PersistedData() {
        final MemberProfile                 member;
        final Iterable<MemberProfileEntity> entities;

        // GIVEN
        member = MemberProfiles.active();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(QueryMemberProfileEntities.withEmail());
    }

    @Test
    @DisplayName("When the type is removed, the member is not changed")
    @PositiveFeeType
    @ActiveMember
    void testSaveAll_RemoveType_NoChange() {
        final MemberProfile                 member;
        final Iterable<MemberProfileEntity> entities;

        // GIVEN
        member = MemberProfiles.withoutType();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(QueryMemberProfileEntities.withEmail());
    }

    @Test
    @DisplayName("With a valid member, the created member is returned")
    @PositiveFeeType
    @EmailContactMethod
    void testSaveAll_ReturnedData() {
        final MemberProfile             member;
        final Collection<MemberProfile> saved;

        // GIVEN
        member = MemberProfiles.active();

        // WHEN
        saved = repository.saveAll(List.of(member));

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .containsExactly(MemberProfiles.created());
    }

    @Test
    @DisplayName("When the member is persisted, the profile types includes the member type")
    @PositiveFeeType
    @EmailContactMethod
    void testSaveAll_SetsType() {
        final MemberProfile member;
        final ProfileEntity profile;

        // GIVEN
        member = MemberProfiles.active();

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
