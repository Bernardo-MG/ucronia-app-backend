
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.adapter.inbound.jpa.repository.KeyAssignmentSpringRepository;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.test.configuration.data.annotation.AlternativeAvailableKey;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignmentEntities;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository - Delete")
class ITKeyAssignmentRepositoryDelete {

    @Autowired
    private MemberRepository              memberRepository;

    @Autowired
    private KeyAssignmentRepository       repository;

    @Autowired
    private KeyAssignmentSpringRepository springRepository;

    @Test
    @DisplayName("When deleting an assignment, it is deleted")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testDelete() {

        // WHEN
        repository.delete(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("Removing an assignment keeps the member")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testDelete_Member() {
        final Member member;

        // WHEN
        repository.delete(KeyConstants.NUMBER);

        // THEN
        member = memberRepository.findOne(MemberConstants.NUMBER)
            .orElseThrow();
        Assertions.assertThat(member)
            .isEqualTo(Members.active());
    }

    @Test
    @DisplayName("When there is no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("Removing one assignment keeps the profile's other assignment")
    @PositiveFeeType
    @ActiveMemberWithKey
    @AlternativeAvailableKey
    void testDelete_ProfileWithMultipleKeys() {

        // WHEN
        repository.delete(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .contains(KeyAssignmentEntities.assigned(), KeyAssignmentEntities.unassigned());
    }

}
