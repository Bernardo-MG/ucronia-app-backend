
package com.bernardomg.association.profile.test.usecase.service.unit;

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

import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.association.profile.usecase.service.DefaultProfileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultProfileService - delete")
class TestProfileServiceDelete {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private ProfileRepository       profileRepository;

    @InjectMocks
    private DefaultProfileService   service;

    @Test
    @DisplayName("When deleting, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Profiles.valid()));

        // WHEN
        service.delete(ProfileConstants.NUMBER);

        // THEN
        verify(profileRepository).delete(ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When the profile doesn't exist, then an exception is thrown")
    void testDelete_NotExisting_NotRemovesEntity() {
        final ThrowingCallable action;

        // GIVEN
        given(profileRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the profile doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingProfileException.class);
    }

}
