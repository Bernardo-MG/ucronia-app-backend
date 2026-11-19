
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;
import java.util.List;

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
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.test.configuration.factory.ReadMemberEntities;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - saveAll")
class ITMemberRepositorySaveAll {

    @Autowired
    private ProfileSpringRepository    profileSpringRepository;

    @Autowired
    private MemberRepository           repository;

    @Autowired
    private ReadMemberSpringRepository springRepository;

    @Test
    @DisplayName("When no data is received, then nothing is saved")
    void testSaveAll_Empty() {
        final Iterable<ReadMemberEntity> entities;

        // WHEN
        repository.saveAll(List.of());

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .isEmpty();
    }

    @Test
    @DisplayName("When changing the member name, then the member is persisted")
    @PositiveFeeType
    @ActiveMember
    void testSaveAll_Existing_NameChange_PersistedData() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.firstNameChange();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.firstNameChange());
    }

    @Test
    @DisplayName("When there is a valid member, then the member is persisted")
    @PositiveFeeType
    void testSaveAll_PersistedData() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.toCreate();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "memberNumber", "profile.id",
                "profile.number", "profile.contactChannels.id", "profile.contactChannels.profileId",
                "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.active());
    }

    @Test
    @DisplayName("When the type is removed, then the member is not changed")
    @PositiveFeeType
    @ActiveMember
    void testSaveAll_RemoveType_NoChange() {
        final Member                     member;
        final Iterable<ReadMemberEntity> entities;

        // GIVEN
        member = Members.withoutType();

        // WHEN
        repository.saveAll(List.of(member));

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "profile.id", "profile.number",
                "profile.contactChannels.id", "profile.contactChannels.profileId", "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.active());
    }

    @Test
    @DisplayName("When there is a valid member, then the created member is returned")
    @PositiveFeeType
    void testSaveAll_ReturnedData() {
        final Member             member;
        final Collection<Member> saved;

        // GIVEN
        member = Members.toCreate();

        // WHEN
        saved = repository.saveAll(List.of(member));

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .containsExactly(Members.created());
    }

    @Test
    @DisplayName("When the member is persisted, then the profile types includes the member type")
    @PositiveFeeType
    void testSaveAll_SetsType() {
        final Member        member;
        final ProfileEntity profile;

        // GIVEN
        member = Members.toCreate();

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
