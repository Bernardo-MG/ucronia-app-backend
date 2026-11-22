
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository - findProfileAssignments")
class ITKeyAssignmentRepositoryFindProfileAssignment {

    @Autowired
    private KeyAssignmentRepository repository;

    @Test
    @DisplayName("When assignments are queried by profile number, then matching assignments are returned")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testFindProfileAssignments() {
        final Collection<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findProfileAssignments(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignments)
            .as("Assignments are found by profile number")
            .containsExactly(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("When a missing profile is queried, then it is reported as missing")
    void testFindProfileAssignments_MissingProfile() {
        final ThrowingCallable action;

        // WHEN
        action = () -> repository.findProfileAssignments(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("Looking up a missing profile reports a missing profile")
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("When a profile has no keys, then no assignments are returned")
    @PositiveFeeType
    @ActiveMember
    void testFindProfileAssignments_NotAssigned() {
        final Collection<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findProfileAssignments(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignments)
            .as("A profile without keys returns no assignments")
            .isEmpty();
    }

    @Test
    @DisplayName("When a profile without membership is selected, then it is a valid assignment target")
    @ValidProfile
    void testFindProfileAssignments_ProfileWithoutMembership() {
        final Collection<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findProfileAssignments(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignments)
            .as("A profile without membership is a valid assignment target")
            .isEmpty();
    }

}
