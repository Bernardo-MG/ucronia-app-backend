
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
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

    @Test
    @PositiveFeeType
    @ValidProfile
    @DisplayName("When a member is saved, then its profile number is unchanged and its member number is generated")
    void testSave_AssignsMemberNumberWithoutChangingProfileNumber() {
        final Member member;
        final Member saved;

        // GIVEN
        member = Members.active();

        // WHEN
        saved = repository.save(member);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(saved.number())
                .as("profile number")
                .isEqualTo(member.number());
            softly.assertThat(saved.memberNumber())
                .as("generated member number")
                .isPositive();
            softly.assertThat(repository.findOne(saved.number())
                .orElseThrow()
                .memberNumber())
                .as("persisted member number")
                .isEqualTo(saved.memberNumber());
        });
    }

    @Test
    @DisplayName("When a member exists and a contact method is added, then the member is persisted")
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "memberNumber", "profile.id",
                "profile.number", "contactChannels.id", "contactChannels.profileId", "contactChannels.profile")
            .containsExactly(ReadMemberEntities.withEmail());
    }

    @Test
    @DisplayName("When a member name is changed, then the member is persisted")
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "memberNumber", "profile.id",
                "profile.number", "profile.contactChannels.id", "profile.contactChannels.profileId",
                "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.firstNameChange());
    }

    @Test
    @DisplayName("When a member exists and a contact method is removed, then the member is persisted")
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "memberNumber", "profile.id",
                "profile.number", "contactChannels.id", "contactChannels.profileId", "contactChannels.profile")
            .containsExactly(ReadMemberEntities.withEmail());
    }

    @Test
    @DisplayName("When a member exists, then the created member is returned")
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
            .usingRecursiveComparison()
            .ignoringFields("audit", "memberNumber")
            .isEqualTo(Members.active());
    }

    @Test
    @DisplayName("When a member doesn't exist but the profile does, then the member is persisted")
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "memberNumber", "profile.id",
                "profile.number", "profile.contactChannels.id", "profile.contactChannels.profileId",
                "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.active());
    }

    @Test
    @PositiveFeeType
    @ActiveMember
    @DisplayName("When an existing member is updated, then its member number is retained")
    void testSave_KeepsMemberNumberWhenUpdating() {
        final Member existing;
        final Member member;
        final Member saved;

        // GIVEN
        existing = repository.findOne(Members.active()
            .number())
            .orElseThrow();
        member = Members.firstNameChange();

        // WHEN
        saved = repository.save(member);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(saved.memberNumber())
                .as("member number")
                .isEqualTo(existing.memberNumber());
            softly.assertThat(saved.activated()
                .memberNumber())
                .as("activated member number")
                .isEqualTo(existing.memberNumber());
            softly.assertThat(saved.deactivated()
                .memberNumber())
                .as("deactivated member number")
                .isEqualTo(existing.memberNumber());
        });
    }

    @Test
    @DisplayName("When there is a member, then the member is persisted")
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "memberNumber", "profile.id",
                "profile.number", "profile.contactChannels.id", "profile.contactChannels.profileId",
                "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.active());
    }

    @Test
    @DisplayName("When the type is removed, then the member is not changed")
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "memberNumber", "profile.id",
                "profile.number", "profile.contactChannels.id", "profile.contactChannels.profileId",
                "profile.contactChannels.profile")
            .containsExactly(ReadMemberEntities.active());
    }

    @Test
    @DisplayName("When there is a member, then the created member is returned")
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
            .usingRecursiveComparison()
            .ignoringFields("memberNumber")
            .isEqualTo(Members.created());
    }

    @Test
    @DisplayName("When the member is persisted, then the profile types includes the member type")
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
    @DisplayName("When there is a member with contact method, then the member is persisted")
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
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "memberNumber", "profile.id",
                "profile.number", "contactChannels.id", "contactChannels.profileId", "contactChannels.profile")
            .containsExactly(ReadMemberEntities.withEmail());
    }

}
