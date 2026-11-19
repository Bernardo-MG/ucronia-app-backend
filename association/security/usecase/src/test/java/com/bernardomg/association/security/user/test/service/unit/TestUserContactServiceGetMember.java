
package com.bernardomg.association.security.user.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.security.user.domain.model.UserProfile;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfiles;
import com.bernardomg.association.security.user.usecase.service.DefaultUserProfileService;
import com.bernardomg.security.domain.user.exception.MissingUsernameException;
import com.bernardomg.security.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultUserProfileService - getProfile")
class TestUserProfileServiceGetProfile {

    @InjectMocks
    private DefaultUserProfileService service;

    @Mock
    private UserProfileRepository     userProfileRepository;

    @Mock
    private UserRepository            userRepository;

    @Test
    @DisplayName("When there is a profile assigned to the user, then it returns the user")
    void testGetProfile() {
        final Optional<UserProfile> profile;

        // GIVEN
        given(userRepository.exists(UserConstants.USERNAME)).willReturn(true);
        given(userProfileRepository.findByUsername(UserConstants.USERNAME))
            .willReturn(Optional.of(UserProfiles.valid()));

        // WHEN
        profile = service.getProfile(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profile)
            .as("When there is a profile assigned to the user, then it returns the user")
            .contains(UserProfiles.valid());
    }

    @Test
    @DisplayName("When there is no profile, then it throws an exception")
    void testGetProfile_NoProfile() {
        final ThrowingCallable action;

        // GIVEN
        given(userRepository.exists(UserConstants.USERNAME)).willReturn(false);

        // WHEN
        action = () -> service.getProfile(UserConstants.USERNAME);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no profile, then it throws an exception")
            .isInstanceOf(MissingUsernameException.class);
    }

}
