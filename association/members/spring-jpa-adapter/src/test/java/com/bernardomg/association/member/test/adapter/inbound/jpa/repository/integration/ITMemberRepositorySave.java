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
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntityConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.model.ReadMemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.ReadMemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithEmail;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.test.configuration.factory.ReadMemberEntities;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - save")
class ITMemberRepositorySave {

    @Autowired
    private ProfileSpringRepository    profileSpringRepository;

    @Autowired
    private MemberRepository           repository;

    @Autowired
    private ReadMemberSpringRepository springRepository;

    public ITMemberRepositorySave() {
        super();
    }

    @Test
    @DisplayName("When a member exists and a contact method is added, the member is persisted")
    @PositiveFeeType
    @EmailContactMethod
    @ActiveMember
    void testSave_Existing_AddContactMethod_PersistedData() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.withEmail();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "contactChannels.id", "contactChannels.profileId", "contactChannels.profile")
            .containsExactly(ReadMemberEntities.withEmail());
    }

    @Test
    @DisplayName("When a member name is changed, the member is persisted")
    @PositiveFeeType
    @ActiveMember
    void testSave_Existing_NameChange_PersistedData() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.firstNameChange();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.firstNameChange());
    }

    @Test
    @DisplayName("When a member exists and a contact method is removed, the member is persisted")
    @PositiveFeeType
    @ActiveMemberWithEmail
    void testSave_Existing_RemoveContactMethod_PersistedData() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "contactChannels.id", "contactChannels.profileId", "contactChannels.profile")
            .containsExactly(ReadMemberEntities.withEmail());
    }

    @Test
    @DisplayName("When a member exists, the created member is returned")
    @PositiveFeeType
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
    @DisplayName("When a member doesn't exist but the profile does, the member is persisted")
    @PositiveFeeType
    @ValidProfile
    void testSave_ExistingProfile_PersistedData() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.active());
    }

    @Test
    @DisplayName("With a member, the member is persisted")
    @PositiveFeeType
    void testSave_PersistedData() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.active());
    }

    @Test
    @DisplayName("When the type is removed, the member is not changed")
    @PositiveFeeType
    @ActiveMember
    void testSave_RemoveType_NoChange() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.withoutType();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.active());
    }

    @Test
    @DisplayName("With a member, the created member is returned")
    @PositiveFeeType
    void testSave_ReturnedData() {
        final Member member;
        final Member saved;

        // GIVEN
        member = Members.active();

        // WHEN
        saved = repository.save(member);

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .isEqualTo(Members.created());
    }

    @Test
    @DisplayName("When the member is persisted, the profile types includes the member type")
    @PositiveFeeType
    void testSave_SetsType() {
        final Member        member;
        final ProfileEntity profile;

        // GIVEN
        member = Members.active();

        // WHEN
        repository.save(member);

        // THEN
        profile = profileSpringRepository.findByNumber(1L)
            .get();

        Assertions.assertThat(profile)
            .as("profile")
            .extracting(ProfileEntity::getTypes)
            .asInstanceOf(InstanceOfAssertFactories.SET)
            .containsExactly(MemberEntityConstants.PROFILE_TYPE);
    }

    @Test
    @DisplayName("With a member with contact method, the member is persisted")
    @PositiveFeeType
    @EmailContactMethod
    void testSave_WithContactMethod_PersistedData() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.withEmail();

        // WHEN
        repository.save(member);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "contactChannels.id", "contactChannels.profileId", "contactChannels.profile")
            .containsExactly(ReadMemberEntities.withEmail());
    }

}
