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

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberProfileEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.QueryMemberProfileSpringRepository;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberProfiles;
import com.bernardomg.association.member.test.configuration.factory.QueryMemberContactEntities;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberProfileRepository - save with number")
class ITMemberProfileRepositorySaveWithNumber {

    @Autowired
    private ProfileSpringRepository            profileSpringRepository;

    @Autowired
    private MemberProfileRepository            repository;

    @Autowired
    private QueryMemberProfileSpringRepository springRepository;

    public ITMemberProfileRepositorySaveWithNumber() {
        super();
    }

    @Test
    @DisplayName("With a member, the member is persisted")
    @EmailContactMethod
    @ValidProfile
    void testSaveWithNumber_PersistedData() {
        final MemberProfile                      member;
        final Iterable<QueryMemberProfileEntity> entities;

        // GIVEN
        member = MemberProfiles.active();

        // WHEN
        repository.save(member, ProfileConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(QueryMemberContactEntities.valid());
    }

    @Test
    @DisplayName("With a member, the created member is returned")
    @EmailContactMethod
    @ValidProfile
    void testSaveWithNumber_ReturnedData() {
        final MemberProfile member;
        final MemberProfile saved;

        // GIVEN
        member = MemberProfiles.active();

        // WHEN
        saved = repository.save(member, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .isEqualTo(MemberProfiles.noContactChannel());
    }

    @Test
    @DisplayName("When the member is persisted, the profile types includes the member type")
    @EmailContactMethod
    void testSaveWithNumber_SetsType() {
        final MemberProfile member;
        final MemberProfile saved;
        final ProfileEntity profile;

        // GIVEN
        member = MemberProfiles.active();

        // WHEN
        saved = repository.save(member, ProfileConstants.NUMBER);

        // THEN
        profile = profileSpringRepository.findByNumber(saved.number())
            .get();

        Assertions.assertThat(profile)
            .as("profile")
            .extracting(ProfileEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(MemberEntityConstants.PROFILE_TYPE);
    }

}
