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

import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.QueryMemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.test.configuration.factory.QueryMemberEntities;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - save with number")
class ITMemberRepositorySaveWithNumber {

    @Autowired
    private ProfileSpringRepository     profileSpringRepository;

    @Autowired
    private MemberRepository            repository;

    @Autowired
    private QueryMemberSpringRepository springRepository;

    public ITMemberRepositorySaveWithNumber() {
        super();
    }

    @Test
    @DisplayName("With an active member, the member is persisted")
    @PositiveFeeType
    @ValidProfile
    void testSaveWithNumber_Active_PersistedData() {
        final Member                      member;
        final Iterable<QueryMemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.save(member, ProfileConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(QueryMemberEntities.active());
    }

    @Test
    @DisplayName("With an active member, the created member is returned")
    @PositiveFeeType
    @ValidProfile
    void testSaveWithNumber_Active_ReturnedData() {
        final Member member;
        final Member saved;

        // GIVEN
        member = Members.active();

        // WHEN
        saved = repository.save(member, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .isEqualTo(Members.active());
    }

    @Test
    @DisplayName("With an inactive member, the member is persisted")
    @PositiveFeeType
    @ValidProfile
    void testSaveWithNumber_Inactive_PersistedData() {
        final Member                      member;
        final Iterable<QueryMemberEntity> entities;

        // GIVEN
        member = Members.inactive();

        // WHEN
        repository.save(member, ProfileConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(QueryMemberEntities.inactive());
    }

    @Test
    @DisplayName("When the member is persisted, the profile types includes the member type")
    @PositiveFeeType
    void testSaveWithNumber_SetsType() {
        final Member        member;
        final Member        saved;
        final ProfileEntity profile;

        // GIVEN
        member = Members.active();

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
