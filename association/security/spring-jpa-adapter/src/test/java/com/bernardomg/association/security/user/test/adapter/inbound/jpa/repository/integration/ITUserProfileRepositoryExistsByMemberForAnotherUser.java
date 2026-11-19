
package com.bernardomg.association.security.user.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.TestApplication;
import com.bernardomg.association.security.user.test.configuration.data.annotation.AlternativeUserWithMember;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithProfile;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("UserProfileRepository - existsByProfileForAnotherUser")
class ITUserProfileRepositoryExistsByMemberForAnotherUser {

    @Autowired
    private UserProfileRepository repository;

    @Test
    @DisplayName("When the member is assigned to another user, then it doesn't exist")
    @AlternativeUserWithMember
    void testExistsByProfileForAnotherUser() {
        final boolean exists;

        // WHEN
        exists = repository.existsByProfileForAnotherUser(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("When the member is assigned to another user, then it doesn't exist")
            .isTrue();
    }

    @Test
    @DisplayName("When the member is assigned to the user, then it doesn't exist")
    @ValidUserWithProfile
    void testExistsByProfileForAnotherUser_AssignedToUser() {
        final boolean exists;

        // WHEN
        exists = repository.existsByProfileForAnotherUser(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("When the member is assigned to the user, then it doesn't exist")
            .isFalse();
    }

    @Test
    @DisplayName("When no data exists, then it doesn't exist")
    void testExistsByProfileForAnotherUser_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByProfileForAnotherUser(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("When no data exists, then it doesn't exist")
            .isFalse();
    }

}
