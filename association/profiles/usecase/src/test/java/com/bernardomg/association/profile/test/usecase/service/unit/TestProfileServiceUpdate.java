
package com.bernardomg.association.profile.test.usecase.service.unit;

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

import com.bernardomg.association.profile.domain.exception.MissingContactMethodException;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.association.profile.usecase.service.DefaultProfileService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultProfileService - update")
class TestProfileServiceUpdate {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private ProfileRepository       profileRepository;

    @InjectMocks
    private DefaultProfileService   service;

    @Test
    @DisplayName("When there is a profile with an existing identifier, then an exception is thrown")
    void testUpdate_IdentifierExists() {
        final ThrowingCallable action;
        final Profile          profile;

        // GIVEN
        profile = Profiles.nameChange();

        given(profileRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(profileRepository.existsByIdentifierForAnother(ProfileConstants.NUMBER, ProfileConstants.IDENTIFIER))
            .willReturn(true);

        // WHEN
        action = () -> service.update(profile);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "identifier", ProfileConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("When there is a not existing profile, then an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Profile          profile;
        final ThrowingCallable action;

        // GIVEN
        profile = Profiles.nameChange();

        given(profileRepository.exists(ProfileConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(profile);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing profile, then an exception is thrown")
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("When there is a profile having padding whitespaces in first and last name, then these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.padded();

        given(profileRepository.exists(ProfileConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(profile);

        // THEN
        verify(profileRepository).save(Profiles.valid());
    }

    @Test
    @DisplayName("When updating a profile, then the change is persisted")
    void testUpdate_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.nameChange();

        given(profileRepository.exists(ProfileConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(profile);

        // THEN
        verify(profileRepository).save(Profiles.nameChange());
    }

    @Test
    @DisplayName("When updating an active profile, then the change is returned")
    void testUpdate_ReturnedData() {
        final Profile profile;
        final Profile updated;

        // GIVEN
        profile = Profiles.nameChange();

        given(profileRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(profileRepository.save(Profiles.nameChange())).willReturn(Profiles.nameChange());

        // WHEN
        updated = service.update(profile);

        // THEN
        Assertions.assertThat(updated)
            .as("profile")
            .isEqualTo(Profiles.nameChange());
    }

    @Test
    @DisplayName("When there is a profile with a not existing profile method, then an exception is thrown")
    void testUpdate_WithProfile_NotExistingContactMethod() {
        final Profile          profile;
        final ThrowingCallable action;

        // GIVEN
        profile = Profiles.withEmail();

        given(profileRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(profile);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a profile with a not existing profile method, then an exception is thrown")
            .isInstanceOf(MissingContactMethodException.class);
    }

    @Test
    @DisplayName("When updating a profile with a profile method, then the change is persisted")
    void testUpdate_WithProfile_PersistedData() {
        final Profile profile;

        // GIVEN
        profile = Profiles.withEmail();

        given(profileRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(contactMethodRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(profile);

        // THEN
        verify(profileRepository).save(Profiles.withEmail());
    }

}
