
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.adapter.inbound.jpa.model.KeyAssignmentEntity;
import com.bernardomg.association.key.adapter.inbound.jpa.repository.KeyAssignmentSpringRepository;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.test.configuration.data.annotation.AlternativeAvailableKey;
import com.bernardomg.association.key.test.configuration.data.annotation.AvailableKey;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignmentEntities;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository - Save")
class ITKeyAssignmentRepositorySave {

    @Autowired
    private KeyAssignmentRepository       repository;

    @Autowired
    private KeyAssignmentSpringRepository springRepository;

    @Test
    @DisplayName("Saving a key twice doesn't duplicate it")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testSave_Duplicated() {
        final Collection<KeyAssignmentEntity> entities;

        // WHEN
        repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);
        repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .containsExactly(KeyAssignmentEntities.assigned());
    }

    @Test
    @DisplayName("An inactive member can keep and receive a key assignment")
    @PositiveFeeType
    @InactiveMember
    @AvailableKey
    void testSave_InactiveMember() {
        final List<KeyAssignmentEntity> entities;

        // WHEN
        repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .containsExactly(KeyAssignmentEntities.assigned());
    }

    @Test
    @DisplayName("When saving, the data is persisted")
    @ValidProfile
    @AvailableKey
    void testSave_PersistedData() {
        final Collection<KeyAssignmentEntity> entities;

        // WHEN
        repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .containsExactly(KeyAssignmentEntities.assigned());
    }

    @Test
    @DisplayName("A profile without membership can receive a key")
    @ValidProfile
    @AvailableKey
    void testSave_ProfileWithoutMembership() {
        final Collection<KeyAssignmentEntity> entities;

        // WHEN
        repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .containsExactly(KeyAssignmentEntities.assigned());
    }

    @Test
    @DisplayName("Reassigning a key preserves the previous profile's other assignments")
    @PositiveFeeType
    @ActiveMemberWithKey
    @AlternativeProfile
    @AlternativeAvailableKey
    void testSave_Reassigned_OtherKeys() {
        final KeyAssignment             other;
        final Collection<KeyAssignment> previous;
        final Collection<KeyAssignment> current;

        // GIVEN
        other = repository.save(KeyConstants.ALTERNATIVE_NUMBER, ProfileConstants.NUMBER);

        // WHEN
        repository.save(KeyConstants.NUMBER, ProfileConstants.ALTERNATIVE_NUMBER);

        // THEN
        previous = repository.findProfileAssignments(ProfileConstants.NUMBER);
        current = repository.findProfileAssignments(ProfileConstants.ALTERNATIVE_NUMBER);

        Assertions.assertThat(previous)
            .containsExactly(other);
        Assertions.assertThat(current)
            .containsExactly(KeyAssignments.reassigned());
    }

    @Test
    @DisplayName("Reassigning a key replaces the persisted profile without duplicating the assignment")
    @PositiveFeeType
    @ActiveMemberWithKey
    @AlternativeProfile
    void testSave_Reassigned_PersistedData() {
        final Collection<KeyAssignmentEntity> entities;

        // WHEN
        repository.save(KeyConstants.NUMBER, ProfileConstants.ALTERNATIVE_NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .singleElement()
            .satisfies(entity -> {
                Assertions.assertThat(entity.getKey()
                    .getNumber())
                    .isEqualTo(KeyConstants.NUMBER);
                Assertions.assertThat(entity.getProfile()
                    .getNumber())
                    .isEqualTo(ProfileConstants.ALTERNATIVE_NUMBER);
            });
    }

    @Test
    @DisplayName("Reassigning a key removes it from the previous profile and gives it to the new profile")
    @PositiveFeeType
    @ActiveMemberWithKey
    @AlternativeProfile
    void testSave_Reassigned_ProfileAssignments() {
        final Collection<KeyAssignment> previous;
        final Collection<KeyAssignment> current;

        // WHEN
        repository.save(KeyConstants.NUMBER, ProfileConstants.ALTERNATIVE_NUMBER);

        // THEN
        previous = repository.findProfileAssignments(ProfileConstants.NUMBER);
        current = repository.findProfileAssignments(ProfileConstants.ALTERNATIVE_NUMBER);

        Assertions.assertThat(previous)
            .isEmpty();
        Assertions.assertThat(current)
            .containsExactly(KeyAssignments.reassigned());
    }

    @Test
    @DisplayName("Reassigning a key returns the new profile")
    @PositiveFeeType
    @ActiveMemberWithKey
    @AlternativeProfile
    void testSave_Reassigned_ReturnedData() {
        final KeyAssignment assignment;

        // WHEN
        assignment = repository.save(KeyConstants.NUMBER, ProfileConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .isEqualTo(KeyAssignments.reassigned());
        Assertions.assertThat(repository.findOne(KeyConstants.NUMBER))
            .contains(KeyAssignments.reassigned());
    }

    @Test
    @DisplayName("When saving, the data is returned")
    @ValidProfile
    @AvailableKey
    void testSave_ReturnedData() {
        final KeyAssignment assignment;

        // WHEN
        assignment = repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .isEqualTo(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("Two keys can be assigned to the same profile")
    @PositiveFeeType
    @ActiveMemberWithKey
    @AlternativeAvailableKey
    void testSave_TwoKeys() {
        final List<KeyAssignmentEntity> entities;

        // WHEN
        repository.save(KeyConstants.ALTERNATIVE_NUMBER, MemberConstants.NUMBER);

        // THEN
        entities = springRepository.findAll();

        Assertions.assertThat(entities)
            .containsExactly(KeyAssignmentEntities.assigned(), KeyAssignmentEntities.assignedAlternative());
    }

}
