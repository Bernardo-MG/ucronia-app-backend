
package com.bernardomg.association.profile.test.usecase.service.unit;

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

import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.association.profile.usecase.service.DefaultProfileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultProfileService - getOne")
class TestProfileServiceGetOne {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private ProfileRepository       profileRepository;

    @InjectMocks
    private DefaultProfileService   service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetOne() {
        final Optional<Profile> profile;

        // GIVEN
        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));

        // WHEN
        profile = service.getOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .as("When there is data, then it is returned")
            .contains(Profiles.valid());
    }

    @Test
    @DisplayName("When the profile doesn't exist, then an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the profile doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingProfileException.class);
    }

}
