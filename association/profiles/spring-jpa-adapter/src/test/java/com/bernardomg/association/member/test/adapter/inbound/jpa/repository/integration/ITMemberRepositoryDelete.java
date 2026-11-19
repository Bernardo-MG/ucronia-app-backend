
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.adapter.inbound.jpa.repository.KeyAssignmentSpringRepository;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.ReadMemberSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - delete")
class ITMemberRepositoryDelete {

    @Autowired
    private KeyAssignmentRepository       assignmentRepository;

    @Autowired
    private KeyAssignmentSpringRepository assignmentSpringRepository;

    @Autowired
    private ProfileSpringRepository       profileSpringRepository;

    @Autowired
    private MemberRepository              repository;

    @Autowired
    private ReadMemberSpringRepository    springRepository;

    @Test
    @DisplayName("When deleting a member, then it is deleted")
    @PositiveFeeType
    @ActiveMember
    void testDelete_Active() {
        // WHEN
        repository.delete(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("When deleting a member, then it is deleted")
            .isZero();
    }

    @Test
    @DisplayName("When deleting a member, then the profile is deleted")
    @PositiveFeeType
    @ActiveMember
    void testDelete_Active_Profile() {
        // WHEN
        repository.delete(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(profileSpringRepository.count())
            .as("When deleting a member, then the profile is deleted")
            .isZero();
    }

    @Test
    @DisplayName("When a member is deleted, then its assignment is removed and the key remains unassigned")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testDelete_KeyAssignment() {
        // WHEN
        repository.delete(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignmentSpringRepository.count())
            .as("Deleting a member removes its assignment and keeps the unassigned key")
            .isZero();
        Assertions.assertThat(assignmentRepository.findOne(KeyConstants.NUMBER))
            .as("Deleting a member removes its assignment and keeps the unassigned key")
            .contains(KeyAssignments.unassigned());
    }

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("When there is no data, then nothing is deleted")
            .isZero();
    }

    @Test
    @DisplayName("When there is a profile with no member role, then nothing is deleted")
    @ValidProfile
    void testDelete_NoMembership() {
        // WHEN
        repository.delete(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("When there is a profile with no member role, then nothing is deleted")
            .isZero();
    }

}
