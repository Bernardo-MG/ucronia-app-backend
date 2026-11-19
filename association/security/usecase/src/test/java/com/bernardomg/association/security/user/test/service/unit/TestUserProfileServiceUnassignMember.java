
package com.bernardomg.association.security.user.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.usecase.service.DefaultUserProfileService;
import com.bernardomg.security.domain.user.exception.MissingUsernameException;
import com.bernardomg.security.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultUserProfileService - unassignProfile")
class TestUserProfileServiceUnassignMember {

    @InjectMocks
    private DefaultUserProfileService service;

    @Mock
    private UserProfileRepository     userProfileRepository;

    @Mock
    private UserRepository            userRepository;

    @Test
    @DisplayName("When there is a member assigned to the user, then it removes the member")
    void testUnassignProfile() {

        // GIVEN
        given(userRepository.exists(UserConstants.USERNAME)).willReturn(true);

        // WHEN
        service.unassignProfile(UserConstants.USERNAME);

        // THEN
        verify(userProfileRepository).unassignProfile(UserConstants.USERNAME);
    }

    @Test
    @DisplayName("When there is no member, then it throws an exception")
    void testUnassignProfile_NoMember() {
        final ThrowingCallable action;

        // GIVEN
        given(userRepository.exists(UserConstants.USERNAME)).willReturn(false);

        // WHEN
        action = () -> service.unassignProfile(UserConstants.USERNAME);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no member, then it throws an exception")
            .isInstanceOf(MissingUsernameException.class);
    }

}
