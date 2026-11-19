
package com.bernardomg.association.security.user.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.security.user.domain.exception.MissingUserProfileException;
import com.bernardomg.association.security.user.domain.model.UserProfile;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfileConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfiles;
import com.bernardomg.association.security.user.test.configuration.factory.Users;
import com.bernardomg.association.security.user.usecase.service.DefaultUserProfileService;
import com.bernardomg.security.domain.user.exception.MissingUsernameException;
import com.bernardomg.security.domain.user.repository.UserRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultUserProfileService - assignProfile")
class TestUserProfileServiceAssignProfile {

    @InjectMocks
    private DefaultUserProfileService service;

    @Mock
    private UserProfileRepository     userProfileRepository;

    @Mock
    private UserRepository            userRepository;

    @Test
    @DisplayName("When the profile has already been assigned, then it throws an exception")
    void testAssignProfile_ExistingProfile() {
        final ThrowingCallable action;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(userProfileRepository.findOne(UserProfileConstants.NUMBER)).willReturn(Optional.of(UserProfiles.valid()));

        given(userProfileRepository.existsByProfileForAnotherUser(UserConstants.USERNAME, UserProfileConstants.NUMBER))
            .willReturn(true);

        // WHEN
        action = () -> service.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("assigned", "profile", UserProfileConstants.NUMBER));
    }

    @Test
    @DisplayName("When there is no profile, then it throws an exception")
    void testAssignProfile_NoProfile() {
        final ThrowingCallable action;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(userProfileRepository.findOne(UserProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no profile, then it throws an exception")
            .isInstanceOf(MissingUserProfileException.class);
    }

    @Test
    @DisplayName("When there is no user, then it throws an exception")
    void testAssignProfile_NoUser() {
        final ThrowingCallable action;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no user, then it throws an exception")
            .isInstanceOf(MissingUsernameException.class);
    }

    @Test
    @DisplayName("When there is valid data, then the relationship is persisted")
    void testAssignProfile_Persisted() {

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(userProfileRepository.findOne(UserProfileConstants.NUMBER)).willReturn(Optional.of(UserProfiles.valid()));

        // WHEN
        service.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        verify(userProfileRepository).assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When there is valid data, then the created relationship is returned")
    void testAssignProfile_Returned() {
        final UserProfile profile;

        // GIVEN
        given(userRepository.findOne(UserConstants.USERNAME)).willReturn(Optional.of(Users.enabled()));
        given(userProfileRepository.findOne(UserProfileConstants.NUMBER)).willReturn(Optional.of(UserProfiles.valid()));
        given(userProfileRepository.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER))
            .willReturn(UserProfiles.valid());

        // WHEN
        profile = service.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("When there is valid data, then the created relationship is returned")
            .isEqualTo(UserProfiles.valid());
    }

}
