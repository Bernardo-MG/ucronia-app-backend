
package com.bernardomg.association.security.account.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.security.account.usecase.DefaultAccountProfileService;
import com.bernardomg.association.security.user.domain.model.UserProfile;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfiles;
import com.bernardomg.security.usecase.session.UsernameInSessionProvider;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAccountProfileService - getCurrentProfile")
class TestAccountProfileServiceGetCurrentProfile {

    @InjectMocks
    private DefaultAccountProfileService service;

    @Mock
    private UsernameInSessionProvider    usernameProvider;

    @Mock
    private UserProfileRepository        userProfileRepository;

    @Test
    @DisplayName("When there is no profile for the user in session, then it returns an empty result")
    void testGetCurrentProfile_NoProfile() {
        final Optional<UserProfile> profile;

        // GIVEN
        given(usernameProvider.getCurrentUsername()).willReturn(Optional.of(UserConstants.USERNAME));
        given(userProfileRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        profile = service.getCurrentProfile();

        // THEN
        Assertions.assertThat(profile)
            .as("When there is no profile for the user in session, then it returns an empty result")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is no user in session, then it returns an empty result")
    void testGetCurrentProfile_NoUserInSession() {
        final Optional<UserProfile> profile;

        // GIVEN
        given(usernameProvider.getCurrentUsername()).willReturn(Optional.empty());

        // WHEN
        profile = service.getCurrentProfile();

        // THEN
        Assertions.assertThat(profile)
            .as("When there is no user in session, then it returns an empty result")
            .isEmpty();
        verifyNoInteractions(userProfileRepository);
    }

    @Test
    @DisplayName("When there is a profile for the user in session, then it returns the profile")
    void testGetCurrentProfile_ProfileFound() {
        final UserProfile           expected;
        final Optional<UserProfile> profile;

        // GIVEN
        expected = UserProfiles.valid();

        given(usernameProvider.getCurrentUsername()).willReturn(Optional.of(UserConstants.USERNAME));
        given(userProfileRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.of(expected));

        // WHEN
        profile = service.getCurrentProfile();

        // THEN
        Assertions.assertThat(profile)
            .as("When there is a profile for the user in session, then it returns the profile")
            .contains(expected);
    }

}
