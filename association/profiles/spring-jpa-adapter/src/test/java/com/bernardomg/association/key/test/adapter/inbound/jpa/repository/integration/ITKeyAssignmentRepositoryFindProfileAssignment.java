
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
@DisplayName("KeyAssignmentRepository - Find profile assignment")
class ITKeyAssignmentRepositoryFindProfileAssignment {

    @Autowired
    private KeyAssignmentRepository repository;

    @Test
    @DisplayName("Assignments are found by profile number")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testFindProfileAssignments() {
        final Collection<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findProfileAssignments(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignments)
            .containsExactly(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("Looking up a missing profile reports a missing profile")
    void testFindProfileAssignments_MissingProfile() {
        final ThrowingCallable execution;

        // WHEN
        execution = () -> repository.findProfileAssignments(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("A profile without keys returns no assignments")
    @PositiveFeeType
    @ActiveMember
    void testFindProfileAssignments_NotAssigned() {
        final Collection<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findProfileAssignments(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignments)
            .isEmpty();
    }

    @Test
    @DisplayName("A profile without membership is a valid assignment target")
    @ValidProfile
    void testFindProfileAssignments_ProfileWithoutMembership() {
        final Collection<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findProfileAssignments(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignments)
            .isEmpty();
    }

}
